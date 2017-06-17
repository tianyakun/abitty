package com.abitty.dao;

import com.abitty.entity.TblProduct;

import java.util.List;

public interface TblProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TblProduct record);

    int insertSelective(TblProduct record);

    TblProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TblProduct record);

    int updateByPrimaryKey(TblProduct record);

    List<TblProduct> selectAllPublish();

    List<TblProduct> selectAllPublishByCatalogNo(String catalogNo);

    TblProduct selectByProductNo(String productNo);
}