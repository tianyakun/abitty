package com.abitty.wechat;

import com.google.common.base.Preconditions;

import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yak on 17/6/24.
 */
public class SHA1Util {

    /**
     * sign 安全加密算法
     * @param plaintext 明文
     * @return
     * @throws DigestException
     */
    public static String sign(final String plaintext) throws DigestException {
        Preconditions.checkNotNull(plaintext, "plaintext is null");

        try {
            //指定sha1算法
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(plaintext.getBytes());
            //获取字节数组
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString().toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            throw new DigestException("签名错误！");
        }
    }
}
