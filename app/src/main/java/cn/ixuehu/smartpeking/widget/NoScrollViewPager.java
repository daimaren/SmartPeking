package cn.ixuehu.smartpeking.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 项目名：SmartPeking
 * 包名：cn.ixuehu.smartpeking.widget
 * Created by daimaren on 2016/1/4.
 */
public class NoScrollViewPager extends LazyViewPager{
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //不消费
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //不拦截
        return false;
    }
}
