package com.yibao.lifecyceledemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.yibao.lifecyceledemo.h5.JsObject;
import com.yibao.lifecyceledemo.h5.Main2Activity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLifecycle().addObserver(new MyLifecyclerObserver());
        initView();
        loadHtml();
        initListener();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                mWebView.evaluateJavascript("javascript:changeText()", value -> {
                });
                break;
            case R.id.btn2:
//                mWebView.loadUrl("javascript:changeStyle()");
                mWebView.evaluateJavascript("javascript:changeStyle(\"" + "Smartisan" + "\")", value -> {
                });
                break;
            case R.id.btn3:
                // confirmP interceptP promptP
                mWebView.evaluateJavascript("javascript:promptP(\"" + "HTC" + "\")", value -> {
                });
                break;
            default:
                break;

        }
    }

    private void initListener() {
        mWebView.setWebChromeClient(new WebChromeClient() {
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

    }


    @SuppressLint("SetJavaScriptEnabled")
    private void loadHtml() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.loadUrl("file:///android_asset/dom.html");
        mWebView.addJavascriptInterface(new JsObject(this), "smartisan");
        mWebView.setWebChromeClient(new WebChromeClient());
    }

}
