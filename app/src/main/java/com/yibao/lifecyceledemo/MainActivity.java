package com.yibao.lifecyceledemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.yibao.lifecyceledemo.h5.JsObject;
import com.yibao.lifecyceledemo.h5.Main2Activity;
import com.yibao.lifecyceledemo.optization.TimeMonitorConfig;
import com.yibao.lifecyceledemo.optization.TimeMonitorManager;

import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private WebView mWebView;
    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START).recordingTimeTag("SplashActivity-onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLifecycle().addObserver(new MyLifecyclerObserver());
        initView();
        loadHtml();
        initListener();
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START).recordingTimeTag("SplashActivity-onCreate-Over");

    }

    @Override
    protected void onStart() {
        super.onStart();
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START).end("SplashActivity-onStart", false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
//                mWebView.evaluateJavascript("javascript:changeText()", value -> {
//                });
                break;
            case R.id.btn2:
//                mWebView.loadUrl("javascript:changeStyle()");
                mWebView.evaluateJavascript("javascript:changeStyle(\"" + "Smartisan" + "\")", value -> {
                });
                break;
            case R.id.btn3:
                // confirmP interceptP promptP
                mWebView.evaluateJavascript("javascript:promptP(\"" + "HTC" + "\")", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.d("lsp", " onReceiveValue   " + value);
                    }
                });
                break;
            default:
                break;

        }
    }

    private void initListener() {
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.d("lsp", " 加载进度  " + newProgress);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Js的Alert方法");
                builder.setMessage(message + " Android");//这个message就是alert传递过来的值
                builder.setPositiveButton("确定", (dialog, which) -> result.confirm());
                builder.show();
                //自己处理
                result.cancel();
                return true;
            }

            /**
             * Webview加载html中有confirm执行的时候，会回调这个方法
             * url:当前Webview显示的url
             * message：alert的参数值
             * JsResult：java将结果回传到js中
             */
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Js的Confirm方法");
                builder.setMessage(message);//这个message就是alert传递过来的值
                builder.setPositiveButton("确定", (dialog, which) -> {
                    //处理确定按钮，且通过jsresult传递，告诉js点击的是确定按钮
                    result.confirm();
                    startActivity(new Intent(MainActivity.this, Main2Activity.class));
                });
                builder.setNegativeButton("取消", (dialog, which) -> {
                    //处理取消按钮，且通过jsresult传递，告诉js点击的是取消按钮
                    result.cancel();

                });
                builder.show();
                //自己处理
                result.cancel();
                return true;
            }

            /**
             * Webview加载html中有prompt()执行的时候，会回调这个方法
             * url:当前Webview显示的url
             * message：alert的参数值
             *defaultValue就是prompt的第二个参数值，输入框的默认值
             * JsPromptResult：java将结果重新回传到js中
             */
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
                                      final JsPromptResult result) {

                Log.d("lsp", "url   " + url);
                Log.d("lsp", "message    " + message);
                Log.d("lsp", "defaultValue    " + defaultValue);
                Uri uri = Uri.parse(message);
                String scheme = uri.getScheme();
                if (scheme != null) {
                    if (scheme.equals("ps")) {
                        if (uri.getAuthority().equals("web")) {
                            Set<String> queryParameterNames = uri.getQueryParameterNames();
                            for (String queryParameterName : queryParameterNames) {
                                Log.d("lsp", " arg   :   " + uri.getQueryParameter(queryParameterName));
                            }
                        } else {
                            Log.d("lsp", "协议出错。 Authority  " + uri.getAuthority());
                        }
                    } else {
                        Log.d("lsp", "协议出错。scheme " + scheme);
                    }

                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Js的Prompt方法");
                builder.setMessage(message);//这个message就是alert传递过来的值
                //添加一个EditText
                final EditText editText = new EditText(MainActivity.this);
                editText.setText(defaultValue);//这个就是prompt 输入框的默认值
                if (defaultValue.length() > 0) {
                    editText.setSelection(defaultValue.length());
                }
                //添加到对话框
                builder.setView(editText);
                builder.setPositiveButton("确定", (dialog, which) -> {
                    //获取edittext的新输入的值
                    String newValue = editText.getText().toString().trim();
                    //处理确定按钮了，且过jsresult传递，告诉js点击的是确定按钮(参数就是输入框新输入的值，我们需要回传到js中)
                    result.confirm(newValue);
//                    startActivity(new Intent(MainActivity.this, Main2Activity.class));
                    mWebView.evaluateJavascript("javascript:androidChangeText(\"" + newValue + "\")", value -> {
                    });
                });
                builder.setNegativeButton("取消", (dialog, which) -> {
                    //处理取消按钮，且过jsresult传递，告诉js点击的是取消按钮
                    result.cancel();

                });
                builder.show();
                //自己处理
                result.cancel();
                return true;
            }
        });

    }

    private void initView() {
        mWebView = findViewById(R.id.web_view);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        mIv = findViewById(R.id.iv);
        String imgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584080473919&di=446c01077c9beed8505a6cdc0de3a84d&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Fa62f2e4dd07b75ee2ca1ffe576ece585aaf6439c6c83-4DS67H_fw658";
//        Glide.with(this).load(imgUrl).into(mIv);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void loadHtml() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        String url = "http://192.168.0.233:8080/dom.html";

//        mWebView.loadUrl(url);
        mWebView.loadUrl("file:///android_asset/dom.html");
//        mWebView.addJavascriptInterface(new JsObject(this), "smartisan");
//        mWebView.setWebChromeClient(new WebChromeClient());
    }

}
