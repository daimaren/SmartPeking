package cn.ixuehu.smartpeking.base;

import android.content.Context;
import android.view.View;

/**
 * 项目名：SmartPeking
 * 包名：cn.ixuehu.smartpeking.base
 * Created by daimaren on 2016/1/4.
 */
public abstract class MenuController {
    protected View mRootView;
    protected Context mContext;

    public MenuController(Context context)
    {
        this.mContext = context;
        mRootView = initView(context);
    }
    /**
     * 初始化View
     */
    protected abstract View initView(Context context);
    /**
     * 获得根视图
     */
    public View getmRootView(){
        return mRootView;
    }
    /**
     * 读取上下文
     */
    public Context getContext(){
        return mContext;
    }
    /**
     * 初始化数据
     */
    public void initData(){

    }
}
