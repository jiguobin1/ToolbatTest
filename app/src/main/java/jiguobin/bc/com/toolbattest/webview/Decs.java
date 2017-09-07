package jiguobin.bc.com.toolbattest.webview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import jiguobin.bc.com.toolbattest.R;

public class Decs extends AppCompatActivity {
    private android.webkit.WebView webView;
    private String url;
    private String icon;
    private String title;
    private ProgressDialog progress;
    private CollapsingToolbarLayout coll_title;
    private ImageView image_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decs);
        coll_title= (CollapsingToolbarLayout) findViewById(R.id.Coll_title);
        image_icon= (ImageView) findViewById(R.id.image_icon);
        initWV();
        WebListener();
    }
    private void initWV(){
        progress=new ProgressDialog(Decs.this);
        webView = (android.webkit.WebView) findViewById(R.id.decs);
        Intent intent = getIntent();
        //获取从解析页面传入的url
        url = intent.getStringExtra("url");
        icon = intent.getStringExtra("image_icon");
        title = intent.getStringExtra("title");
        Glide.with(Decs.this).load(icon).into(image_icon);
        coll_title.setTitle(title);
        //加载url
        webView.loadUrl(url);
        Log.e("TAG","----------"+url);
        WebSettings settings = webView.getSettings();
        //支持JavaScript
        settings.setJavaScriptEnabled(true);
    }
    private void WebListener(){
        webView.setWebViewClient(new WebViewClient(){
            //使用当前的webview打开
            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                return false;
            }
//            //页面加载时
//            @Override
//            public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                progress.setTitle("网页提示：");
//                progress.setMessage("正在加载中请稍后......");
//                progress.show();
//            }
//
//            @Override
//            public void onPageFinished(android.webkit.WebView view, String url) {
//                progress.dismiss();
//                super.onPageFinished(view, url);
//            }
        });
    }

}