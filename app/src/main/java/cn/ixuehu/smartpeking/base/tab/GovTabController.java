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
public class GovTabController extends TabController{
    private TextView tv;
    public GovTabController(Context context) {
        super(context);
    }

    @Override
    protected View initContentView(Context context) {
        tv = new TextView(context);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.RED);
        tv.setTextSize(24);
        return tv;
    }

    @Override
    public void initData() {
        mIbMenu.setVisibility(View.VISIBLE);
        mTvTitle.setText("人口管理");
        tv.setText("政务的内容");
    }
}
