package com.taishan.swordsmanli.myapplication.model.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Author：BorisLee
 * E-mail：lizt@toltech.cn
 * Date：2017/4/18
 */
@Entity
public class users {
    @Id(autoincrement = true)
    private long id;
    private String name;
    private String age;
    private String sex;
    private String salary;
    @Generated(hash = 1268152936)
    public users(long id, String name, String age, String sex, String salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.salary = salary;
    }
    @Generated(hash = 1023868587)
    public users() {
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
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getSalary() {
        return this.salary;
    }
    public void setSalary(String salary) {
        this.salary = salary;
    }
}
