package com.abitty.service;

import com.abitty.dto.ResponseDto;
import com.abitty.entity.TblAddress;
import com.abitty.entity.TblOrderInfo;

import java.util.List;

/**
 * Created by yak on 17/6/16.
 */
public interface OrderService {
    void add(TblOrderInfo tblOrderInfo);

    List<TblOrderInfo> getAllByUid(String uid);

    TblOrderInfo queryOrderInfo(String orderNo);

    boolean saveRequestOrder(TblOrderInfo tblOrderInfo, TblAddress tblAddress);

    void recievePayinfo(TblOrderInfo tblOrderInfo);

    TblOrderInfo getByPayId(String payId);

    boolean paySuccess(TblOrderInfo tblOrderInfo);

    List<TblOrderInfo> getSuccessOrderByUid(String uid);
}
