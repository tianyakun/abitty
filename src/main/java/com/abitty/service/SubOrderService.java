package com.abitty.service;

import com.abitty.entity.TblSubOrder;

/**
 * Created by yak on 17/7/1.
 */
public interface SubOrderService {
    TblSubOrder getBySubOrderNo(String nextSub);
}
