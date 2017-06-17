package com.abitty.dto;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;

/**
 * Created by yak on 17/6/15.
 */
public class AddressDto implements Serializable{

    private Integer addressId;

    @NotNull
    @NotBlank
    private String uid;

    @NotNull
    @NotBlank
    private String recName;

    @NotNull
    @NotBlank
    private String province;

    @NotNull
    @NotBlank
    private String city;

    @NotNull
    @NotBlank
    private String area;

    @NotNull
    @NotBlank
    private String addressDetail;

    private String postcode;

    @NotNull
    @NotBlank
    private String phone;

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddressDto{");
        sb.append("addressId=").append(addressId);
        sb.append(", uid='").append(uid).append('\'');
        sb.append(", recName='").append(recName).append('\'');
        sb.append(", province='").append(province).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", area='").append(area).append('\'');
        sb.append(", addressDetail='").append(addressDetail).append('\'');
        sb.append(", postcode='").append(postcode).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
