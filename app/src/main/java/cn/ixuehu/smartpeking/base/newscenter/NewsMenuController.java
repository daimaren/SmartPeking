package cn.ixuehu.smartpeking.base.newscenter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.TabPageIndicator;

import java.util.List;

import cn.ixuehu.smartpeking.R;
import cn.ixuehu.smartpeking.base.MenuController;
import cn.ixuehu.smartpeking.bean.NewsCenterBean;

/**
 * 项目名：SmartPeking
 * 包名：cn.ixuehu.smartpeking.base.newscenter
 * Created by daimaren on 2016/1/4.
 */
public class NewsMenuController extends MenuController {

    private static final String	TAG	= "NewsMenuController";
   @ViewInject(R.id.newscenter_news_pager)
    private ViewPager			mPager;	// ViewPager

    @ViewInject(R.id.newscenter_news_indicator)
    private TabPageIndicator mIndicator; // 指针

    private NewsCenterBean.NewsCenterMenuBean mMenuBean;	// 菜单数据

    private List<NewsCenterBean.NewsBean> mChildren;	// ViewPager对应的数据

    public NewsMenuController(Context context,NewsCenterBean.NewsCenterMenuBean menuBean) {
        super(context);

        this.mMenuBean = menuBean;
        mChildren = menuBean.children;
    }

    @Override
    protected View initView(Context context) {
        View view = View.inflate(mContext,R.layout.newscenter_news,null);
        ViewUtils.inject(this,view);
        return view;
    }

    @Override
    public void initData() {
        mPager.setAdapter(new NewsPagerAdapter());
        mIndicator.setViewPager(mPager);

        //mIndicator.setOnPageChangeListener(this);
    }
/*    @OnClick(R.id.newscenter_news_arrow)
    public void ClickArrow(View view)
    {
        //选中下一个
        int item = mPager.getCurrentItem();
        mPager.setCurrentItem(++item);
    }*/
    class NewsPagerAdapter extends PagerAdapter
    {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            /*NewsCenterBean.NewsBean Bean = mChildren.get(position);
            NewsListController controller = new NewsListController(mContext,Bean);

            View rootView = controller.getmRootView();
            //将View添加到ViewPager中
            container.addView(rootView);
            controller.initData();
            return rootView;*/
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
