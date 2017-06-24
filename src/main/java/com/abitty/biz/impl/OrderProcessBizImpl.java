package com.abitty.biz.impl;

import com.abitty.biz.OrderProcessBiz;
import com.abitty.dto.OrderConfirmRequestDto;
import com.abitty.dto.ResponseDto;
import com.abitty.entity.TblAddress;
import com.abitty.entity.TblOrderInfo;
import com.abitty.enums.ExceptionEnum;
import com.abitty.service.OrderService;
import com.abitty.utils.EntityDTOUtil;
import com.abitty.utils.ParamChecker;
import com.abitty.wechat.WechatPayProxy;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yak on 17/6/24.
 */
@Component
public class OrderProcessBizImpl implements OrderProcessBiz {

    private final static Logger logger = LoggerFactory.getLogger(OrderProcessBizImpl.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private WechatPayProxy wechatPayProxy;

    @Override
    public void confirmOrder(OrderConfirmRequestDto requestDto, ResponseDto responseDto) {
        logger.info("订单确认请求: {}", requestDto);
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
            TblAddress tblAddress = EntityDTOUtil.OrderConfirmRequestDtoToTblAddress(requestDto);
            TblOrderInfo tblOrderInfo = EntityDTOUtil.OrderConfirmRequestDtoToTblOrderInfo(requestDto);

            //消息发送请求数据入库
            if (!orderService.saveRequestOrder(tblOrderInfo, tblAddress)) {
                logger.error("订单请求数据入库失败");
                responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
                return;
            }

            //请求微信下单支付过程
            wechatPayProxy.paySubmit(tblOrderInfo);

            //根据发送结果更新数据
            orderService.recievePayinfo(tblMessageInfo);

            //最终结果回填Dto
//            responseDto.setRetCode(tblMessageInfo.getResultCode());
//            responseDto.setRetMsg(tblMessageInfo.getResultInfo());
//            responseDto.addAttribute("messageId", tblMessageInfo.getMessageId());

            logger.info("消息发送处理完成, 响应参数:{}", responseDto.toString());

        } catch (Exception e) {
            logger.error("消息发送处理系统异常", e);
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }
    }
}
