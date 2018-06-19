package com.taishan.swordsmanli.myapplication.ui.ios;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.taishan.swordsmanli.myapplication.R;

public class SizeIntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_intro);

        ((Toolbar) findViewById(R.id.toolbar)).setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SizeIntroActivity.this.finish();
            }
        });

        final SwitchView vSwitch1 = (SwitchView) findViewById(R.id.v_switch_1);

    }
}
