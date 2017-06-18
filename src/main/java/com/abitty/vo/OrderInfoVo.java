package com.abitty.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yak on 17/6/18.
 */
public class OrderInfoVo {
    private String orderNo;

    private String uid;

    private String productNo;

    private String productName;

    private String productIcon;

    private Integer totalQuantity;

    private BigDecimal totalAmount;

    private Integer status;

    private String deliveryType;

    private Integer subQuantity;

    private Integer totalSub;

    private Integer finishSub;

    private String nextSub;

    private Date nextSubTime;

    private int intervalDays;

    private String progress;

    private Integer userNumber;

    private String remark;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Integer getSubQuantity() {
        return subQuantity;
    }

    public void setSubQuantity(Integer subQuantity) {
        this.subQuantity = subQuantity;
    }

    public Integer getTotalSub() {
        return totalSub;
    }

    public void setTotalSub(Integer totalSub) {
        this.totalSub = totalSub;
    }

    public Integer getFinishSub() {
        return finishSub;
    }

    public void setFinishSub(Integer finishSub) {
        this.finishSub = finishSub;
    }

    public String getNextSub() {
        return nextSub;
    }

    public void setNextSub(String nextSub) {
        this.nextSub = nextSub;
    }

    public Date getNextSubTime() {
        return nextSubTime;
    }

    public void setNextSubTime(Date nextSubTime) {
        this.nextSubTime = nextSubTime;
    }

    public Integer getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(Integer userNumber) {
        this.userNumber = userNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }

    public int getIntervalDays() {
        return intervalDays;
    }

    public void setIntervalDays(int intervalDays) {
        this.intervalDays = intervalDays;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderInfoVo{");
        sb.append("orderNo='").append(orderNo).append('\'');
        sb.append(", uid='").append(uid).append('\'');
        sb.append(", productNo='").append(productNo).append('\'');
        sb.append(", productName='").append(productName).append('\'');
        sb.append(", productIcon='").append(productIcon).append('\'');
        sb.append(", totalQuantity=").append(totalQuantity);
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", status=").append(status);
        sb.append(", deliveryType='").append(deliveryType).append('\'');
        sb.append(", subQuantity=").append(subQuantity);
        sb.append(", totalSub=").append(totalSub);
        sb.append(", finishSub=").append(finishSub);
        sb.append(", nextSub='").append(nextSub).append('\'');
        sb.append(", nextSubTime=").append(nextSubTime);
        sb.append(", intervalDays=").append(intervalDays);
        sb.append(", progress='").append(progress).append('\'');
        sb.append(", userNumber=").append(userNumber);
        sb.append(", remark='").append(remark).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
