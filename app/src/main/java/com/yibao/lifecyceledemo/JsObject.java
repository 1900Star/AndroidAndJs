package com.yibao.lifecyceledemo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;

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
//        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        mContext.startActivity(new Intent(mContext, Main2Activity.class));
        Log.d("lsp", "js 调用安卓的方法    " + msg);

    }
}
