package cn.ixuehu.smartpeking;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import cn.ixuehu.smartpeking.fragment.ContentFragment;
import cn.ixuehu.smartpeking.fragment.MenuFragment;

public class MainUI extends SlidingFragmentActivity {
    private static final String	TAG_CONTENT	= "content";
    private static final String	TAG_MENU	= "menu";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_container);

        // 2. 设置菜单的布局
        setBehindContentView(R.layout.menu_container);

        // 菜单的实例
        SlidingMenu mMenu = getSlidingMenu();
        // 3.设置slidingMeun的mode
        mMenu.setMode(SlidingMenu.LEFT);

        // 4.如果有右菜单
        // mMenu.setMode(SlidingMenu.LEFT_RIGHT);
        // mMenu.setSecondaryMenu(int Res);//设置右菜单

        // 4.设置菜单宽度
        mMenu.setBehindWidth(150);
        // mMenu.setBehindOffset(150);

        // 5.设置Touch Mode Above
        mMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        // // 6.设置Touch Mode behind
        // mMenu.setTouchModeBehind(SlidingMenu.TOUCHMODE_FULLSCREEN);

        // 加载菜单和主页
        initFragment();
    }
    private void initFragment()
    {
        FragmentManager manager = getSupportFragmentManager();

        // 1.开启事务
        FragmentTransaction transaction = manager.beginTransaction();

        // 加载切换主页面
        transaction.replace(R.id.main_container_content, new ContentFragment(), TAG_CONTENT);

        // 加载切换菜单页面
        transaction.replace(R.id.main_container_menu, new MenuFragment(), TAG_MENU);

        // 提交事务
        transaction.commit();
    }
    /**
     * 获取菜单fragment
     *
     * @return
     */
    public MenuFragment getMenuFragment()
    {
        FragmentManager manager = getSupportFragmentManager();
        return (MenuFragment) manager.findFragmentByTag(TAG_MENU);
    }

    /**
     * 获取内容fragment
     *
     * @return
     */
    public ContentFragment getContentFragment()
    {
        FragmentManager manager = getSupportFragmentManager();
        return (ContentFragment) manager.findFragmentByTag(TAG_CONTENT);
    }
}
