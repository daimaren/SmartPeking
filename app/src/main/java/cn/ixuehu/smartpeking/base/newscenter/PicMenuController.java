package cn.ixuehu.smartpeking.base.newscenter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import cn.ixuehu.smartpeking.base.MenuController;

/**
 * 项目名：SmartPeking
 * 包名：cn.ixuehu.smartpeking.base.newscenter
 * Created by daimaren on 2016/1/4.
 */
public class PicMenuController extends MenuController{
    private TextView tv;
    public PicMenuController(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        tv.setText("组图菜单对应的页面");
    }

    @Override
    protected View initView(Context context) {
        tv = new TextView(mContext);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(24);
        tv.setTextColor(Color.RED);
        return tv;
    }
}
