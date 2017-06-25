package com.abitty.utils;

import java.math.BigDecimal;

public final class BankUtils {

    /**
     * 将金额转为以分为单位
     * @param value
     * @return
     */
    public static String yuantoCent(BigDecimal value) {
        return value.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 将以分表示的金额转为以元为单位
     * @param centvalue
     * @return
     */
    public static String centtoYuan(BigDecimal centvalue) {
        return centvalue.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 将金额转为以分为单位
     * @param value
     * @return
     */
    public static BigDecimal toCent(BigDecimal value) {
        return value.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 将以分表示的金额转为以元为单位
     * @param centvalue
     * @return
     */
    public static BigDecimal toYuan(String centvalue) {
        try {
            return new BigDecimal(centvalue).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将金额转为以分为单位
     * @param value
     * @return
     */
    public static BigDecimal toCent(String value) {
        try {
            return new BigDecimal(value).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将以分表示的金额转为以元为单位
     * @param centvalue
     * @return
     */
    public static BigDecimal toYuan(BigDecimal centvalue) {
        return centvalue.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}

