package com.taishan.swordsmanli.myapplication.common;

import android.util.Log;

/**
 * Author：BorisLee
 * E-mail：lizt@toltech.cn
 * Date：2017/4/25
 */

public class iDongLog {

    public static final String TAG = "iDongTAG";

    private static final String HARDWARE = "<硬件交互日志>";

    private static final String OPERATION = "<操作日志>";

    private static final String DEBUG = "<调试日志>";

    public static void Hardware(String content){
        Log.e(TAG,HARDWARE+content);
    }

    public static void Operation(String content){
        Log.e(TAG,OPERATION+content);
    }

    public static void Debug(String content){
        Log.d(TAG,DEBUG+content);
    }

}
