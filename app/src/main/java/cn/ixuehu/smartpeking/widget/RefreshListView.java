package cn.ixuehu.smartpeking.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.ixuehu.smartpeking.R;

/**
 * 项目名：SmartPeking-master
 * 包名：cn.ixuehu.smartpeking.widget
 * Created by daimaren on 2016/1/14.
 */
public class RefreshListView extends ListView implements ListView.OnScrollListener{
    private static final int STATE_PULL_DOWN_REFRESH = 0;                 // 下拉刷新状态
    private static final int STATE_RELEASE_REFRESH = 1;                   // 松开刷新状态
    private static final int STATE_REFRESHING = 2;                        // 正在刷新状态

    private int mCurrentState = STATE_PULL_DOWN_REFRESH;
    private LinearLayout mHeaderLayout;
    private LinearLayout mFooterLayout;
    private View mCustomView;
    private View mRefreshView;

    private ImageView mIvArrow;
    private ProgressBar mProgressBar;
    private TextView mTvState;
    private TextView mTvTime;

    private int mDownX;
    private int mDownY;
    private int mRefreshHeight;
    private int mFooterHeight;
    private final String TAG = "RefreshListView";

    private RotateAnimation down2UpAnimation;
    private RotateAnimation up2DownAnimation;
    private int mPaddingTop;

    private OnRefreshListener	mListener;
    private boolean isLoadMore;
    private boolean noMore;
    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderlayout();
        initFooterlayout();
        initAnimation();
    }

    public RefreshListView(Context context) {
        super(context);
        initHeaderlayout();
        initFooterlayout();
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
        mHeaderLayout.setPadding(0, -mRefreshHeight, 0, 0);

    }
    private void initFooterlayout()
    {
        mFooterLayout = (LinearLayout) View.inflate(getContext(), R.layout.refresh_footer_layout, null);
        this.addFooterView(mFooterLayout);
        //初始隐藏起来
        mFooterLayout.measure(0, 0);
        mFooterHeight = mFooterLayout.getMeasuredHeight();
        mFooterLayout.setPadding(0, -mFooterHeight, 0, 0);

        //设置滚动的监听
        this.setOnScrollListener(this);

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
                    //释放刷新
                    mCurrentState = STATE_REFRESHING;
                    refreshUI();
                    //调用接口，通知正在刷新
                    if (mListener != null)
                    {
                        mListener.onRefreshing();
                    }
                }
                if (mCurrentState == STATE_PULL_DOWN_REFRESH)
                {
                    //下拉刷新
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void refreshUI() {
        switch (mCurrentState) {
            case STATE_PULL_DOWN_REFRESH:
                //Log.d(TAG, "refreshUI: 下拉刷新");
                //1.显示箭头
                mIvArrow.setVisibility(VISIBLE);
                mProgressBar.setVisibility(INVISIBLE);
                //2.箭头动画
                mIvArrow.startAnimation(up2DownAnimation);
                //3.显示松开刷树
                mTvState.setText("下拉刷新");
                break;
            case STATE_REFRESHING:
                //Log.d(TAG, "refreshUI: 正在刷新");
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
    public void setOnRefreshListener(OnRefreshListener lintener)
    {
        this.mListener = lintener;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        //最后一个是FooterView,ListView最后一个可见时
        int lastVisiblePosition = getLastVisiblePosition();
        int count = getAdapter().getCount() ;
        Log.d(TAG, "lastVisiblePosition: " + lastVisiblePosition);
        Log.d(TAG, "count: " + count);
        if (lastVisiblePosition == count - 1
                && (i == OnScrollListener.SCROLL_STATE_FLING
                || i == OnScrollListener.SCROLL_STATE_IDLE))
        {
            if (!isLoadMore && !noMore)
            {
                //显示查看更多
                mFooterLayout.setPadding(0,0,0,0);
                setSelection(count + 2);
                if (mListener != null)
                {
                    mListener.onLoadMore();
                }
                isLoadMore = true;
            }

        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    public interface OnRefreshListener
    {
        //正在刷新时的回调
        void onRefreshing();
        void onLoadMore();
    }
    public void setRefreshFinish()
    {
        // 默认有更多
        setRefreshFinish(false);
    }
    public void setTime(long time)
    {
        mTvTime.setText(getDataString(time));
    }
    public void setRefreshFinish(boolean noMore)
    {
        //设置刷新时间
        long time = System.currentTimeMillis();
        mTvTime.setText(getDataString(time));
        if (isLoadMore)
        {
            mFooterLayout.setPadding(0,- mFooterHeight,0,0);
            this.noMore = noMore;
            isLoadMore = false;
        }
        else
        {
            this.noMore = false;
            //状态回到下拉刷新
            mCurrentState = STATE_PULL_DOWN_REFRESH;
            //更新UI
            refreshUI();
        }
    }
    private String getDataString(long time)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(new Date(time));
    }

}
