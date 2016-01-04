package cn.ixuehu.smartpeking.base.newscenter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

import cn.ixuehu.smartpeking.base.MenuController;
import cn.ixuehu.smartpeking.bean.NewsCenterBean;

/**
 * 项目名：SmartPeking
 * 包名：cn.ixuehu.smartpeking.base.newscenter
 * Created by daimaren on 2016/1/4.
 */
public class NewsMenuController extends MenuController implements ViewPager.OnPageChangeListener {

   /* @ViewInject(R.id.newscenter_news_pager)
    private ViewPager			mPager;	// ViewPager

    @ViewInject(R.id.newscenter_news_indicator);
    private TabPageIndicator	mIndicator; // 指针*/

    private NewsCenterBean.NewsCenterMenuBean mMenuBean;	// 菜单数据

    private List<NewsCenterBean.NewsBean> mChildren;	// ViewPager对应的数据
    public NewsMenuController(Context context,NewsCenterBean.NewsCenterMenuBean menuBean) {
        super(context);

        this.mMenuBean = menuBean;
        mChildren = menuBean.children;
    }

    @Override
    protected View initView(Context context) {
        return null;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
