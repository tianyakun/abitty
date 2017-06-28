package com.abitty.service.impl;

import com.abitty.dao.TblFeedbackMapper;
import com.abitty.entity.TblFeedback;
import com.abitty.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yak on 17/6/28.
 */
@Component
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private TblFeedbackMapper tblFeedbackMapper;

    @Override
    public void addFeedback(TblFeedback tblFeedback) {
        tblFeedbackMapper.insertSelective(tblFeedback);
    }
}
