package com.abitty.transport.util;

import com.abitty.transport.http.HttpsTrustManager;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.security.*;

/**
 * @author yonglong.xiao
 */
public final class CompatibleSSLUtils {

    private static final Logger logger = LoggerFactory.getLogger(CompatibleSSLUtils.class);


    /**
     * 默认为不校验证书
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext getDefaultSSLContext() throws NoSuchAlgorithmException, KeyManagementException {
        return buildSSLContext(null, new X509TrustManager[]{new HttpsTrustManager()}, new SecureRandom());
    }


    /**
     * 使用可信证书
     * @param trustStoreFile
     * @param trustPassword
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws KeyManagementException
     */
    public static SSLContext buildSSLContext(String trustStoreFile, String trustPassword) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return buildSSLContext(null, getTrustManagers(trustStoreFile, trustPassword), null);
    }


    /**
     * 包含通讯密钥，可信证书
     * @param keyStoreFile
     * @param keyPassword
     * @param trustStoreFile
     * @param trustPassword
     * @return
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws KeyManagementException
     */
    public static SSLContext buildSSLContext(String keyStoreFile, String keyPassword, String trustStoreFile, String trustPassword) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return buildSSLContext(
                getKeyManagers(keyStoreFile, keyPassword),
                getTrustManagers(trustStoreFile, trustPassword),
                null);
    }


    /**
     * 创建SSLContext
     * @param keyManagers
     * @param trustManagers
     * @param secureRandom
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext buildSSLContext(KeyManager[] keyManagers, TrustManager[] trustManagers, SecureRandom secureRandom) throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagers, trustManagers, secureRandom);
        return sslContext;
    }

    private static TrustManager[] getTrustManagers(String trustStoreFile, String trustPassword) throws NoSuchAlgorithmException, KeyStoreException{
        KeyStore trustStore = loadKeyStore(trustStoreFile, trustPassword);

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
        trustManagerFactory.init(trustStore);
        return trustManagerFactory.getTrustManagers();
    }

    private static KeyManager[] getKeyManagers(String keyStoreFile, String keyPassword) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException{
        KeyStore keyStore = loadKeyStore(keyStoreFile, keyPassword);

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, keyPassword.toCharArray());
        return keyManagerFactory.getKeyManagers();
    }

    public static KeyStore loadKeyStore(String keyFile, String keyPassword){
        FileInputStream keyStream = null;
        try {
            keyStream = new FileInputStream((new File(keyFile)));

            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(keyStream, keyPassword.toCharArray());
            return keyStore;
        } catch (Throwable e) {
            logger.error("加载证书出现异常 keyFile={}" , keyFile, e);
        } finally {
            IOUtils.closeQuietly(keyStream);
        }
        return null;
    }
}
