package cn.ixuehu.smartpeking.base.newscenter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import cn.ixuehu.smartpeking.bean.NewsListPagerBean;
import cn.ixuehu.smartpeking.utils.Constans;

/**
 * 项目名：SmartPeking
 * 包名：cn.ixuehu.smartpeking.base.newscenter
 * Created by daimaren on 2016/1/4.
 */
public class PicMenuController extends MenuController{
    @ViewInject(R.id.newscenter_pic_list_view)
    private ListView mListView;
    @ViewInject(R.id.newscenter_pic_grid_view)
    private GridView mGridView;
    private List<NewsListPagerBean.NewsItemBean> mNewsPics;
    private BitmapUtils bmpUtils;
    private boolean isGrid = false;
    public PicMenuController(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, Constans.PHOTOS_URL, new RequestCallBack<String>() {
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

    private void processData(String json) {
        //解析数据
        Gson gson = new Gson();
        NewsListPagerBean newsListPagerBean = gson.fromJson(json, NewsListPagerBean.class);
        mNewsPics = newsListPagerBean.data.news;

        picAdapter adapter = new picAdapter();
        mListView.setAdapter(adapter);
        mGridView.setAdapter(adapter);
    }

    @Override
    protected View initView(Context context) {
        View view = View.inflate(mContext, R.layout.newscenter_pic, null);
        ViewUtils.inject(this, view);
        bmpUtils = new BitmapUtils(mContext);
        return view;
    }

    public void setSwitchButton(ImageButton mIbListOrGrid) {
        mIbListOrGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isGrid = ! isGrid;
                mListView.setVisibility(isGrid ? View.GONE:View.VISIBLE);
                mGridView.setVisibility(isGrid ? View.VISIBLE : View.GONE);
                ((ImageButton)view).setImageResource(isGrid ? R.drawable.icon_pic_grid_type :
                        R.drawable.icon_pic_list_type);
            }
        });
    }

    class picAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            if (mNewsPics != null)
                return mNewsPics.size();
            return 0;
        }

        @Override
        public Object getItem(int i) {
            if (mNewsPics != null)
                return  mNewsPics.get(i);
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null)
            {
                //没有复用
                view = View.inflate(mContext, R.layout.item_pic, null);
                holder = new ViewHolder();
                view.setTag(holder);
                holder.tvTitle = (TextView) view.findViewById(R.id.item_pic_tv_title);
                holder.ivIcon = (ImageView) view.findViewById(R.id.item_pic_iv_icon);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }
            //赋值
            holder.tvTitle.setText(mNewsPics.get(i).title);
            holder.ivIcon.setImageResource(R.drawable.pic_item_list_default);
            String url = mNewsPics.get(i).listimage.replace("10.0.2.2:8080",Constans.LOCAL_IP);
            bmpUtils.display(holder.ivIcon,url);
            return view;
        }
    }
    class ViewHolder
    {
        ImageView ivIcon;
        TextView tvTitle;
    }
}
