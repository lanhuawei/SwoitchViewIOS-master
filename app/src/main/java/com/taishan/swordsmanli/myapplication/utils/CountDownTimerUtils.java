package com.taishan.swordsmanli.myapplication.utils;

import android.os.CountDownTimer;

import com.taishan.swordsmanli.myapplication.ui.interfaces.IView;

/**
 * Author：BorisLee
 * E-mail：lizt@toltech.cn
 * Date：2017/4/17
 */

public class CountDownTimerUtils extends CountDownTimer {
    private int frequency = 600;
    private IView iView  ;

    public CountDownTimerUtils(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public void setjiekou(IView iView){
        this.iView=iView;
    }

    public void setFrequency(int frequency){
        this.frequency=frequency;
        }

    @Override
    public void onTick(long l) {
    frequency--;
    LogUtils.d("倒计时开始"+frequency);
    iView.fetchedData(frequency);
    }

    @Override
    public void onFinish() {
    frequency--;
    LogUtils.d("倒计时结束"+frequency);
    iView.fetchedData(frequency);
    frequency=600;
    }
}
