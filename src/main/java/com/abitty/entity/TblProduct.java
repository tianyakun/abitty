package com.abitty.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TblProduct {
    private Integer id;

    private String productNo;

    private String productName;

    private String catalogNo;

    private String description;

    private BigDecimal price;

    private BigDecimal nowPrice;

    private Integer status;

    private String icon;

    private String detail;

    private String images;

    private String deliveryType;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setName(String productName) {
        this.productName = productName;
    }

    public String getCatalogNo() {
        return catalogNo;
    }

    public void setCatalogNo(String catalogNo) {
        this.catalogNo = catalogNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(BigDecimal nowPrice) {
        this.nowPrice = nowPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TblProduct{");
        sb.append("id=").append(id);
        sb.append(", productNo='").append(productNo).append('\'');
        sb.append(", name='").append(productName).append('\'');
        sb.append(", catalogNo='").append(catalogNo).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", price=").append(price);
        sb.append(", nowPrice=").append(nowPrice);
        sb.append(", status=").append(status);
        sb.append(", icon='").append(icon).append('\'');
        sb.append(", detail='").append(detail).append('\'');
        sb.append(", images='").append(images).append('\'');
        sb.append(", deliveryType='").append(deliveryType).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}