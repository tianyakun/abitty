package com.abitty.dto;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by yak on 17/6/17.
 */
public class OrderConfirmRequestDto implements Serializable {

    @NotNull
    @NotBlank
    private String productNo;

    @NotNull
    private Integer totalQuantity;

    @NotNull
    private BigDecimal totalAmount;

    @NotNull
    @NotBlank
    private String deliveryType;

//    @NotNull
//    @NotBlank
//    private String deliveryTime;

    @NotNull
    private Integer subQuantity;

    @NotNull
    private Integer totalSub;

    @NotNull
    private Integer userNumber;

    private String remark;

    @NotNull
    @NotBlank
    private String receiverName;

    @NotNull
    @NotBlank
    private String phoneNumber;

    @NotNull
    @NotBlank
    private String addressProvince;

    @NotNull
    @NotBlank
    private String addressCity;

    @NotNull
    @NotBlank
    private String addressArea;

    @NotNull
    @NotBlank
    private String addressDetail;

    @NotNull
    @NotBlank
    private String postcode;

    @NotNull
    @NotBlank
    private String productBody;//商品描述 商家名称-销售商品类目

    private String uid;

    private String ip;

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

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

//    public String getDeliveryTime() {
//        return deliveryTime;
//    }
//
//    public void setDeliveryTime(String deliveryTime) {
//        this.deliveryTime = deliveryTime;
//    }

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

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddressProvince() {
        return addressProvince;
    }

    public void setAddressProvince(String addressProvince) {
        this.addressProvince = addressProvince;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressArea() {
        return addressArea;
    }

    public void setAddressArea(String addressArea) {
        this.addressArea = addressArea;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getProductBody() {
        return productBody;
    }

    public void setProductBody(String productBody) {
        this.productBody = productBody;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderConfirmRequestDto{");
        sb.append("productNo='").append(productNo).append('\'');
        sb.append(", totalQuantity=").append(totalQuantity);
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", deliveryType='").append(deliveryType).append('\'');
        sb.append(", subQuantity=").append(subQuantity);
        sb.append(", totalSub=").append(totalSub);
        sb.append(", userNumber=").append(userNumber);
        sb.append(", remark='").append(remark).append('\'');
        sb.append(", receiverName='").append(receiverName).append('\'');
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", addressProvince='").append(addressProvince).append('\'');
        sb.append(", addressCity='").append(addressCity).append('\'');
        sb.append(", addressArea='").append(addressArea).append('\'');
        sb.append(", addressDetail='").append(addressDetail).append('\'');
        sb.append(", postcode='").append(postcode).append('\'');
        sb.append(", productBody='").append(productBody).append('\'');
        sb.append(", uid='").append(uid).append('\'');
        sb.append(", ip='").append(ip).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
