package cn.ixuehu.smartpeking.base.tab;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import cn.ixuehu.smartpeking.base.TabController;

/**
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56.base.tab
 * @类名: HomeTabController
 * @创建者: 肖琦
 * @创建时间: 2015-4-22 下午4:43:41
 * @描述: 首页对应的controller
 * 
 * @svn版本: $Rev: 18 $
 * @更新人: $Author: xq $
 * @更新时间: $Date: 2015-04-22 17:06:53 +0800 (Wed, 22 Apr 2015) $
 * @更新描述: TODO
 */
public class HomeTabController extends TabController
{

	private TextView	tv;

	public HomeTabController(Context context) {
		super(context);
	}

	@Override
	protected View initContentView(Context context)
	{
		tv = new TextView(context);

		tv.setTextSize(24);
		tv.setGravity(Gravity.CENTER);
		tv.setTextColor(Color.RED);
		return tv;
	}

	@Override
	public void initData()
	{
		//设置menu按钮不可见
		mIbMenu.setVisibility(View.GONE);
		//设置Title
		mTvTitle.setText("智慧北京");
		//设置内容
		tv.setText("首页的内容");
	}
}
