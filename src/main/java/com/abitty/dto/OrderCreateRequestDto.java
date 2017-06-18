package com.abitty.dto;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yak on 17/6/17.
 */
public class OrderCreateRequestDto implements Serializable {

//    @NotNull
//    @NotBlank
//    private String uid;

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
    @NotBlank
    private Integer addressId;

    @NotNull
    private Integer userNumber;

    private String remark;

//    public String getUid() {
//        return uid;
//    }
//
//    public void setUid(String uid) {
//        this.uid = uid;
//    }

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


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderCreateRequestDto{");
        sb.append("productNo='").append(productNo).append('\'');
        sb.append(", totalQuantity=").append(totalQuantity);
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", deliveryType='").append(deliveryType).append('\'');
        sb.append(", subQuantity=").append(subQuantity);
        sb.append(", totalSub=").append(totalSub);
        sb.append(", addressId=").append(addressId);
        sb.append(", userNumber=").append(userNumber);
        sb.append(", remark='").append(remark).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
