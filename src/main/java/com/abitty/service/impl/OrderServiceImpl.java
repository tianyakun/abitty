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
import com.abitty.constant.AbittyConstants;
import com.abitty.utils.DateUtils;
import com.abitty.utils.Sequence;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

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
    public boolean paySuccess(TblOrderInfo tblOrderInfo) {

        try {
            Preconditions.checkNotNull(tblOrderInfo, "tblOrderInfo is null");

            List<TblSubOrder> subOrderList = buildSubOrders(tblOrderInfo);
            for (TblSubOrder tblSubOrder : subOrderList) {
                tblSubOrderMapper.insertSelective(tblSubOrder);
            }

            TblSubOrder firstSubOrder = subOrderList.get(0);
            tblOrderInfo.setStatus(AbittyConstants.OrderState.PAY_SUCCESS);
            tblOrderInfo.setNextSub(firstSubOrder.getSubOrderNo());
            tblOrderInfo.setNextSubTime(firstSubOrder.getDeliveryTime());
            tblOrderInfoMapper.updateByPrimaryKeySelective(tblOrderInfo);

            return true;
        } catch (Exception e) {
            logger.error("订单支付成功 更新失败", e);
            return false;
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
            tblSubOrder.setSubOrderNo("S" + Sequence.next());
            tblSubOrder.setOrderNo(orderNo);
            tblSubOrder.setProductNo(productNo);
            tblSubOrder.setQuantity(quantity);
            tblSubOrder.setStatus(AbittyConstants.SubOrderState.INITIAL);
            tblSubOrder.setRecvName(recName);
            tblSubOrder.setPhone(phone);
            tblSubOrder.setAddress(address);
            tblSubOrder.setCreateTime(new Date());

            tblSubOrder.setDeliveryTime(DateUtils.addWeeks(first, i));

            tblSubOrderList.add(tblSubOrder);
        }

        return tblSubOrderList;
    }

    @Transactional
    @Override
    public boolean saveRequestOrder(TblOrderInfo tblOrderInfo, TblAddress tblAddress) {


        tblAddressMapper.insertSelective(tblAddress);

        tblOrderInfo.setAddressId(tblAddress.getId());
        tblOrderInfoMapper.insertSelective(tblOrderInfo);

        return true;
    }

    @Override
    public void recievePayinfo(TblOrderInfo tblOrderInfo) {
        tblOrderInfoMapper.updateByPrimaryKeySelective(tblOrderInfo);
    }

    @Override
    public TblOrderInfo getByPayId(String payId) {
        return tblOrderInfoMapper.selectByPayId(payId);
    }

    @Override
    public List<TblOrderInfo> getSuccessOrderByUid(String uid) {
        return tblOrderInfoMapper.selectSuccessOrderByUid(uid);
    }
}
