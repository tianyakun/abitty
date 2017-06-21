package com.abitty.biz.impl;

import com.abitty.biz.MessageProcessBiz;
import com.abitty.dto.ResponseDto;
import com.abitty.dto.SendMessageRequestDto;
import com.abitty.entity.TblMessageInfo;
import com.abitty.enums.ExceptionEnum;
import com.abitty.service.MessageService;
import com.abitty.utils.EntityDTOUtil;
import com.abitty.utils.ParamChecker;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yak on 17/6/20.
 */
@Component
public class MessageProcessBizImpl implements MessageProcessBiz {

    private final static Logger logger = LoggerFactory.getLogger(MessageProcessBizImpl.class);

    @Autowired
    private MessageService messageService;

    public void sendMessage(SendMessageRequestDto requestDto, ResponseDto responseDto) {
        logger.info("消息发送请求，请求参数: {}", requestDto);
        try {
            //接口参数校验
            String constraintMessage = ParamChecker.getConstraintMessage(requestDto);
            if (!Strings.isNullOrEmpty(constraintMessage)) {
                logger.error("参数校验失败:{}", constraintMessage);
                responseDto.setRetCode(ExceptionEnum.PARAM_INVALID.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.PARAM_INVALID.getErrorMsg());
                return;
            }

            //Dto转Entity
            TblMessageInfo tblMessageInfo = EntityDTOUtil.sendMessageRequestDtoToTblMessageInfo(requestDto);

            //消息发送请求数据入库
            if (!messageService.saveRequestMessage(tblMessageInfo)) {
                logger.error("消息发送请求数据入库失败: {}", tblMessageInfo.getResultInfo());
                responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
                return;
            }

            //发送过程
            messageService.sendTransaction(tblMessageInfo);

            //根据发送结果更新数据
            messageService.updateSendResult(tblMessageInfo);

            //最终结果回填Dto
            responseDto.setRetCode(tblMessageInfo.getResultCode());
            responseDto.setRetMsg(tblMessageInfo.getResultInfo());
            responseDto.addAttribute("messageId", tblMessageInfo.getMessageId());

            logger.info("消息发送处理完成, 响应参数:{}", responseDto.toString());

        } catch (Exception e) {
            logger.error("消息发送处理系统异常", e);
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
    }

    public boolean checkValidateCode(String messageId, String validateCode) {
        try {
            Preconditions.checkNotNull(messageId, " messageId is null");
            Preconditions.checkNotNull(validateCode, "validateCode is null");

            return messageService.validateTransaction(messageId, validateCode);

        } catch (Exception e) {
            logger.error("验证码校验失败", e);
            return false;
        }
    }
}
