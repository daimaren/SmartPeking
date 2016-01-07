package cn.ixuehu.smartpeking.base.newscenter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;

import java.util.List;

import cn.ixuehu.smartpeking.MainUI;
import cn.ixuehu.smartpeking.R;
import cn.ixuehu.smartpeking.base.MenuController;
import cn.ixuehu.smartpeking.bean.NewsCenterBean;

/**
 * 项目名：SmartPeking
 * 包名：cn.ixuehu.smartpeking.base.newscenter
 * Created by daimaren on 2016/1/4.
 */
public class NewsMenuController extends MenuController implements ViewPager.OnPageChangeListener {

   @ViewInject(R.id.newscenter_news_pager)
    private ViewPager			mPager;	// ViewPager

    @ViewInject(R.id.newscenter_news_indicator)
    private TabPageIndicator mIndicator; // 指针

    private NewsCenterBean.NewsCenterMenuBean mMenuBean;	// 菜单数据

    private List<NewsCenterBean.NewsBean> mChildren;	// ViewPager对应的数据
    private TextView tv;
    public NewsMenuController(Context context,NewsCenterBean.NewsCenterMenuBean menuBean) {
        super(context);

        this.mMenuBean = menuBean;
        mChildren = menuBean.children;
    }

    @Override
    protected View initView(Context context) {
        View view = View.inflate(context,R.layout.newscenter_news,null);
        ViewUtils.inject(this,view);
        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        SlidingMenu menu= ((MainUI)mContext).getSlidingMenu();
        menu.setTouchModeAbove(position == 0 ? SlidingMenu.TOUCHMODE_FULLSCREEN : SlidingMenu.TOUCHMODE_NONE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void initData() {
        mPager.setAdapter(new NewsPagerAdapter());
        mIndicator.setViewPager(mPager);

        mIndicator.setOnPageChangeListener(this);
    }
    @OnClick(R.id.newscenter_news_arrow)
    public void Clickarrow(View view)
    {
        //选中下一个
        int item = mPager.getCurrentItem();
        mPager.setCurrentItem(++item);
    }
    class NewsPagerAdapter extends PagerAdapter
    {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView tv = new TextView(mContext);
            tv.setText(mChildren.get(position).title);
            tv.setTextSize(24);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.RED);
            container.addView(tv);
            return tv;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            if (mChildren != null)
                return mChildren.size();
            return 0;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (mChildren != null)
            {
                NewsCenterBean.NewsBean bean = mChildren.get(position);
                return bean.title;
            }
            return super.getPageTitle(position);
        }
    }
}
