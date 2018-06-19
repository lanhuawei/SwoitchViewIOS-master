package com.taishan.swordsmanli.myapplication.ui.activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.taishan.swordsmanli.myapplication.R;
import com.taishan.swordsmanli.myapplication.ui.interfaces.IView;
import com.taishan.swordsmanli.myapplication.utils.CountDownTimerUtils;
import com.taishan.swordsmanli.myapplication.utils.LogUtils;
import com.taishan.swordsmanli.myapplication.utils.MyTimerTask;
import com.taishan.swordsmanli.myapplication.utils.ToastUtil;
import com.taishan.swordsmanli.myapplication.utils.Utils;

import java.util.Timer;

public class MainActivity extends Activity implements IView{
    private TextView tv_Time;
    private Button btn_Start,btn_Cancel,btn_Query,btn_Dialog;
    private CountDownTimerUtils mCountDownTimerUtils;
    private Timer timer = new Timer();
    private MyTimerTask timerTask = new MyTimerTask();


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    tv_Time.setText(msg.arg1+"");
                    break;
                case 13:
                    int i = msg.arg1;
                    i--;
                    if (i==0){
                        LogUtils.d("handler倒计时结束"+i+"系统时间"+Utils.getTime());
                    }else {
                        LogUtils.d("handler倒计时结束"+i+"系统时间"+Utils.getTime());
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(13,i,0),100);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main222);
        init();
    }

    private void init(){
        mCountDownTimerUtils = new CountDownTimerUtils(60000,100);
        mCountDownTimerUtils.setjiekou(this);
        timerTask.setiView(this);
        tv_Time = (TextView) findViewById(R.id.tv_time);
        btn_Start = (Button) findViewById(R.id.btn_start);
        btn_Cancel = (Button) findViewById(R.id.btn_cancel);
        btn_Query = (Button) findViewById(R.id.btn_query);
        btn_Dialog = (Button) findViewById(R.id.btn_dialog);

        btn_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int time = Utils.getCurrentTimeStamp();
                Log.d("系统时间",Utils.getTime()+"");
//                mHandler.sendMessage(mHandler.obtainMessage(13,100,0));
//                reStart();
                timer.schedule(timerTask, 0, 100);
            }
        });

        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCancel();
                mCountDownTimerUtils.setFrequency(10);
                timer.cancel();
            }
        });

        btn_Query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,GreenDaoActivity.class));
            }
        });

        btn_Dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RecyclerActivity.class));
            }
        });
    }

    /**
     * 取消倒计时
     */
    private void onCancel(){
    ToastUtil.showToast(this,"取消倒计时");
    if (mCountDownTimerUtils!=null)mCountDownTimerUtils.cancel();
    }

    /**
     * 重新开始倒计时
     */
    private void reStart(){
    ToastUtil.showToast(this,"开启倒计时");
    if (mCountDownTimerUtils!=null)mCountDownTimerUtils.start();
    }

    @Override
    public void fetchedData(int i) {
//        tv_Time.setText(i+"");
    }

    @Override
    public void fetchedData2(int j) {
        mHandler.sendMessage(mHandler.obtainMessage(0,j,0));
        if (j==0){
            timer.cancel();
        }
    }
}
