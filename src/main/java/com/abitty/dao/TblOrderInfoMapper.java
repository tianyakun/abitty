package com.abitty.dao;

import com.abitty.entity.TblOrderInfo;

import java.util.List;

public interface TblOrderInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TblOrderInfo record);

    int insertSelective(TblOrderInfo record);

    TblOrderInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TblOrderInfo record);

    int updateByPrimaryKey(TblOrderInfo record);

    TblOrderInfo selectByOrderNo(String orderNo);

    List<TblOrderInfo> selectByUid(String uid);

    TblOrderInfo selectByPayId(String payId);
}