package cn.ixuehu.smartpeking.base.tab;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import cn.ixuehu.smartpeking.MainUI;
import cn.ixuehu.smartpeking.base.MenuController;
import cn.ixuehu.smartpeking.base.TabController;
import cn.ixuehu.smartpeking.base.newscenter.InteractMenuController;
import cn.ixuehu.smartpeking.base.newscenter.NewsMenuController;
import cn.ixuehu.smartpeking.base.newscenter.PicMenuController;
import cn.ixuehu.smartpeking.base.newscenter.TopicMenuController;
import cn.ixuehu.smartpeking.bean.NewsCenterBean;
import cn.ixuehu.smartpeking.fragment.MenuFragment;
import cn.ixuehu.smartpeking.utils.Constans;

/**
 * Created by lenovo on 2016/1/4.
 */
public class NewsCenterTabController extends TabController{
    protected static final String TAG= "NewsCenterTabController";
    private FrameLayout mContainer;
    private List<MenuController> mMenuControllers;
    private List<NewsCenterBean.NewsCenterMenuBean>  mMenuDatas;
    public NewsCenterTabController(Context context) {
        super(context);
    }

    @Override
    protected View initContentView(Context context) {
        mContainer = new FrameLayout(context);
        return mContainer;
    }

    @Override
    public void initData() {
        mIbMenu.setVisibility(View.VISIBLE);
        mTvTitle.setText("新闻");
        HttpUtils utils = new HttpUtils();

        String url = Constans.NEWSCENTER_URL;
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String strResult = responseInfo.result;
                Log.d(TAG,"接口访问成功:" + strResult);
                //处理数据
                processData(strResult);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.d(TAG,"访问服务器失败:"+msg);
            }
        });
    }
    protected void processData(String json)
    {
        // 1.json解析 String ----> Object
        Gson gson = new Gson();
        // Class指的是要转化成的类型,javabean的类型
        NewsCenterBean bean = gson.fromJson(json, NewsCenterBean.class);
        mMenuDatas = bean.data;

        // 2. Model ---> View
        // 2-1.给菜单加载数据
        MenuFragment menuFragment = ((MainUI) mContext).getMenuFragment();
        menuFragment.setData(mMenuDatas);

        // 2-2.给自己的内容实体加载数据
        mMenuControllers = new ArrayList<MenuController>();

        for (int i = 0; i < mMenuDatas.size(); i++)
        {
            NewsCenterBean.NewsCenterMenuBean menuBean = mMenuDatas.get(i);
            int type = menuBean.type;

            switch (type)
            {
                case 1:
                    mMenuControllers.add(new NewsMenuController(mContext, menuBean));// 新闻菜单
                    break;
                case 10:
                    mMenuControllers.add(new TopicMenuController(mContext));// 专题菜单
                    break;
                case 2:
                    mMenuControllers.add(new PicMenuController(mContext));// 组图菜单
                    break;
                case 3:
                    mMenuControllers.add(new InteractMenuController(mContext));// 互动菜单
                    break;
                default:
                    break;
            }
        }
        // 设置默认加载第一个
        switchMenu(0);
    }

    @Override
    public void switchMenu(int position) {
        // 清空容器
        mContainer.removeAllViews();

        MenuController menuController = mMenuControllers.get(position);

        // 加载视图
        View rootView = menuController.getmRootView();
        mContainer.addView(rootView);

        // 设置title
        NewsCenterBean.NewsCenterMenuBean bean = mMenuDatas.get(position);
        mTvTitle.setText(bean.title);

        // 加载数据
        menuController.initData();
    }
}
