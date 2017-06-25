package com.abitty.entity;

import java.util.Date;

public class TblCatalog {
    private Integer id;

    private String catalogNo;

    private String name;

    private String icon;

    private Integer pid;

    private String description;

    private String deliveryType;

    private Integer isDelete;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCatalogNo() {
        return catalogNo;
    }

    public void setCatalogNo(String catalogNo) {
        this.catalogNo = catalogNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
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
        final StringBuilder sb = new StringBuilder("TblCatalog{");
        sb.append("id=").append(id);
        sb.append(", catalogNo='").append(catalogNo).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", icon='").append(icon).append('\'');
        sb.append(", pid=").append(pid);
        sb.append(", description='").append(description).append('\'');
        sb.append(", deliveryType='").append(deliveryType).append('\'');
        sb.append(", isDelete=").append(isDelete);
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}