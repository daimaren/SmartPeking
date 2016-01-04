package cn.ixuehu.smartpeking.base.tab;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import cn.ixuehu.smartpeking.base.TabController;

/**
 * 项目名：SmartPeking
 * 包名：cn.ixuehu.smartpeking.base.tab
 * Created by daimaren on 2016/1/4.
 */
public class SettingTabController extends TabController{
    private TextView tv;
    public SettingTabController(Context context) {
        super(context);
    }

    @Override
    protected View initContentView(Context context) {
        tv = new TextView(context);
        tv.setTextSize(24);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.RED);
        return tv;
    }

    @Override
    public void initData() {
        // 设置menu按钮是否可见
        mIbMenu.setVisibility(View.GONE);
        // 设置title
        mTvTitle.setText("设置");

        // 设置内容数据
        tv.setText("设置的内容");
    }
}
