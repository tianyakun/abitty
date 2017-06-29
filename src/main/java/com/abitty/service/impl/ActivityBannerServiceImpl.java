package com.abitty.service.impl;

import com.abitty.dao.TblActivityBannerMapper;
import com.abitty.entity.TblActivityBanner;
import com.abitty.service.ActivityBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yak on 17/6/29.
 */
@Component
public class ActivityBannerServiceImpl implements ActivityBannerService {

    @Autowired
    private TblActivityBannerMapper tblActivityBannerMapper;

    @Override
    public List<TblActivityBanner> getAllEffective() {
        return tblActivityBannerMapper.selectAllEffective();
    }
}
