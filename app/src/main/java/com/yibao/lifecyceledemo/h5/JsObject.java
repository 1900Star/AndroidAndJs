package com.yibao.lifecyceledemo.h5;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * @author luoshipeng
 * createDate：2019/9/17 0017 15:55
 * className   JsObject
 * Des：TODO
 */
public class JsObject {
    private Context mContext;

    public JsObject(Context context) {
        mContext = context;
    }

    @JavascriptInterface
    public void hellowWorld(String msg) {
        mContext.startActivity(new Intent(mContext, Main2Activity.class));
        Log.d("lsp", "js 调用安卓的方法    " + msg);
    }

    @JavascriptInterface
    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        Log.d("lsp", "js 调用安卓的方法    " + msg);
    }
}
