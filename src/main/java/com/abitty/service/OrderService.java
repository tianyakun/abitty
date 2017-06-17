package com.abitty.service;

import com.abitty.dto.ResponseDto;
import com.abitty.entity.TblOrderInfo;

import java.util.List;

/**
 * Created by yak on 17/6/16.
 */
public interface OrderService {
    void add(TblOrderInfo tblOrderInfo);

    void confirmPay(String orderNo, ResponseDto responseDto);

    List<TblOrderInfo> getAllByUid(String uid);

    TblOrderInfo queryOrderInfo(String orderNo);
}
