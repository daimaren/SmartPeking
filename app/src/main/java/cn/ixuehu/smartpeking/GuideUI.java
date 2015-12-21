package cn.ixuehu.smartpeking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import utils.CacheUtils;

public class GuideUI extends Activity implements View.OnClickListener,ViewPager.OnPageChangeListener {
    private ViewPager mPager;			// 页面中的Viewpager
    private List<ImageView> mPageDatas;		// 页面对应的数据
    private Button mBtnStart;			// 开始按钮
    private LinearLayout mContainerPoint;	// 静态点的容器
    private View			mFocusPoint;		// 动态的点
    private int			mPointSpace;		// 两点间的距离
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.guide);

        // 初始化View
        initView();
        // 初始化数据
        initData();
    }
    private void initView()
    {

        mPager = (ViewPager) findViewById(R.id.guide_pager);
        mBtnStart = (Button) findViewById(R.id.guide_btn_start);
        mContainerPoint = (LinearLayout) findViewById(R.id.guide_container_point);
        mFocusPoint = findViewById(R.id.guide_focus_point);
        mContainerPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mContainerPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mPointSpace = mContainerPoint.getChildAt(1).getLeft() - mContainerPoint.getChildAt(0).getLeft();
            }
        });
        // 给button设置点击事件
        mBtnStart.setOnClickListener(this);
    }
    private void initData()
    {
        int[] imgRes = new int[] {
                R.drawable.guide_1,
                R.drawable.guide_2,
                R.drawable.guide_3
        };

        // 初始化List数据
        mPageDatas = new ArrayList<ImageView>();

        ImageView iv;
        View point;
        for (int i = 0; i < imgRes.length; i++)
        {
            iv = new ImageView(this);
            iv.setImageResource(imgRes[i]);// 设置图片资源
            iv.setScaleType(ImageView.ScaleType.FIT_XY);// 设置图片填充
            // 添加到list中
            mPageDatas.add(iv);

            // 添加静态的点
            point = new View(this);
            point.setBackgroundResource(R.drawable.guide_point_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            if (i != 0)
            {
                params.leftMargin = 10;
            }

            mContainerPoint.addView(point, params);
        }
        // 给ViewPager设置数据
        mPager.setAdapter(new GuideAdapter());// adapter --> list<数据类型>
        // 监听ViewPager的滑动
        mPager.setOnPageChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnStart)
        {
            clickStart();
        }
    }
    private void clickStart()
    {
        // 设置已经开启过应用
        CacheUtils.setBoolean(this, WelcomeUI.KEY_FIRST_START, false);

        // 页面跳转
        Intent intent = new Intent(this, MainUI.class);
        startActivity(intent);

        // 结束自己
        finish();

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        int leftMargin = (int) (mPointSpace * positionOffset + position * mPointSpace + 0.5f);

        //RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) mFocusPoint.getLayoutParams();
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mFocusPoint.getLayoutParams();
        params.leftMargin = leftMargin;

        mFocusPoint.setLayoutParams(params);
    }

    @Override
    public void onPageSelected(int position) {
        mBtnStart.setVisibility(position == mPageDatas.size() - 1 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    class GuideAdapter extends PagerAdapter
    {

        @Override
        public int getCount()
        {
            if (mPageDatas != null) { return mPageDatas.size(); }
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
            // 展示ImageView
            ImageView iv = mPageDatas.get(position);

            // 将ImageView加到ViewPager中
            container.addView(iv);

            // 返还ImageView
            return iv;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }
    }
}
