package com.abitty.vo;

/**
 * Created by yak on 17/6/29.
 */
public class ActivityBannerVo {

    private String activityNo;

    private String description;

    private String icon;

    private String redirectUrl;

    public String getActivityNo() {
        return activityNo;
    }

    public void setActivityNo(String activityNo) {
        this.activityNo = activityNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ActivityBannerVo{");
        sb.append("activityNo='").append(activityNo).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", icon='").append(icon).append('\'');
        sb.append(", redirectUrl='").append(redirectUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
