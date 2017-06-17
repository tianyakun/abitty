package com.abitty.controller;

import com.abitty.dto.OrderCreateRequestDto;
import com.abitty.dto.ResponseDto;
import com.abitty.entity.TblOrderInfo;
import com.abitty.entity.TblSubOrder;
import com.abitty.enums.ExceptionEnum;
import com.abitty.service.AddressService;
import com.abitty.service.OrderService;
import com.abitty.utils.Constants;
import com.abitty.utils.ParamChecker;
import com.abitty.utils.Sequence;
import com.abitty.vo.OrderDetailVo;
import com.abitty.vo.OrderInfoVo;
import com.abitty.vo.SubOrderVo;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/create")
    @ResponseBody
    public ResponseDto createOrder(final OrderCreateRequestDto requestDto) {
        logger.info("创建订单请求 requestDto={}", requestDto);

        ResponseDto responseDto = new ResponseDto();

        try {
            //参数校验
            String constraintMessage = ParamChecker.getConstraintMessage(requestDto);
            if (!Strings.isNullOrEmpty(constraintMessage)) {
                logger.error("参数校验失败:{}", constraintMessage);
                responseDto.setRetCode(ExceptionEnum.PARAM_INVALID.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.PARAM_INVALID.getErrorMsg());
            } else {
                TblOrderInfo tblOrderInfo = new TblOrderInfo();
                tblOrderInfo.setOrderNo("order" + Sequence.next());
                tblOrderInfo.setUid(requestDto.getUid());
                tblOrderInfo.setProductNo(requestDto.getProductNo());
                tblOrderInfo.setTotalQuantity(requestDto.getTotalQuantity());
                tblOrderInfo.setTotalAmount(requestDto.getTotalAmount());
                tblOrderInfo.setStatus(Constants.OrderState.INITIAL);
                tblOrderInfo.setDeliveryType(requestDto.getDeliveryType());//todo
//                tblOrderInfo.setDeliveryTime(requestDto.getDeliveryTime());
                tblOrderInfo.setSubQuantity(requestDto.getSubQuantity());
                tblOrderInfo.setTotalSub(requestDto.getTotalSub());
                tblOrderInfo.setCreateTime(new Date());
                tblOrderInfo.setAddressId(requestDto.getAddressId());
                tblOrderInfo.setUserNumber(requestDto.getUserNumber());
                tblOrderInfo.setRemark(requestDto.getRemark());

                orderService.add(tblOrderInfo);

                responseDto.addAttribute("orderNo", tblOrderInfo.getOrderNo());
                responseDto.addAttribute("totalAmount", tblOrderInfo.getTotalAmount());

                responseDto.setRetCode(ExceptionEnum.SUCCESS.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.SUCCESS.getErrorMsg());
            }
        } catch (Exception e) {
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }

        logger.info("创建订单返回: {}", responseDto);
        return responseDto;
    }

    @RequestMapping(value = "/confirmPay")
    @ResponseBody
    public ResponseDto confirmPay(final String orderNo) {
        logger.info("订单确认支付请求 orderNo={}", orderNo);

        ResponseDto responseDto = new ResponseDto();

        try {

            if (Strings.isNullOrEmpty(orderNo)) {
                responseDto.setRetCode(ExceptionEnum.PARAM_INVALID.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.PARAM_INVALID.getErrorCode());
            } else {
                orderService.confirmPay(orderNo, responseDto);
            }

        } catch (Exception e) {
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
        }

        logger.info("订单确认支付返回 responseDto={}", responseDto);
        return responseDto;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public ResponseDto getOrderList(final String uid) {
        logger.info("获取订单列表请求 uid={}", uid);

        ResponseDto responseDto = new ResponseDto();

        try {

            if (Strings.isNullOrEmpty(uid)) {
                responseDto.setRetCode(ExceptionEnum.PARAM_INVALID.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.PARAM_INVALID.getErrorCode());
            } else {
                List<TblOrderInfo> tblOrderInfoList = orderService.getAllByUid(uid);

                List<OrderInfoVo> orderInfoVoList = buildOrderInfoVoList(tblOrderInfoList);

                responseDto.addAttribute("list", orderInfoVoList);

                Map<String, Object> data = Maps.newHashMap();
                data.put("list", tblOrderInfoList);

                responseDto.setRetCode(ExceptionEnum.SUCCESS.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.SUCCESS.getErrorCode());
            }

        } catch (Exception e) {
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
        }

        logger.info("获取订单列表请求返回 responseDto={}", responseDto);
        return responseDto;
    }

    @RequestMapping(value = "/detail")
    @ResponseBody
    public ResponseDto getOrderDetail(final String orderNo) {

        logger.info("获取订单详情请求 orderNo={}", orderNo);

        ResponseDto responseDto = new ResponseDto();

        try {

            if (Strings.isNullOrEmpty(orderNo)) {
                responseDto.setRetCode(ExceptionEnum.PARAM_INVALID.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.PARAM_INVALID.getErrorCode());
            } else {
                TblOrderInfo tblOrderInfo = orderService.queryOrderInfo(orderNo);

                OrderDetailVo orderDetailVo = buildOrderDetailVo(tblOrderInfo);

                responseDto.addAttribute("detail", orderDetailVo);

                responseDto.setRetCode(ExceptionEnum.SUCCESS.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.SUCCESS.getErrorCode());
            }

        } catch (Exception e) {
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
        }

        logger.info("获取订单列表请求返回 responseDto={}", responseDto);
        return responseDto;
    }

    private OrderDetailVo buildOrderDetailVo(TblOrderInfo tblOrderInfo) {
        OrderInfoVo orderInfoVo = buildOrderInfoVo(tblOrderInfo);
        List<SubOrderVo> subOrderVoList = buildSubVoList(tblOrderInfo.getSubOrderList());

        OrderDetailVo detailVo = new OrderDetailVo();
        detailVo.setOrderInfo(orderInfoVo);
        detailVo.setSubList(subOrderVoList);
        return detailVo;

    }

    private List<SubOrderVo> buildSubVoList(List<TblSubOrder> subOrderList) {
        List<SubOrderVo> subOrderVoList = Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(subOrderList)) {
            subOrderVoList = Lists.transform(subOrderList, new Function<TblSubOrder, SubOrderVo>() {
                public SubOrderVo apply(TblSubOrder input) {
                    SubOrderVo vo = new SubOrderVo();
                    vo.setSubOrderNo(input.getSubOrderNo());
                    vo.setOrderNo(input.getOrderNo());
                    vo.setProductNo(input.getProductNo());
                    vo.setQuantity(input.getQuantity());
                    vo.setStatus(input.getStatus());
                    vo.setRecvName(input.getRecvName());
                    vo.setPhone(input.getPhone());
                    vo.setAddress(input.getAddress());
                    vo.setDeliveryTime(input.getDeliveryTime());
                    vo.setUsedPercent(input.getUsedPercent());
                    vo.setRemark(input.getRemark());
                    return vo;
                }
            });
        }

        return subOrderVoList;
    }

    private List<OrderInfoVo> buildOrderInfoVoList(List<TblOrderInfo> tblOrderInfoList) {
        List<OrderInfoVo> orderInfoVoList = Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(tblOrderInfoList)) {
            orderInfoVoList = Lists.transform(tblOrderInfoList, new Function<TblOrderInfo, OrderInfoVo>() {
                public OrderInfoVo apply(TblOrderInfo input) {
                    return buildOrderInfoVo(input);
                }
            });
        }

        return orderInfoVoList;
    }


    private OrderInfoVo buildOrderInfoVo(TblOrderInfo input) {
        if (input != null) {
            OrderInfoVo vo = new OrderInfoVo();
            vo.setOrderNo(input.getOrderNo());
            vo.setUid(input.getUid());
            vo.setProductNo(input.getProductNo());
            vo.setTotalQuantity(input.getTotalQuantity());
            vo.setTotalAmount(input.getTotalAmount());
            vo.setStatus(input.getStatus());
            vo.setDeliveryType(input.getDeliveryType());
            vo.setSubQuantity(input.getSubQuantity());
            vo.setTotalSub(input.getTotalSub());
            vo.setFinishSub(input.getFinishSub());
            vo.setNextSub(input.getNextSub());
            vo.setNextSubTime(input.getNextSubTime());
            vo.setUserNumber(input.getUserNumber());
            vo.setRemark(input.getRemark());
            return vo;
        }
        return null;
    }
}
