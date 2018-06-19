package com.taishan.swordsmanli.myapplication.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hiflying.smartlink.ISmartLinker;
import com.hiflying.smartlink.OnSmartLinkListener;
import com.hiflying.smartlink.SmartLinkedModule;
import com.hiflying.smartlink.v3.SnifferSmartLinker;
import com.hiflying.smartlink.v7.MulticastSmartLinker;
import com.taishan.swordsmanli.myapplication.R;

/**
 * Author：BorisLee
 * E-mail：lizt@toltech.cn
 * Date：2017/4/25
 */

public class CustomizedActivity extends BaseActivity implements View.OnClickListener,OnSmartLinkListener{

    public static final String EXTRA_SMARTLINK_VERSION = "EXTRA_SMARTLINK_VERSION";
    private static final String TAG = "CustomizedActivity";
    protected EditText mSsidEditText;
    protected EditText mPasswordEditText;
    protected Button mStartButton,btn_stop;
    protected ISmartLinker mSnifferSmartLinker;
    private boolean mIsConncting = false;
    protected Handler mViewHandler = new Handler();
    private BroadcastReceiver mWifiChangedReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_customized);
        initData();
        initView();
        setOnClick();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSnifferSmartLinker.setOnSmartLinkListener(null);
        mSnifferSmartLinker.stop();
        mIsConncting = false;
    }

    private void initData(){
        int smartLinkVersion = getIntent().getIntExtra(EXTRA_SMARTLINK_VERSION, 3);
        if(smartLinkVersion == 7) {
            mSnifferSmartLinker = MulticastSmartLinker.getInstance();
        }else {
            mSnifferSmartLinker = SnifferSmartLinker.getInstance();
        }

        mWifiChangedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (networkInfo != null && networkInfo.isConnected()) {
                    mSsidEditText.setText(getSSid());
                    mPasswordEditText.requestFocus();
                    mStartButton.setEnabled(true);
                }else {
                    mSsidEditText.setText(getString(R.string.hiflying_smartlinker_no_wifi_connectivity));
                    mSsidEditText.requestFocus();
                    mStartButton.setEnabled(false);
                    dismissCustomProgressDialog();
                }
            }
        };
        registerReceiver(mWifiChangedReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void initView(){
        mStartButton = (Button) findViewById(R.id.btn_start);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        mSsidEditText = (EditText) findViewById(R.id.et_ssid);
        mPasswordEditText = (EditText) findViewById(R.id.et_password);

        mSsidEditText.setText(getSSid());
    }


    private void setOnClick(){
        mStartButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:
                if(!mIsConncting){
//设置要配置的ssid 和pswd
                    try {
                        mSnifferSmartLinker.setOnSmartLinkListener(CustomizedActivity.this);
//开始 smartLink
                        mSnifferSmartLinker.start(getApplicationContext(), mPasswordEditText.getText().toString().trim(),
                                mSsidEditText.getText().toString().trim());
                        mIsConncting = true;
                        showCustomProgressDialog();
                    } catch (Exception e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btn_stop:
                dismissCustomProgressDialog();
                break;
        }
    }



    @Override
    public void onLinked(final SmartLinkedModule smartLinkedModule) {
// TODO Auto-generated method stub

        Log.w(TAG, "onLinked");
        mViewHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), getString(R.string.hiflying_smartlinker_new_module_found, smartLinkedModule.getMac(), smartLinkedModule.getModuleIP()),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCompleted() {
        Log.w(TAG, "onCompleted");
        mViewHandler.post(new Runnable() {
            @Override
            public void run() {
               // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), getString(R.string.hiflying_smartlinker_completed),
                        Toast.LENGTH_SHORT).show();
                dismissCustomProgressDialog();
                mIsConncting = false;
            }
        });
    }

    @Override
    public void onTimeOut() {
        Log.w(TAG, "onTimeOut");
        mViewHandler.post(new Runnable() {
            @Override
            public void run() {
               // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), getString(R.string.hiflying_smartlinker_timeout),
                        Toast.LENGTH_SHORT).show();
                dismissCustomProgressDialog();
                mIsConncting = false;
            }
        });
    }
    
    private String getSSid(){
        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        if(wm != null){
            WifiInfo wi = wm.getConnectionInfo();
            if(wi != null){
                String ssid = wi.getSSID();
                if(ssid.length()>2 && ssid.startsWith("\"") && ssid.endsWith("\"")){
                    return ssid.substring(1,ssid.length()-1);
                }else{
                    return ssid;
                }
            }
        }
        return "";
    }


}
