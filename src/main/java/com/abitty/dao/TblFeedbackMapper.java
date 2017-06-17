package com.abitty.dao;

import com.abitty.entity.TblFeedback;

public interface TblFeedbackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TblFeedback record);

    int insertSelective(TblFeedback record);

    TblFeedback selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TblFeedback record);

    int updateByPrimaryKey(TblFeedback record);
}