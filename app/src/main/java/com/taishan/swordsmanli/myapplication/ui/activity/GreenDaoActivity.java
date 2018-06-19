package com.taishan.swordsmanli.myapplication.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.taishan.swordsmanli.myapplication.R;
import com.taishan.swordsmanli.myapplication.common.iDongApplication;
import com.taishan.swordsmanli.myapplication.model.db.Vser;
import com.taishan.swordsmanli.myapplication.model.db.users;
import com.taishan.swordsmanli.myapplication.utils.LogUtils;
import com.usher.greendao_demo.greendao.gen.VserDao;
import com.usher.greendao_demo.greendao.gen.usersDao;

import java.util.List;

/**
 * Author：BorisLee
 * E-mail：lizt@toltech.cn
 * Date：2017/4/19
 */

public class GreenDaoActivity extends Activity implements View.OnClickListener{
    private EditText et_name,et_age,et_sex,et_salary;
    private String et_Name,et_Age,et_Sex,et_Salary;
    private Button btn_insert,btn_query,btn_all,btn_update,btn_delet;
    private List<users> usersList;
    private List<Vser> vserList;
    private usersDao userDaos;
    private VserDao vserDao;
    private Vser vser;
    private users mUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greendao);
        setOnListener();

        userDaos = iDongApplication.getApplication().getDaoSession().getUsersDao();
        vserDao = iDongApplication.getApplication().getDaoSession().getVserDao();
    }

    private void setOnListener(){
        et_name  = (EditText) findViewById(R.id.et_name);
        et_age  = (EditText) findViewById(R.id.et_age);
        et_sex  = (EditText) findViewById(R.id.et_sex);
        et_salary  = (EditText) findViewById(R.id.et_salary);

        et_Name = et_name.getText().toString().trim();
        et_Age = et_name.getText().toString().trim();
        et_Sex = et_name.getText().toString().trim();
        et_Salary = et_name.getText().toString().trim();

        btn_insert = (Button) findViewById(R.id.btn_insert);
        btn_query = (Button) findViewById(R.id.btn_query);
        btn_all = (Button) findViewById(R.id.btn_all);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_delet = (Button) findViewById(R.id.btn_delete);

        btn_insert.setOnClickListener(this);
        btn_query.setOnClickListener(this);
        btn_all.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_delet.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_insert:
                insertData();
                break;
            case R.id.btn_query:
                queryData();
                break;
            case R.id.btn_all:
                break;
            case R.id.btn_update:
                break;
            case R.id.btn_delete:
                break;
        }

    }

    /**
     * 增
     */
    private void insertData(){
        Vser vser = new Vser(0,"BorisLee");
        vserDao.insert(vser);
    }

    /**
     * 查
     */
    private void queryData(){
    vserList = vserDao.loadAll();
        for (int i =0 ;i<vserList.size();i++){
            LogUtils.d(vserList.get(i).getName());
        }
    }
}
