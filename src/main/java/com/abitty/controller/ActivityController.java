package com.abitty.controller;

import com.abitty.dto.AddressDto;
import com.abitty.dto.ResponseDto;
import com.abitty.entity.TblActivityBanner;
import com.abitty.entity.TblAddress;
import com.abitty.entity.TblOrderInfo;
import com.abitty.enums.ExceptionEnum;
import com.abitty.service.ActivityBannerService;
import com.abitty.service.AddressService;
import com.abitty.utils.ParamChecker;
import com.abitty.vo.ActivityBannerVo;
import com.abitty.vo.OrderInfoVo;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yak on 17/6/15.
 */
@Controller
@RequestMapping(value = "/activity")
public class ActivityController {

    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityBannerService activityBannerService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public ResponseDto list() {

        logger.info("获取活动banner列表");

        ResponseDto responseDto = new ResponseDto();

        try {
            List<TblActivityBanner> tblActivityBannerList = activityBannerService.getAllEffective();

            List<ActivityBannerVo> activityBannerVoList = buildActivityBannerVoList(tblActivityBannerList);

            responseDto.addAttribute("list", activityBannerVoList);

            responseDto.setRetCode(ExceptionEnum.SUCCESS.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SUCCESS.getErrorCode());
        } catch (Exception e) {
            logger.error("获取活动banner列表异常", e);
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
        }

        logger.info("获取活动banner列表返回 responseDto={}", responseDto);
        return responseDto;
    }

    private List<ActivityBannerVo> buildActivityBannerVoList(List<TblActivityBanner> tblActivityBannerList) {
        List<ActivityBannerVo> activityBannerVoList = Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(tblActivityBannerList)) {
            return Lists.transform(tblActivityBannerList, new Function<TblActivityBanner, ActivityBannerVo>() {
                @Override
                public ActivityBannerVo apply(TblActivityBanner input) {
                    ActivityBannerVo vo = new ActivityBannerVo();
                    vo.setActivityNo(input.getActivityNo());
                    vo.setDescription(input.getDescription());
                    vo.setIcon(input.getIcon());
                    vo.setRedirectUrl(input.getRedirectUrl());
                    return vo;
                }
            });
        }

        return activityBannerVoList;
    }

}
