package cn.ixuehu.smartpeking.base.newscenter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import cn.ixuehu.smartpeking.R;
import cn.ixuehu.smartpeking.base.MenuController;
import cn.ixuehu.smartpeking.bean.NewsCenterBean;

/**
 * 项目名：SmartPeking
 * 包名：cn.ixuehu.smartpeking.base.newscenter
 * Created by daimaren on 2016/1/7.
 */
public class NewsListController extends MenuController{
    private static final String TAG = "NewsListController";
    private String mUrl;
    private ListView mListView;
    @ViewInject(R.id.news_list_pic_pager)
    private ViewPager mPicPager;
    @ViewInject(R.id.news_list_tv_title)
    private TextView mTvTitle;
    @ViewInject(R.id.news_list_point_container)
    private LinearLayout mPointContainer;

    public NewsListController(Context context,NewsCenterBean.NewsBean data) {
        super(context);
        this.mUrl = data.url;
    }

    @Override
    protected View initView(Context context) {
        View piclayout = View.inflate(mContext, R.layout.news_top_pic,null);
        //ViewUtils注入
        ViewUtils.inject(this,piclayout);
        return null;
    }
}
