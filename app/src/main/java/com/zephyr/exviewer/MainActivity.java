package com.zephyr.exviewer;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "ExViewer";
    private WebView mWebView;
    private TextView mWebViewState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.main_web);
        mWebViewState = (TextView) findViewById(R.id.web_state);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUserAgentString(getString(R.string.UA));


        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                shouldOverrideUrlLoading(view, request);
                return false;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                //Load HTML
                mWebViewState.setText("page finished");
                mWebView.loadUrl("javascript:window.htmlParser.onGotContent(document.documentElement.outerHTML, '" + url + "');");
                Log.d(TAG, "onPageFinished");
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mWebViewState.setText("page loading");
                Log.d(TAG, "onPageStarted");
            }
        });
        mWebView.addJavascriptInterface(this, "htmlParser");

        String url = "http://www.pixiv.net/";
        setCookie(url);
        mWebView.loadUrl(url);
    }


    @JavascriptInterface
    public void onGotContent(String html, String url) {
        Log.d(TAG, "get html: " + html);
    }

    private void setCookie(String url) {
//        CookieSyncManager

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
//        cookieManager.removeSessionCookie();//移除
//        cookieManager.setCookie(url, "PHPSESSID=6172982_aac6072ff7592ce1b18dd49dfb283a2d; expires=Tue, 21-Mar-2017 15:03:33 GMT; Max-Age=2592000; path=/; domain=.pixiv.net");
        cookieManager.setCookie(url, "module_orders_mypage=%5B%7B%22name%22%3A%22recommended_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22everyone_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22sensei_courses%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22spotlight%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22fanbox%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22featured_tags%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22contests%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22following_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22mypixiv_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22booth_follow_items%22%2C%22visible%22%3Atrue%7D%5D; expires=Mon, 19-Feb-2018 15:03:33 GMT; Max-Age=31536000; path=/; domain=.pixiv.net");
        cookieManager.flush();

    }
}
