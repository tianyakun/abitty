package com.abitty.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yak on 17/6/17.
 */
public class ProductVo {

    private String productNo;

    private String name;

    private String catalogNo;

    private String description;

    private BigDecimal price;

    private BigDecimal nowPrice;

    private String deliveryType;

    private String icon;

    private String detail;

    private String images;

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProductVo{");
        sb.append("productNo='").append(productNo).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", catalogNo='").append(catalogNo).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", price=").append(price);
        sb.append(", nowPrice=").append(nowPrice);
        sb.append(", deliveryType=").append(deliveryType);
        sb.append(", icon='").append(icon).append('\'');
        sb.append(", detail='").append(detail).append('\'');
        sb.append(", images='").append(images).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
