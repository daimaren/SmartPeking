package cn.ixuehu.smartpeking;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 项目名：SmartPeking-master
 * 包名：cn.ixuehu.smartpeking
 * Created by daimaren on 2016/1/16.
 */
public class DetailUI extends Activity{
    public static final String KEY_URL = "url";
    @ViewInject(R.id.detail_iv_back)
    private ImageView mIvBack;
    @ViewInject(R.id.detail_iv_share)
    private ImageView				mIvShare;

    @ViewInject(R.id.detail_iv_textsize)
    private ImageView				mIvTextSize;

    @ViewInject(R.id.detail_wv)
    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        //初始化View
        initView();
    }
    private void initView()
    {
        ViewUtils.inject(this);
    }
    private void initData()
    {
        String url = getIntent().getStringExtra(KEY_URL);
        //页面显示
        mWebView.loadUrl(url);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });
    }
    @OnClick(R.id.detail_iv_back)
    private void OnBack(View view)
    {
        finish();
    }
    @OnClick(R.id.detail_iv_textsize)
    private void OnTextSize(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置字体");
        CharSequence[] items = new CharSequence[]{
                "超大号字体",
                "大号字体",
                "正常字体",
                "小号字体",
                "超小号字体"
        };
        builder.setSingleChoiceItems()


    }
}
