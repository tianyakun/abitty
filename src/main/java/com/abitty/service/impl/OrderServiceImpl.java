package com.abitty.service.impl;

import com.abitty.dao.TblAddressMapper;
import com.abitty.dao.TblOrderInfoMapper;
import com.abitty.dao.TblSubOrderMapper;
import com.abitty.dto.ResponseDto;
import com.abitty.entity.TblAddress;
import com.abitty.entity.TblOrderInfo;
import com.abitty.entity.TblSubOrder;
import com.abitty.enums.ExceptionEnum;
import com.abitty.service.OrderService;
import com.abitty.utils.Constants;
import com.abitty.utils.DateUtils;
import com.abitty.utils.Sequence;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by yak on 17/6/16.
 */
@Component
public class OrderServiceImpl implements OrderService{

    @Autowired
    private TblOrderInfoMapper tblOrderInfoMapper;

    @Autowired
    private TblAddressMapper tblAddressMapper;

    @Autowired
    private TblSubOrderMapper tblSubOrderMapper;

    public void add(TblOrderInfo tblOrderInfo) {
        tblOrderInfoMapper.insertSelective(tblOrderInfo);
    }

    @Transactional
    public void confirmPay(String orderNo, ResponseDto responseDto) {

        try {
            TblOrderInfo tblOrderInfo = tblOrderInfoMapper.selectByOrderNo(orderNo);

            if (tblOrderInfo == null) {
                responseDto.setRetCode(ExceptionEnum.ORDER_NOT_EXIST.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.ORDER_NOT_EXIST.getErrorMsg());
                return;
            }

            if (tblOrderInfo.getStatus() != Constants.OrderState.INITIAL) {
                responseDto.setRetCode(ExceptionEnum.ORDER_STATUS_INVALID.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.ORDER_STATUS_INVALID.getErrorMsg());
                return;
            }

            List<TblSubOrder> subOrderList = buildSubOrders(tblOrderInfo);
            for (TblSubOrder tblSubOrder : subOrderList) {
                tblSubOrderMapper.insertSelective(tblSubOrder);
            }

            TblSubOrder firstSubOrder = subOrderList.get(0);
            tblOrderInfo.setStatus(Constants.OrderState.PAID);
            tblOrderInfo.setNextSub(firstSubOrder.getSubOrderNo());
            tblOrderInfo.setNextSubTime(firstSubOrder.getDeliveryTime());
            tblOrderInfoMapper.updateByPrimaryKeySelective(tblOrderInfo);

            responseDto.setRetCode(ExceptionEnum.SUCCESS.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SUCCESS.getErrorCode());
        } catch (Exception e) {
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
        }


    }

    public List<TblOrderInfo> getAllByUid(String uid) {

        return tblOrderInfoMapper.selectByUid(uid);
    }

    public TblOrderInfo queryOrderInfo(String orderNo) {

        TblOrderInfo tblOrderInfo = tblOrderInfoMapper.selectByOrderNo(orderNo);

        if (tblOrderInfo != null) {
            List<TblSubOrder> subOrderList = tblSubOrderMapper.selectByOrderNo(orderNo);
            tblOrderInfo.setSubOrderList(subOrderList);
        }

        return tblOrderInfo;

    }

    private List<TblSubOrder> buildSubOrders(TblOrderInfo tblOrderInfo) {
        List<TblSubOrder> tblSubOrderList = Lists.newArrayList();

        String orderNo = tblOrderInfo.getOrderNo();
        String productNo = tblOrderInfo.getProductNo();
        int quantity = tblOrderInfo.getSubQuantity();
        TblAddress tblAddress = tblAddressMapper.selectByPrimaryKey(tblOrderInfo.getAddressId());
        String recName = tblAddress.getRecName();
        String phone = tblAddress.getPhone();
        String address = tblAddress.getPcaDetail() + tblAddress.getAddressDetail();

        Date createTime = tblOrderInfo.getCreateTime();
        Date first = DateUtils.addDays(createTime, 3);

        for (int i = 0; i < tblOrderInfo.getTotalSub(); i++) {
            TblSubOrder tblSubOrder = new TblSubOrder();
            tblSubOrder.setSubOrderNo("sub" + Sequence.next());
            tblSubOrder.setOrderNo(orderNo);
            tblSubOrder.setProductNo(productNo);
            tblSubOrder.setQuantity(quantity);
            tblSubOrder.setStatus(Constants.SubOrderState.INITIAL);
            tblSubOrder.setRecvName(recName);
            tblSubOrder.setPhone(phone);
            tblSubOrder.setAddress(address);
            tblSubOrder.setCreateTime(new Date());

            tblSubOrder.setDeliveryTime(DateUtils.addWeeks(first, i));

            tblSubOrderList.add(tblSubOrder);
        }

        return tblSubOrderList;
    }

}
