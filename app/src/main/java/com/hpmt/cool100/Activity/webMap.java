package com.hpmt.cool100.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hpmt.cool100.R;
import com.hpmt.cool100.Util.http.HttpGetData;
import com.hpmt.cool100.Util.tools.ShowAlertView;
import com.hpmt.cool100.base.BaseActivity;
import com.hpmt.cool100.config.MacrosConfig;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

public class webMap extends BaseActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_map);
        WebView webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/www/cameraTest.html");
        //在js中调用本地java方法
        webView.addJavascriptInterface(new JsInterface(this), "AndroidWebView");

        //添加客户端支持
        webView.setWebChromeClient(new WebChromeClient());
    }


    private class JsInterface {
        private Context mContext;

        public JsInterface(Context context) {
            this.mContext = context;
        }

        //在js中调用window.AndroidWebView.showInfoFromJs(name)，便会触发此方法。
        @JavascriptInterface
        public void showInfoFromJs(String name) {
            //跳转1.省市2.坐标
            String[] split = name.split("@");
           Intent Build1intent = new Intent();
            //Intent Build1intent = new Intent(webMap.this, addBuild.class);
            Build1intent.putExtra("city",split[0]);
            Build1intent.putExtra("lnglat",split[1]);
            //startActivity(Build1intent);
           setResult(10, Build1intent);
            finish();
        }

        @JavascriptInterface
        public void cancelJs() {
            finish();
        }
    }



}
