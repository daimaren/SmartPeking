package cn.ixuehu.smartpeking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

import utils.CacheUtils;

public class WelcomeUI extends Activity {
    private final static long 	ANIMATION_DURATION	= 1500;
    private static final String	TAG					    = "WelcomeUI";
    public static final String	KEY_FIRST_START	    = "is_first_start"; // 标记是否是第一次打开的key
    private View mRootView;								// 根视图
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_ui);

        // 初始化View

        mRootView = findViewById(R.id.welcome_root);

        // 1. 旋转动画
        RotateAnimation rotateAnimation = new RotateAnimation(0, // 起始角度
                360,// 终止的角度
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        rotateAnimation.setDuration(ANIMATION_DURATION);// 设置动画的时长
        rotateAnimation.setFillAfter(true);

        // 2. 缩放动画(安装比例缩放)
        ScaleAnimation scaleAnimation = new ScaleAnimation(0f,
                1f,
                0f,
                1f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnimation.setDuration(ANIMATION_DURATION);// 设置动画的时长
        scaleAnimation.setFillAfter(true);

        // 3. 透明度的动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(ANIMATION_DURATION);// 设置动画的时长
        alphaAnimation.setFillAfter(true);

        // 动画集合
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(rotateAnimation);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);

        mRootView.startAnimation(set);
        // 监听动画执行
        set.setAnimationListener(new WelcomeAnimationListener());
    }
    private void doNavgation()
    {
        // 页面跳转
        // 根据情况进行页面跳转
        // 如果是第一次打开应用程序，那么就进入引导页面，否则进入主页
        boolean isFirstStart = CacheUtils.getBoolean(this, KEY_FIRST_START, true);
        if (isFirstStart)
        {
            Log.d(TAG, "进入引导页面");
            Intent intent = new Intent(this, GuideUI.class);
            startActivity(intent);
        }
        else
        {
            Log.d(TAG, "进入主页面");
            Intent intent = new Intent(this, MainUI.class);
            startActivity(intent);
        }

        finish();
    }
    class WelcomeAnimationListener implements Animation.AnimationListener
    {

        protected static final long	ANIMATION_DELAY	= 500;

        @Override
        public void onAnimationEnd(Animation animation)
        {
            // 动画结束后，等待一会，再进行页面跳转

            new Thread(new Runnable() {

                @Override
                public void run()
                {
                    // 等待
                    try
                    {
                        Thread.sleep(ANIMATION_DELAY);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                    // 做页面跳转
                    doNavgation();
                }
            }).start();

        }

        @Override
        public void onAnimationStart(Animation animation)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationRepeat(Animation animation)
        {
            // TODO Auto-generated method stub

        }

    }
}
