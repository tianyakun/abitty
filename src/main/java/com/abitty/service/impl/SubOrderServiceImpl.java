package com.abitty.service.impl;

import com.abitty.dao.TblSubOrderMapper;
import com.abitty.entity.TblSubOrder;
import com.abitty.service.SubOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yak on 17/7/1.
 */
@Component
public class SubOrderServiceImpl implements SubOrderService {

    @Autowired
    private TblSubOrderMapper tblSubOrderMapper;

    @Override
    public TblSubOrder getBySubOrderNo(String subOrderNo) {
        return tblSubOrderMapper.selectBySubOrderNo(subOrderNo);
    }
}
