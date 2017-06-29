package com.abitty.dao;

import com.abitty.entity.TblActivityBanner;

import java.util.List;

public interface TblActivityBannerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TblActivityBanner record);

    int insertSelective(TblActivityBanner record);

    TblActivityBanner selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TblActivityBanner record);

    int updateByPrimaryKey(TblActivityBanner record);

    List<TblActivityBanner> selectAllEffective();
}