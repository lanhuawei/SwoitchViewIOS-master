package com.taishan.swordsmanli.myapplication.ui.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.taishan.swordsmanli.myapplication.common.iDongLog;
import com.taishan.swordsmanli.myapplication.ui.view.CustomProgressDialog;

/**
 * Author：BorisLee
 * E-mail：lizt@toltech.cn
 * Date：2017/4/25
 */

public abstract class BaseActivity extends Activity {

    private CustomProgressDialog mCustomProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public CustomProgressDialog showCustomProgressDialog(){
        mCustomProgressDialog = new CustomProgressDialog(this);
        mCustomProgressDialog.show();
        return mCustomProgressDialog;
    }

    public void dismissCustomProgressDialog(){
        if (mCustomProgressDialog!=null&&mCustomProgressDialog.isShowing())mCustomProgressDialog.dismiss();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        iDongLog.Debug("BaseActivity onConfigurationChanged");
    }
}
