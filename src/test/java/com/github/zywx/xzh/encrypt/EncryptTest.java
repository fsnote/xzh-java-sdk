package com.github.zywx.xzh.encrypt;

import com.github.zywx.xzh.exception.XzhException;
import com.github.zywx.xzh.util.AesEncryptUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.StringReader;

/**
 * JAVA AES 加减密测试
 *
 * @author fsnail.wang@gmail.com
 * @date 2018/3/17 下午4:10
 */
public class EncryptTest {
    private static final Logger LOGGER = Logger.getLogger(EncryptTest.class);

    /**
     * 消息加减密-使用示例
     *
     * @throws Exception
     */
    @Test
    public void testTextMsgEncryptAndDecrypt() {
        try {
            String clientId = "HzvBSGAvkFMUrhELUZayfH2No86tk";
            String encodingAesKey = "zF9muYLlAeV8OCeaY9N3x1S7zJXPhR6m2RNphfOZKj3";
            String token = "cttdusite";

            String timestamp = "1529572294";
            String nonce = "1697889492";
            String replyMsg = "测试消息你好啊";

            // 加密过程
            AesEncryptUtils pc = new AesEncryptUtils(token, encodingAesKey, clientId);
            String contentEncrpt = pc.encryptXmlPassiveMsg(replyMsg, timestamp, nonce);
            LOGGER.info("encrypt:\n" + contentEncrpt);

            // 解密过程（以 im、关注、菜单点击消息格式为例）
            String xmlFormat = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            StringReader sr = new StringReader(contentEncrpt);
            InputSource is = new InputSource(sr);
            Document document = db.parse(is);

            Element root = document.getDocumentElement();
            String encrypt = root.getElementsByTagName("Encrypt").item(0).getTextContent();
            String msgSignature = root.getElementsByTagName("MsgSignature").item(0).getTextContent();

            String msgXML = String.format(xmlFormat, encrypt);
            String decrypt = pc.decryptXmlPassiveMsg(msgSignature, timestamp, nonce, msgXML);
            LOGGER.info("\ndecrypt:\n" + decrypt);
        } catch (XzhException e) {
            LOGGER.error(e.getErrorMsg(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * 对 Xml Encrypt解密
     *
     * @throws Exception
     */
    @Test
    public void testXmlDecryptMsg() {
        try {
            String clientId = "QrxKn0PYqE5F0OHr3MTQOPedcTEZH";
            String encodingAesKey = "U8BQbjSYrZnLDBSuUC8DSKwM6vKboLRUqDUPds5uHi2";
            String token = "1989xbp";

            String timestamp = "1529633409";
            String nonce = "1617361377";
            String msgSignature = "59acc4676c267b4e14ffae6a8bcab286202c5d0";

            // 接收到的 xml 消息
            String msgXml = "<xml>"
                    + "<AppId><![CDATA[3NQrxKn0PYqE5F0OHr3MTQOPedcTEZMH]]></AppId>"
                    + "<TpClientId><![CDATA[3NQrxKn0PYqE5F0OHr3MTQOPedcTEZMH]]></TpClientId>"
                    + "<Encrypt><![CDATA[kcNL2Xb42V1u/ex9GbS47I1ZrB1vOeABhOdjqSA5ZtsPTepm7ZzP+GUy5uZooNjhGZR"
                    + "1eeLCB30KT3wGnpPO0+EulFbhYEhcYeOd+dXC6dj+n0/nmcc+B+zs4rMwCZnjt57NxGt+hiP6xSUr8iUDdLsO"
                    + "THCxA3mvQrBzdh8ilY2V/tum28b86/BCYMCqzPzDL2WW+uVeQbKLR8mNOSyZ94gUnEBf26tyGPqjxWX/bDSUZ"
                    + "S0fUGGChz+S6fGaYUama+ovsjdisWHWwzfiFpgouVP+YQmx6j9SKALnxaJtzj3Ck7tlPkceq4s4wKIBiXh5RR"
                    + "LRBU6hZyqJxT5kpDrkcIuUvq8qK/qVImFmjuPkAYSAqZ4XQ++rXuu59u+9m9IX2PxSa375vd0idBAqmLBOEa5k"
                    + "BG7yIgsxoYWnVezvDjRKb0ve6Ii0c/WDb4NQPUxZfuQts69h9by9PfmYDN/snaE1MeP1K0qP2tkwMY/K7fjzv"
                    + "K4ao2uQqOP8dq7LVjhJ]]></Encrypt></xml>";

            AesEncryptUtils aes = new AesEncryptUtils(token, encodingAesKey, clientId);
            String decrypt = aes.decryptXmlPassiveMsg(msgSignature, timestamp, nonce, msgXml);
            LOGGER.info("\ndecrypt:\n" + decrypt);
        } catch (XzhException e) {
            LOGGER.error(e.getErrorMsg(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * 解密示例：使用 xzh/tp 的开发配置
     *
     * @throws Exception
     */
    @Test
    public void testTextDecryptAndEncrypt() {
        try {
            String clientId = "oFlwOVRKjOGmbAwTI2u0G9oQ1XkCx1m";
            String encodingAesKey = "1q2w3e4r5t6y7u8i9o0pasdfghjklzxcvbnm1q2w3e";
            String token = "0123456789";

            String encryText = "m7EpAOoRHocZnldlo29lcFeKE2Vx44Fn9iT0YnAZ7iZAoVK8EIo+o6+CT/D5aVh0oudw"
                    + "apY+TJVzQAouBwwQM99ioaZGz9i22eWjilLvOHMBlasaeOiDKfubWef30aayEFiWiHb/Zi+uxX"
                    + "xWBirhlLzJdHAGRSq8jGhfzrTL4qdrwozhHIoqToY5N/iDJKF+DVLm0QAvV44DoA+Ro2YWH1aBa2XjA4c"
                    + "GngNg4Lwp5P/cnRUrkXYLkc54ZN26ih8BvX1ZZT+CnjtoXrs6P3rk0oFoNykYXBS6Q4JLdac8YYQaUh5f"
                    + "EwyXximRExXjnV1ekFIcfAVCvruNlDeCBfMMruSCgotHxNC7gFtK2BCT+QJ1bjoSUVO3WHdN2suDAzKd";

            System.out.println("=====解密========");
            AesEncryptUtils crypt = new AesEncryptUtils(token, encodingAesKey, clientId);
            String decryptText = crypt.decrypt(encryText);
            System.out.println(decryptText);

            System.out.println("======加密========");
            String encryptString = crypt.encrypt(crypt.getRandomStr(), decryptText);
            System.out.println(encryptString);
        } catch (XzhException e) {
            LOGGER.error(e.getErrorMsg(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
