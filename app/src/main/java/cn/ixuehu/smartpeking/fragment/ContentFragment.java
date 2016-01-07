package cn.ixuehu.smartpeking.fragment;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.ixuehu.smartpeking.BaseFragment;
import cn.ixuehu.smartpeking.MainUI;
import cn.ixuehu.smartpeking.R;
import cn.ixuehu.smartpeking.base.TabController;
import cn.ixuehu.smartpeking.base.tab.GovTabController;
import cn.ixuehu.smartpeking.base.tab.HomeTabController;
import cn.ixuehu.smartpeking.base.tab.NewsCenterTabController;
import cn.ixuehu.smartpeking.base.tab.SettingTabController;
import cn.ixuehu.smartpeking.base.tab.SmartServiceTabController;
import cn.ixuehu.smartpeking.widget.NoScrollViewPager;

/**
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56.fragment
 * @类名: ContentFragment
 * @创建者: 肖琦
 * @创建时间: 2015-4-22 下午3:17:16
 * @描述: 主页的内容
 * 
 * @svn版本: $Rev: 18 $
 * @更新人: $Author: xq $
 * @更新时间: $Date: 2015-04-22 17:06:53 +0800 (Wed, 22 Apr 2015) $
 * @更新描述: TODO
 */
public class ContentFragment extends BaseFragment
{
    public static final String	TAG	= "ContentFragment";
	@ViewInject(R.id.content_pager)
	private NoScrollViewPager mPager;		// ViewPager
    @ViewInject(R.id.content_rg)
    private RadioGroup mRadioGroup;
    private int					mCurrentTab;


	private List<TabController>	mPagerDatas;

	@Override
	protected View initView()
	{
		// TextView tv = new TextView(mActivity);
		//
		// tv.setText("主页面");
		// tv.setTextSize(24);
		// tv.setGravity(Gravity.CENTER);
		//
		// return tv;

		View view = View.inflate(mActivity, R.layout.content, null);

		// 注入ViewUtils工具
		com.lidroid.xutils.ViewUtils.inject(this, view);

		return view;
	}

	@Override
	protected void initData()
	{
		// 数据初始化
		mPagerDatas = new ArrayList<TabController>();

		mPagerDatas.add(new HomeTabController(mActivity));// 首页
		mPagerDatas.add(new NewsCenterTabController(mActivity));
        mPagerDatas.add(new SmartServiceTabController(mActivity));
        mPagerDatas.add(new GovTabController(mActivity));
        mPagerDatas.add(new SettingTabController(mActivity));

		// 给ViewPager去加载数据
		mPager.setAdapter(new ContentPagerAdapter());// adapter ---> list<数据类型>

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i)
                {
                    case R.id.content_rb_home:
                        mCurrentTab = 0;
                        // 设置菜单不可以滑动
                        setSlidingMenuTouchEnable(false);
                        break;
                    case R.id.content_rb_news:
                        mCurrentTab = 1;
                        // 设置菜单可以滑动
                        setSlidingMenuTouchEnable(true);
                        break;
                    case R.id.content_rb_smart:
                        mCurrentTab = 2;
                        // 设置菜单可以滑动
                        setSlidingMenuTouchEnable(true);
                        break;
                    case R.id.content_rb_gov:
                        mCurrentTab = 3;
                        // 设置菜单可以滑动
                        setSlidingMenuTouchEnable(true);
                        break;
                    case R.id.content_rb_setting:
                        mCurrentTab = 4;
                        // 设置菜单不可以滑动
                        setSlidingMenuTouchEnable(false);
                        break;
                    default:
                        break;
                }

                // 设置ViewPager的选中的页面
                mPager.setCurrentItem(mCurrentTab);
            }
        });

	}

	class ContentPagerAdapter extends PagerAdapter
	{

		@Override
		public int getCount()
		{
			if (mPagerDatas != null) { return mPagerDatas.size(); }
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object)
		{
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
            Log.d(TAG, "加载了" + position);
			TabController controller = mPagerDatas.get(position);

			// 获得视图
			View rootView = controller.getRootView();
			container.addView(rootView);

			// 设置数据
			controller.initData();

			return rootView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			container.removeView((View) object);
		}

	}
    /**
     * 设置滑动菜单是否可以滑动
     */
    private void setSlidingMenuTouchEnable(boolean enable)
    {
        MainUI ui = (MainUI) mActivity;// 获取具体的宿主
        SlidingMenu menu = ui.getSlidingMenu();
        menu.setTouchModeAbove(enable ? SlidingMenu.TOUCHMODE_FULLSCREEN : SlidingMenu.TOUCHMODE_NONE);
    }
    public void switchMenu(int position)
    {
        TabController tabController = mPagerDatas.get(mCurrentTab);

        tabController.switchMenu(position);
    }
}
