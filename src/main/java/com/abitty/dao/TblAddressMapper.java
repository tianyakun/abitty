package com.abitty.dao;

import com.abitty.entity.TblAddress;

import java.util.List;

public interface TblAddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TblAddress record);

    int insertSelective(TblAddress record);

    TblAddress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TblAddress record);

    int updateByPrimaryKey(TblAddress record);

    List<TblAddress> selectAllByUid(String uid);
}