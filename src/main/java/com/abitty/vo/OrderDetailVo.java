package com.abitty.vo;

import java.util.List;

/**
 * Created by yak on 17/6/18.
 */
public class OrderDetailVo {
    private OrderInfoVo orderInfo;

    private List<SubOrderVo> subList;

    public OrderInfoVo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfoVo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public List<SubOrderVo> getSubList() {
        return subList;
    }

    public void setSubList(List<SubOrderVo> subList) {
        this.subList = subList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderDetailVo{");
        sb.append("orderInfo=").append(orderInfo);
        sb.append(", subList=").append(subList);
        sb.append('}');
        return sb.toString();
    }
}
