package com.abitty.service;

import com.abitty.entity.TblUser;

/**
 * Created by yak on 17/6/17.
 */
public interface UserService {
    TblUser getUserByUid(String uid);

    int update(TblUser tblUser);

    int add(TblUser tblUser);
}
