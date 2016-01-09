package cn.ixuehu.smartpeking.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.viewpagerindicator.TabPageIndicator;

/**
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56.widget
 * @类名: TouchedTabPageIndicator
 * @创建者: 肖琦
 * @创建时间: 2015-4-23 下午4:29:25
 * @描述: 不允许父容器去拦截touch事件 的View
 * 
 * @svn版本: $Rev: 37 $
 * @更新人: $Author: xq $
 * @更新时间: $Date: 2015-04-23 16:40:24 +0800 (Thu, 23 Apr 2015) $
 * @更新描述: TODO
 */
public class TouchedTabPageIndicator extends TabPageIndicator
{

	public TouchedTabPageIndicator(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public TouchedTabPageIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		setFocusable(true);
		// 希望父容器不去拦截touch事件
		getParent().requestDisallowInterceptTouchEvent(true);

		return super.dispatchTouchEvent(ev);
	}
}
