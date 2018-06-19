package com.taishan.swordsmanli.myapplication.utils;

import com.taishan.swordsmanli.myapplication.ui.interfaces.IView;

import java.util.TimerTask;

/**
 * Author：BorisLee
 * E-mail：lizt@toltech.cn
 * Date：2017/4/17
 */

public class MyTimerTask extends TimerTask {
    private int frequency = 600;
    private IView iView;
    public void setiView(IView iView){
        this.iView=iView;
    }
    @Override
    public void run() {
        frequency--;
//        LogUtils.d("timer倒计时"+frequency);
        LogUtils.d("timer倒计时结束"+frequency+"系统时间"+Utils.getTime());
        iView.fetchedData2(frequency);
    }
}
