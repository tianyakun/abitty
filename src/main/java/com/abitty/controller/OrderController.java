package com.abitty.controller;

import com.abitty.biz.OrderProcessBiz;
import com.abitty.dto.OrderConfirmRequestDto;
import com.abitty.dto.ResponseDto;
import com.abitty.entity.*;
import com.abitty.enums.ExceptionEnum;
import com.abitty.service.AddressService;
import com.abitty.service.OrderService;
import com.abitty.service.ProductService;
import com.abitty.constant.AbittyConstants;
import com.abitty.service.SubOrderService;
import com.abitty.utils.IpAddrUtil;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
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
    private SubOrderService subOrderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private OrderProcessBiz orderProcessBiz;

    @RequestMapping(value = "/confirm")
    @ResponseBody
    public ResponseDto createOrder(final OrderConfirmRequestDto requestDto, final HttpServletRequest httpServletRequest, final HttpSession httpSession) {
        logger.info("订单确认请求 requestDto={}", requestDto);

        ResponseDto responseDto = new ResponseDto();

        try {
            TblUser tblUser = (TblUser) httpSession.getAttribute("user");

            requestDto.setUid(tblUser.getUid());

            String ip = IpAddrUtil.getIpAddr(httpServletRequest);
            requestDto.setIp(ip);

            orderProcessBiz.confirmOrder(requestDto, responseDto);

        } catch (Exception e) {
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
            responseDto.setChineseMsg(ExceptionEnum.SYSTEM_ERROR.getChineseMessage());
        }

        logger.info("订单确认返回: {}", responseDto);
        return responseDto;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public ResponseDto getOrderList(final HttpSession httpSession) {

        TblUser tblUser = (TblUser) httpSession.getAttribute("user");
        String uid = tblUser.getUid();
        logger.info("获取订单列表请求 uid={}", uid);

        ResponseDto responseDto = new ResponseDto();

        try {

            if (Strings.isNullOrEmpty(uid)) {
                responseDto.setRetCode(ExceptionEnum.PARAM_INVALID.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.PARAM_INVALID.getErrorCode());
                responseDto.setChineseMsg(ExceptionEnum.PARAM_INVALID.getChineseMessage());
            } else {
                List<TblOrderInfo> tblOrderInfoList = orderService.getSuccessOrderByUid(uid);

                List<OrderInfoVo> orderInfoVoList = buildOrderInfoVoList(tblOrderInfoList);

                logger.info(orderInfoVoList.toString());

                responseDto.addAttribute("list", orderInfoVoList);

//                Map<String, Object> data = Maps.newHashMap();
//                data.put("list", tblOrderInfoList);

                responseDto.setRetCode(ExceptionEnum.SUCCESS.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.SUCCESS.getErrorCode());
                responseDto.setChineseMsg(ExceptionEnum.SUCCESS.getChineseMessage());
            }

        } catch (Exception e) {
            logger.error("获取订单列表异常", e);
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setChineseMsg(ExceptionEnum.SYSTEM_ERROR.getChineseMessage());
        }

        logger.info("获取订单列表请求返回 responseDto={}", responseDto);
        return responseDto;
    }

    @RequestMapping(value = "/detail/{orderNo}")
    @ResponseBody
    public ResponseDto getOrderDetail(@PathVariable("orderNo") final String orderNo) {

        logger.info("获取订单详情请求 orderNo={}", orderNo);

        ResponseDto responseDto = new ResponseDto();

        try {

            if (Strings.isNullOrEmpty(orderNo)) {
                responseDto.setRetCode(ExceptionEnum.PARAM_INVALID.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.PARAM_INVALID.getErrorCode());
                responseDto.setChineseMsg(ExceptionEnum.PARAM_INVALID.getChineseMessage());
            } else {
                TblOrderInfo tblOrderInfo = orderService.queryOrderInfo(orderNo);

                OrderDetailVo orderDetailVo = buildOrderDetailVo(tblOrderInfo);

                responseDto.addAttribute("detail", orderDetailVo);

                responseDto.setRetCode(ExceptionEnum.SUCCESS.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.SUCCESS.getErrorCode());
                responseDto.setChineseMsg(ExceptionEnum.SUCCESS.getChineseMessage());
            }

        } catch (Exception e) {
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setChineseMsg(ExceptionEnum.SYSTEM_ERROR.getChineseMessage());
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

            if (!Strings.isNullOrEmpty(input.getNextSub())) {
                TblSubOrder tblSubOrder = subOrderService.getBySubOrderNo(input.getNextSub());
                if (tblSubOrder != null) {
                    vo.setNextSubStatus(tblSubOrder.getStatus());
                } else {
                    vo.setNextSubStatus(0);
                }
            } else {
                vo.setNextSubStatus(0);
            }

            vo.setUserNumber(input.getUserNumber());
            vo.setRemark(input.getRemark());

            TblProduct tblProduct = productService.getByProductNo(input.getProductNo());
            if (tblProduct != null) {
                vo.setProductName(tblProduct.getProductName());
                vo.setProductIcon(tblProduct.getIcon());
            }

            vo.setIntervalDays(getIntervalDays(new Date(), vo.getNextSubTime()));
            vo.setProgress(getProgess(vo.getIntervalDays(), vo.getDeliveryType()));

            return vo;
        }
        return null;
    }

    private int getIntervalDays(Date fDate, Date oDate) {

        Calendar aCalendar = Calendar.getInstance();

        aCalendar.setTime(fDate);

        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

        aCalendar.setTime(oDate);

        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

        return day2 - day1;
    }

    private String getProgess(int intervalDays, String deliveryType) {
        if (intervalDays <=0) {
            return "100%";
        }

        int periodDays;
        if (AbittyConstants.DeliveryType.YEARLY.equalsIgnoreCase(deliveryType)) {
            periodDays = 356;
        } else if (AbittyConstants.DeliveryType.MONTHLY.equalsIgnoreCase(deliveryType)) {
            periodDays = 30;
        } else {
            periodDays = 7;
        }

        if (intervalDays >= periodDays) {
            return "0%";
        }

        return 100*intervalDays/periodDays + "%";
    }


}
