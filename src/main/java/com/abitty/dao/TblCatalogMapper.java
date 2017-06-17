package com.abitty.dao;

import com.abitty.entity.TblCatalog;

import java.util.List;

public interface TblCatalogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TblCatalog record);

    int insertSelective(TblCatalog record);

    TblCatalog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TblCatalog record);

    int updateByPrimaryKey(TblCatalog record);

    List<TblCatalog> selectAll();

    TblCatalog selectByCatalogNo(String catalogNo);
}