package com.abitty.transport.util;

import com.google.common.base.Strings;
import org.apache.commons.io.IOUtils;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.*;

/**
 * @author litingpeng
 */
public final class SSLUtils {

    private static final Logger logger = LoggerFactory.getLogger(SSLUtils.class);

    public static final String ALGORITHM_JKS = "JKS";

    public static final String ALGORITHM_PKCS12 = "PKCS12";

    public static SSLContext buildSSLContext(String keyStoreFile, String keyPassword, String trustStoreFile, String trustPassword) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return buildSSLContext(keyStoreFile, keyPassword, ALGORITHM_JKS, trustStoreFile, trustPassword, ALGORITHM_JKS);
    }

    public static SSLContext buildSSLContext(String keyStoreFile, String keyPassword, String keyAlgorithm, String trustStoreFile, String trustPassword, String trustAlgorithm) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return buildSSLContext(keyStoreFile, keyPassword, keyAlgorithm, trustStoreFile, trustPassword, trustAlgorithm, null);
    }

    public static SSLContext buildSSLContext(String keyStoreFile, String keyPassword, String keyAlgorithm, String trustStoreFile, String trustPassword, String trustAlgorithm, String protocol) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        SSLContextBuilder sslContextBuilder = SSLContexts.custom();

        if(!Strings.isNullOrEmpty(keyStoreFile)) {
            KeyStore keyStore = loadKeyStore(keyStoreFile, keyPassword, keyAlgorithm);
            sslContextBuilder.loadKeyMaterial(keyStore, keyPassword.toCharArray());
        }

        if(!Strings.isNullOrEmpty(trustStoreFile)) {
            KeyStore trustStore = loadKeyStore(trustStoreFile, trustPassword, trustAlgorithm);
            sslContextBuilder.loadTrustMaterial(trustStore);
        }
        return sslContextBuilder.useProtocol(protocol).build();
    }

    public static KeyStore loadKeyStore(String keyFile, String keyPassword){
        return loadKeyStore(keyFile, keyPassword, ALGORITHM_JKS);
    }

    public static KeyStore loadKeyStore(String keyFile, String keyPassword, String algorithm){
        FileInputStream keyStream = null;
        try {
            keyStream = new FileInputStream(new File(keyFile));

            KeyStore keyStore = KeyStore.getInstance(algorithm);
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
