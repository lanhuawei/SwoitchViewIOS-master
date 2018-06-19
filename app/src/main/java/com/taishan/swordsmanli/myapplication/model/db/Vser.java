package com.taishan.swordsmanli.myapplication.model.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Author：BorisLee
 * E-mail：lizt@toltech.cn
 * Date：2017/4/21
 */
@Entity
public class Vser {
    @Id
    private long id;
    private String name;
    @Transient
    private int tempUsageCount;
    @Generated(hash = 891412219)
    public Vser(long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 255676334)
    public Vser() {
    }



    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
