package com.taishan.swordsmanli.myapplication.ui.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.taishan.swordsmanli.myapplication.R;

/**
 * Author：BorisLee
 * E-mail：lizt@toltech.cn
 * Date：2017/4/25
 */

public class CustomProgressDialog extends ProgressDialog {
    private AnimationDrawable mAnimation;
    private Context mContext;
    private ImageView iv_Dialog;
    private int mResid;

    public CustomProgressDialog(Context context) {
        this(context, "加载中");
    }

    public CustomProgressDialog(Context context, String content) {
        this(context,content, R.drawable.fram_dialoge);
    }

    public CustomProgressDialog(Context context, String content, int id) {
        super(context);
        this.mContext = context;
        this.mResid = id;
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setScreenBrightness();
        initView();
        initData();
    }

    private void initData(){
        iv_Dialog.setBackgroundResource(mResid);
        mAnimation = (AnimationDrawable) iv_Dialog.getBackground();
        iv_Dialog.post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();
            }
        });
    }

    private void initView(){
        setContentView(R.layout.dialog_progress);
        iv_Dialog = (ImageView) findViewById(R.id.iv_loading);
    }

    private void setScreenBrightness(){
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        /**
         *  此处设置亮度值。dimAmount代表黑暗数量，也就是昏暗的多少，设置为0则代表完全明亮。
         *  范围是0.0到1.0
         */
        lp.dimAmount= (float) 0.6;
        window.setAttributes(lp);
    }
}
