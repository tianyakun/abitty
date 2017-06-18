package com.abitty.vo;

import java.util.Date;

/**
 * Created by yak on 17/6/18.
 */
public class UserVo {

    private String uid;
    private String gender;
    private String birthday;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserVo{");
        sb.append("uid='").append(uid).append('\'');
        sb.append(", gender='").append(gender).append('\'');
        sb.append(", birthday=").append(birthday);
        sb.append('}');
        return sb.toString();
    }
}
