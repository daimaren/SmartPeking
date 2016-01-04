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
public class SmartServiceTabController extends TabController{
    private TextView tv;
    public SmartServiceTabController(Context context) {
        super(context);
    }

    @Override
    protected View initContentView(Context context) {
        tv = new TextView(context);

        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(24);
        tv.setTextColor(Color.RED);
        return tv;
    }

    @Override
    public void initData() {
        mIbMenu.setVisibility(View.GONE);
        mTvTitle.setText("生活");
        tv.setText("智慧服务的内容");
    }
}
