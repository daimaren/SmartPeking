package cn.ixuehu.smartpeking.base.newscenter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import cn.ixuehu.smartpeking.utils.CacheUtils;
import cn.ixuehu.smartpeking.utils.Constans;

/**
 * 项目名：SmartPeking
 * 包名：cn.ixuehu.smartpeking.base.newscenter
 * Created by daimaren on 2016/1/7.
 */
public class NewsListController extends MenuController implements ViewPager.OnPageChangeListener{
    private static final String TAG = "NewsListController";
    private String mUrl;
    @ViewInject(R.id.news_list_pic_pager)
    private ViewPager mPicPager;
    @ViewInject(R.id.news_list_tv_title)
    private TextView mTvTitle;
    @ViewInject(R.id.news_list_point_container)
    private LinearLayout mPointContainer;
    private List<NewsListPagerBean.NewsTopNewsBean> mPicDatas;
    private BitmapUtils mBitmapUtils;
    private ImageView iv;
    private AutoSwitchPicTask		mAutoSwitchTask;
    @ViewInject(R.id.news_list_view)
    private ListView mListView;
    private List<NewsListPagerBean.NewsItemBean>  mNewsDatas;
    public NewsListController(Context context,NewsCenterBean.NewsBean data) {
        super(context);
        this.mUrl = data.url;
    }

    @Override
    protected View initView(Context context) {
        mBitmapUtils = new BitmapUtils(mContext);
        View  view = View.inflate(mContext, R.layout.news_list_pager,null);
        ViewUtils.inject(this, view);

        View piclayout = View.inflate(mContext, R.layout.news_top_pic,null);
        //ViewUtils注入
        ViewUtils.inject(this,piclayout);
        //ListView
        mListView.addHeaderView(piclayout);

        return view;
    }

    @Override
    public void initData() {
        HttpUtils utils = new HttpUtils();
        String url= Constans.BASE_URL + mUrl;
        //加入网路数据缓存
        String json = CacheUtils.getString(mContext,url);
        if (!TextUtils.isEmpty(json))
        {
            processData(json);
            return;
        }
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
        mNewsDatas = bean.data.news;
        mPicDatas = bean.data.topnews;
        //每次加载数据前清空mPointContainer
        mPointContainer.removeAllViews();
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
        //顶部图片轮播
        if (mAutoSwitchTask == null)
            mAutoSwitchTask = new AutoSwitchPicTask();
        mAutoSwitchTask.start();
        mPicPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mAutoSwitchTask.stop();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        mAutoSwitchTask.start();
                        break;
                }
                return false;
            }
        });
        //ListView数据适配器
        mListView.setAdapter(new ListDataAdapter());
    }
    class ViewHolder
    {
        ImageView	ivIcon;
        TextView    tvTitle;
        TextView	tvPubDate;
    }
    class ListDataAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            if (mNewsDatas != null)
                return mNewsDatas.size();
            return 0;
        }

        @Override
        public Object getItem(int i) {
            if (mNewsDatas != null)
                return mNewsDatas.get(i);
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if (view == null)
            {
                viewHolder = new ViewHolder();
                view = View.inflate(mContext,R.layout.item_news,null);
                view.setTag(viewHolder);
                //初始化View
                viewHolder.tvTitle = (TextView)view.findViewById(R.id.item_news_tv_title);
                viewHolder.tvPubDate = (TextView) view.findViewById(R.id.item_news_tv_pubdate);
                viewHolder.ivIcon = (ImageView) view.findViewById(R.id.item_news_iv_icon);
            }
            else
            {
                viewHolder = (ViewHolder)view.getTag();
            }
            //设置数据
            NewsListPagerBean.NewsItemBean newsItemBean = mNewsDatas.get(i);
            viewHolder.tvTitle.setText(newsItemBean.title);
            viewHolder.tvPubDate.setText(newsItemBean.pubdate);
            //设置默认图片
            viewHolder.ivIcon.setImageResource(R.drawable.pic_item_list_default);
            //加载网络图片
            String url = newsItemBean.listimage.replace("10.0.2.2:8080",Constans.LOCAL_IP);
            mBitmapUtils.display(viewHolder.ivIcon,url);
            return view;
        }
    }
    class AutoSwitchPicTask extends Handler implements Runnable
    {
        public void start()
        {
            stop();
            postDelayed(this,2000);
        }
        public void stop()
        {
            removeCallbacks(this);
        }
        @Override
        public void run() {
            //如果ViewPager不是在最后一个，自动滚动到下一个
            int position = mPicPager.getCurrentItem();
            if (position != mPicPager.getAdapter().getCount() - 1)
            {
                mPicPager.setCurrentItem(++position);
            }
            else
            {
                //最后了就跳转到第一个
                mPicPager.setCurrentItem(0);
            }
            postDelayed(this,2000);
        }
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
            url = url.replace("10.0.2.2:8080",Constans.LOCAL_IP);
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
