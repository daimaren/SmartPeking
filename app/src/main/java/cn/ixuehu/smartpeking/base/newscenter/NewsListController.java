package cn.ixuehu.smartpeking.base.newscenter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.ixuehu.smartpeking.R;
import cn.ixuehu.smartpeking.base.MenuController;
import cn.ixuehu.smartpeking.bean.NewsCenterBean;
import cn.ixuehu.smartpeking.bean.NewsListPagerBean;
import cn.ixuehu.smartpeking.utils.Constans;

/**
 * 项目名：SmartPeking
 * 包名：cn.ixuehu.smartpeking.base.newscenter
 * Created by daimaren on 2016/1/7.
 */
public class NewsListController extends MenuController implements ViewPager.OnPageChangeListener{
    private static final String TAG = "NewsListController";
    private String mUrl;
    private ListView mListView;
    @ViewInject(R.id.news_list_pic_pager)
    private ViewPager mPicPager;
    @ViewInject(R.id.news_list_tv_title)
    private TextView mTvTitle;
    @ViewInject(R.id.news_list_point_container)
    private LinearLayout mPointContainer;
    private List<NewsListPagerBean.NewsTopNewsBean> mPicDatas;
    private BitmapUtils mBitmapUtils;
    private ImageView iv;
    public NewsListController(Context context,NewsCenterBean.NewsBean data) {
        super(context);
        this.mUrl = data.url;
    }

    @Override
    protected View initView(Context context) {
        mBitmapUtils = new BitmapUtils(mContext);
        View piclayout = View.inflate(mContext, R.layout.news_top_pic,null);
        //ViewUtils注入
        ViewUtils.inject(this,piclayout);
        return piclayout;
    }

    @Override
    public void initData() {
        HttpUtils utils = new HttpUtils();
        String url= Constans.BASE_URL + mUrl;
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                processData(result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }
    private void processData(String json)
    {
        Gson gson = new Gson();
        NewsListPagerBean bean= gson.fromJson(json, NewsListPagerBean.class);
        //Log.d(TAG, "" + bean.data.topnews.get(0).title);
        mPicDatas = bean.data.topnews;
        //动态的加载点
        for (int i=0;i<mPicDatas.size();i++)
        {
            View point = new View(mContext);
            point.setBackgroundResource(R.drawable.dot_normal);
            LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(6,6);
            if (i != 0)
            {
                params.leftMargin = 8;
            }
            else
            {
                point.setBackgroundResource(R.drawable.dot_focus);
            }
            mPointContainer.addView(point,params);
        }
        mPicPager.setAdapter(new TopPicPagerAdapter());
        mPicPager.setOnPageChangeListener(this);
        // 设置title的默认值
        mTvTitle.setText(mPicDatas.get(0).title);
    }
    class TopPicPagerAdapter extends PagerAdapter
    {
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            iv =new ImageView(mContext);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(R.drawable.home_scroll_default);
            String url = mPicDatas.get(position).topimage;
            url = url.replace("10.0.2.2:8080","10.13.0.93");
            mBitmapUtils.display(iv, url);
            container.addView(iv);
            return iv;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            if (mPicDatas != null)
                return mPicDatas.size();
            return 0;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int childCount = mPointContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = mPointContainer.getChildAt(i);
            view.setBackgroundResource(position == i ? R.drawable.dot_focus: R.drawable.dot_normal);
        }
        mTvTitle.setText(mPicDatas.get(position).title);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
