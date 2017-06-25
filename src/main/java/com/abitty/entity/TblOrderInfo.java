package com.abitty.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TblOrderInfo {
    private Integer id;

    private String orderNo;

    private String uid;

    private String productNo;

    private Integer serviceAtomCount;

    private Integer totalQuantity;

    private BigDecimal totalAmount;

    private Integer status;

    private String payId;

    private String payReturnId;

    private String deliveryType;

    private String deliveryTime;

    private Integer subQuantity;

    private Integer totalSub;

    private Integer finishSub;

    private String nextSub;

    private Date nextSubTime;

    private Integer addressId;

    private Integer userNumber;

    private String remark;

    private String errorCode;

    private String errorMsg;

    private Date createTime;

    //以下字段不入库
    private List<TblSubOrder> subOrderList;

    private String productBody;

    private String ip;

    private String openidCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getServiceAtomCount() {
        return serviceAtomCount;
    }

    public void setServiceAtomCount(Integer serviceAtomCount) {
        this.serviceAtomCount = serviceAtomCount;
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

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
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

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<TblSubOrder> getSubOrderList() {
        return subOrderList;
    }

    public void setSubOrderList(List<TblSubOrder> subOrderList) {
        this.subOrderList = subOrderList;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getPayReturnId() {
        return payReturnId;
    }

    public void setPayReturnId(String payReturnId) {
        this.payReturnId = payReturnId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getProductBody() {
        return productBody;
    }

    public void setProductBody(String productBody) {
        this.productBody = productBody;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOpenidCode() {
        return openidCode;
    }

    public void setOpenidCode(String openidCode) {
        this.openidCode = openidCode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TblOrderInfo{");
        sb.append("id=").append(id);
        sb.append(", orderNo='").append(orderNo).append('\'');
        sb.append(", uid='").append(uid).append('\'');
        sb.append(", productNo='").append(productNo).append('\'');
        sb.append(", serviceAtomCount=").append(serviceAtomCount);
        sb.append(", totalQuantity=").append(totalQuantity);
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", status=").append(status);
        sb.append(", payId='").append(payId).append('\'');
        sb.append(", payReturnId='").append(payReturnId).append('\'');
        sb.append(", deliveryType='").append(deliveryType).append('\'');
        sb.append(", deliveryTime='").append(deliveryTime).append('\'');
        sb.append(", subQuantity=").append(subQuantity);
        sb.append(", totalSub=").append(totalSub);
        sb.append(", finishSub=").append(finishSub);
        sb.append(", nextSub='").append(nextSub).append('\'');
        sb.append(", nextSubTime=").append(nextSubTime);
        sb.append(", addressId=").append(addressId);
        sb.append(", userNumber=").append(userNumber);
        sb.append(", remark='").append(remark).append('\'');
        sb.append(", errorCode='").append(errorCode).append('\'');
        sb.append(", errorMsg='").append(errorMsg).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", subOrderList=").append(subOrderList);
        sb.append(", productBody='").append(productBody).append('\'');
        sb.append(", ip='").append(ip).append('\'');
        sb.append(", openidCode='").append(openidCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}