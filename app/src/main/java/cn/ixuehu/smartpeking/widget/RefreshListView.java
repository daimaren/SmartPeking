package cn.ixuehu.smartpeking.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.ixuehu.smartpeking.R;

/**
 * 项目名：SmartPeking-master
 * 包名：cn.ixuehu.smartpeking.widget
 * Created by daimaren on 2016/1/14.
 */
public class RefreshListView extends ListView {
    private static final int STATE_PULL_DOWN_REFRESH = 0;                 // 下拉刷新状态
    private static final int STATE_RELEASE_REFRESH = 1;                   // 松开刷新状态
    private static final int STATE_REFRESHING = 2;                        // 正在刷新状态

    private int mCurrentState = STATE_PULL_DOWN_REFRESH;
    private LinearLayout mHeaderLayout;
    private View mCustomView;
    private View mRefreshView;

    private ImageView mIvArrow;
    private ProgressBar mProgressBar;
    private TextView mTvState;
    private TextView mTvTime;

    private int mDownX;
    private int mDownY;
    private int mRefreshHeight;
    private final String TAG = "RefreshListView";

    private RotateAnimation down2UpAnimation;
    private RotateAnimation up2DownAnimation;
    private int mPaddingTop;
    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderlayout();
        initAnimation();
    }

    public RefreshListView(Context context) {
        super(context);
        initHeaderlayout();
        initAnimation();
    }

    private void initAnimation() {
        // 由下往上的动画
        down2UpAnimation = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        down2UpAnimation.setDuration(300);
        down2UpAnimation.setFillAfter(true);

        up2DownAnimation = new RotateAnimation(-180, 0,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        up2DownAnimation.setDuration(300);
        up2DownAnimation.setFillAfter(true);
    }

    private void initHeaderlayout() {
        //给ListView添加头布局
        mHeaderLayout = (LinearLayout) View.inflate(getContext(), R.layout.refresh_header_layout, null);
        mRefreshView = mHeaderLayout.findViewById(R.id.refresh_header_refresh);
        mIvArrow = (ImageView) mHeaderLayout.findViewById(R.id.refresh_header_arrow);
        mProgressBar = (ProgressBar) mHeaderLayout.findViewById(R.id.refresh_header_progress);
        mTvState = (TextView) mHeaderLayout.findViewById(R.id.refresh_header_tv_state);
        mTvTime = (TextView) mHeaderLayout.findViewById(R.id.refresh_header_tv_time);
        this.addHeaderView(mHeaderLayout);
        //隐藏刷新部分
        mRefreshView.measure(0, 0);
        mRefreshHeight = mRefreshView.getMeasuredHeight();
        //Log.d(TAG, "刷新部分的高度:" + mRefreshHeight);
        mHeaderLayout.setPadding(0, - mRefreshHeight, 0, 0);

    }

    public void addCustomHeaderView(View view) {
        this.mCustomView = view;
        mHeaderLayout.addView(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) (ev.getX() + 0.5f);
                mDownY = (int) (ev.getY() + 0.5f);
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) (ev.getX() + 0.5f);
                int moveY = (int) (ev.getY() + 0.5f);
                int diffX = moveX - mDownX;
                int diffY = moveY - mDownY;
                if (mCurrentState == STATE_REFRESHING)
                {
                    break;
                }
                if (getFirstVisiblePosition() == 0) {
                    if (diffY > 0) {
                        //Log.d(TAG, "diffY:" + diffY);
                        //Log.d(TAG, "mMeasureHeight:" + mRefreshHeight);
                        mPaddingTop = diffY - mRefreshHeight;
                        mHeaderLayout.setPadding(0, mPaddingTop, 0, 0);

                        //为负数时代表刷新框还没有全部显示出来
                        if (mPaddingTop < 0 && mCurrentState != STATE_PULL_DOWN_REFRESH) {
                            //Log.d(TAG, "下拉刷新");
                            mCurrentState = STATE_PULL_DOWN_REFRESH;
                            refreshUI();
                        } else if (mPaddingTop >= 0 && mCurrentState != STATE_RELEASE_REFRESH) {
                            //Log.d(TAG, "释放刷新");
                            mCurrentState = STATE_RELEASE_REFRESH;
                            refreshUI();
                        }
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mDownY = 0;
                mDownX =0;
                //松开时如果是释放刷新就设置paddingTop为0
                if (mCurrentState == STATE_RELEASE_REFRESH)
                {
                    mCurrentState = STATE_REFRESHING;
                    refreshUI();
                }
                if (mCurrentState == STATE_PULL_DOWN_REFRESH)
                {

                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void refreshUI() {
        switch (mCurrentState) {
            case STATE_PULL_DOWN_REFRESH:
                Log.d(TAG, "refreshUI: 下拉刷新");
                //1.显示箭头
                mIvArrow.setVisibility(VISIBLE);
                mProgressBar.setVisibility(INVISIBLE);
                //2.箭头动画
                mIvArrow.startAnimation(up2DownAnimation);
                //3.显示松开刷树
                mTvState.setText("下拉刷新");
                break;
            case STATE_REFRESHING:
                Log.d(TAG, "refreshUI: 正在刷新");
                mIvArrow.clearAnimation();
                //1.显示箭头
                mIvArrow.setVisibility(INVISIBLE);
                mProgressBar.setVisibility(VISIBLE);
                //2.显示正在刷新
                mTvState.setText("正在刷新");
                break;
            case STATE_RELEASE_REFRESH:
                mIvArrow.setVisibility(VISIBLE);
                mProgressBar.setVisibility(INVISIBLE);
                mIvArrow.startAnimation(down2UpAnimation);
                mTvState.setText("释放刷新");
                break;
        }
    }
}
