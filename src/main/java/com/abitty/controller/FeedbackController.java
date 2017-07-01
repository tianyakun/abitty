package com.abitty.controller;

import com.abitty.dto.AddressDto;
import com.abitty.dto.ResponseDto;
import com.abitty.entity.TblAddress;
import com.abitty.entity.TblFeedback;
import com.abitty.entity.TblUser;
import com.abitty.enums.ExceptionEnum;
import com.abitty.service.AddressService;
import com.abitty.service.FeedbackService;
import com.abitty.utils.ParamChecker;
import com.abitty.utils.Sequence;
import com.google.common.base.Strings;
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
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yak on 17/6/15.
 */
@Controller
@RequestMapping(value = "/feedback")
public class FeedbackController {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping(value = "")
    @ResponseBody
    public ResponseDto add(final String content, final HttpSession httpSession) {

        logger.info("新增反馈 content={}", content);

        ResponseDto responseDto = new ResponseDto();

        try {
            //参数校验
            if (Strings.isNullOrEmpty(content)) {
                logger.error("新增反馈内容为空");
                responseDto.setRetCode(ExceptionEnum.PARAM_INVALID.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.PARAM_INVALID.getErrorMsg());
                responseDto.setChineseMsg(ExceptionEnum.PARAM_INVALID.getChineseMessage());
                return responseDto;
            }

            TblUser tblUser = (TblUser)httpSession.getAttribute("user");

            TblFeedback tblFeedback = new TblFeedback();
            tblFeedback.setFeedbackNo(String.valueOf(Sequence.next()));
            if (tblUser!=null) {
                tblFeedback.setUid(tblUser.getUid());
            }
            tblFeedback.setContent(content);
            tblFeedback.setCreateTime(new Date());

            feedbackService.addFeedback(tblFeedback);

            responseDto.setRetCode(ExceptionEnum.SUCCESS.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SUCCESS.getErrorMsg());
            responseDto.setChineseMsg(ExceptionEnum.SUCCESS.getChineseMessage());
            return responseDto;
        } catch (Exception e) {
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
            responseDto.setChineseMsg(ExceptionEnum.SYSTEM_ERROR.getChineseMessage());
            return responseDto;
        }
    }
}
