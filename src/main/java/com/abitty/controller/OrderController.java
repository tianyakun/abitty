package com.abitty.controller;

import com.abitty.dto.AddressDto;
import com.abitty.dto.OrderAddRequestDto;
import com.abitty.dto.ResponseDto;
import com.abitty.entity.TblAddress;
import com.abitty.entity.TblOrderInfo;
import com.abitty.enums.ExceptionEnum;
import com.abitty.service.OrderService;
import com.abitty.utils.Constants;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yak on 17/6/15.
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/newOrder")
    @ResponseBody
    public Map<String, Object> newOrder(final OrderAddRequestDto requestDto) {
        Map<String, Object> resultMap = Maps.newHashMap();

        try {
            //参数校验
            String constraintMessage = ParamChecker.getConstraintMessage(requestDto);
            if (!Strings.isNullOrEmpty(constraintMessage)) {
                logger.error("参数校验失败:{}", constraintMessage);
                resultMap.put("retCode", ExceptionEnum.PARAM_INVALID.getErrorCode());
                resultMap.put("retMsg", ExceptionEnum.PARAM_INVALID.getErrorMsg());
                return resultMap;
            }

            TblOrderInfo tblOrderInfo = new TblOrderInfo();
            tblOrderInfo.setOrderNo("order" + Sequence.next());
            tblOrderInfo.setUid(requestDto.getUid());
            tblOrderInfo.setProductNo(requestDto.getProductNo());
            tblOrderInfo.setTotalQuantity(requestDto.getTotalQuantity());
            tblOrderInfo.setTotalAmount(requestDto.getTotalAmount());
            tblOrderInfo.setStatus(Constants.OrderState.INITIAL);
            tblOrderInfo.setDeliveryType(requestDto.getDeliveryType());//todo
            tblOrderInfo.setDeliveryTime(requestDto.getDeliveryTime());
            tblOrderInfo.setSubQuantity(requestDto.getSubQuantity());
            tblOrderInfo.setTotalSub(requestDto.getTotalSub());
            tblOrderInfo.setCreateTime(new Date());
            tblOrderInfo.setAddressId(requestDto.getAddressId());
            tblOrderInfo.setUserNumber(requestDto.getUserNumber());

            orderService.add(tblOrderInfo);

            Map<String, Object> data = Maps.newHashMap();
            data.put("orderNo", tblOrderInfo.getOrderNo());
            data.put("totalAmount", tblOrderInfo.getTotalAmount());

            resultMap.put("retCode", ExceptionEnum.SUCCESS.getErrorCode());
            resultMap.put("retMsg", ExceptionEnum.SUCCESS.getErrorMsg());
            resultMap.put("data", data);

            return resultMap;

        } catch (Exception e) {
            resultMap.put("retCode", ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            resultMap.put("retMsg", ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
            return resultMap;
        }
    }

    @RequestMapping(value = "/confirmPay")
    @ResponseBody
    public ResponseDto confirmPay(HttpServletRequest request) {
        ResponseDto responseDto = new ResponseDto();

        try {
            String orderNo = request.getParameter("orderNo");

            logger.info("confirmPay orderNo={}", orderNo);

            if (Strings.isNullOrEmpty(orderNo)) {
                responseDto.setRetCode(ExceptionEnum.PARAM_INVALID.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.PARAM_INVALID.getErrorCode());
                return responseDto;
            } else {
                orderService.confirmPay(orderNo, responseDto);
                return responseDto;
            }

        } catch (Exception e) {
            responseDto.setRetCode(ExceptionEnum.PARAM_INVALID.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.PARAM_INVALID.getErrorCode());
            return responseDto;
        }
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> getOrderList(HttpServletRequest request) {
        Map<String, Object> resultMap = Maps.newHashMap();

        try {
            String uid = request.getParameter("uid");

            if (Strings.isNullOrEmpty(uid)) {
                resultMap.put("retCode", ExceptionEnum.PARAM_INVALID.getErrorCode());
                resultMap.put("retMsg", ExceptionEnum.PARAM_INVALID.getErrorMsg());
                return resultMap;
            } else {
                List<TblOrderInfo> tblOrderInfoList = orderService.getAllByUid(uid);

                Map<String, Object> data = Maps.newHashMap();
                data.put("list", tblOrderInfoList);

                resultMap.put("data", data);
                resultMap.put("retCode", ExceptionEnum.SUCCESS.getErrorCode());
                resultMap.put("retMsg", ExceptionEnum.SUCCESS.getErrorMsg());

                return resultMap;
            }

        } catch (Exception e) {
            resultMap.put("retCode", ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            resultMap.put("retMsg", ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
            return resultMap;
        }
    }


    @RequestMapping(value = "/item")
    @ResponseBody
    public Map<String, Object> getOrderDetail(HttpServletRequest request) {

        Map<String, Object> resultMap = Maps.newHashMap();

        try {
            String orderNo = request.getParameter("orderNo");

            if (Strings.isNullOrEmpty(orderNo)) {
                resultMap.put("retCode", ExceptionEnum.PARAM_INVALID.getErrorCode());
                resultMap.put("retMsg", ExceptionEnum.PARAM_INVALID.getErrorMsg());
                return resultMap;
            } else {
                TblOrderInfo tblOrderInfo = orderService.queryOrderInfo(orderNo);

                Map<String, Object> data = Maps.newHashMap();
                data.put("item", tblOrderInfo);

                resultMap.put("data", data);
                resultMap.put("retCode", ExceptionEnum.SUCCESS.getErrorCode());
                resultMap.put("retMsg", ExceptionEnum.SUCCESS.getErrorMsg());

                return resultMap;
            }

        } catch (Exception e) {
            resultMap.put("retCode", ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            resultMap.put("retMsg", ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
            return resultMap;
        }
    }
}
