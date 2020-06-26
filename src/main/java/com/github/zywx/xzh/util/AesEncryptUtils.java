package com.github.zywx.xzh.util;

import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.github.zywx.xzh.exception.XzhErrorEnum;
import com.github.zywx.xzh.exception.XzhException;

/**
 * JAVA AES 加减密
 *
 * @author fsnail.wang@gmail.com
 * @date 2018/3/15 下午3:31
 */
public class AesEncryptUtils {
    private static Logger LOGGER = Logger.getLogger(AesEncryptUtils.class);
    private static Charset CHARSET = Charset.forName("utf-8");
    private Base64 base64 = new Base64();
    private byte[] aesKey;
    private String token;
    private String clientId;

    /**
     * 构造函数
     *
     * @param token          开发者token
     * @param encodingAesKey 开发者encodingAESKey
     * @param clientId       开发者clientId
     * @throws XzhException 异常错误信息
     */
    public AesEncryptUtils(String token, String encodingAesKey, String clientId) throws XzhException {
        int encodingAesKeyLength = 43;
        if (encodingAesKey.length() != encodingAesKeyLength) {
            throw new XzhException(XzhErrorEnum.ILLEGAL_AES_KEY_ERROR);
        }

        this.token = token;
        this.clientId = clientId;
        aesKey = Base64.decodeBase64(encodingAesKey + "=");
    }

    /**
     * 生成4个字节的网络字节序
     *
     * @param sourceNumber 文本长度
     * @return orderBytes
     */
    private byte[] getNetworkBytesOrder(int sourceNumber) {
        byte[] orderBytes = new byte[4];
        orderBytes[3] = (byte) (sourceNumber & 0xFF);
        orderBytes[2] = (byte) (sourceNumber >> 8 & 0xFF);
        orderBytes[1] = (byte) (sourceNumber >> 16 & 0xFF);
        orderBytes[0] = (byte) (sourceNumber >> 24 & 0xFF);

        return orderBytes;
    }

    /**
     * 还原4个字节的网络字节序
     *
     * @param orderBytes 字节码
     * @return sourceNumber
     */
    private int recoverNetworkBytesOrder(byte[] orderBytes) {
        int sourceNumber = 0;
        int length = 4;
        int number = 8;
        for (int i = 0; i < length; i++) {
            sourceNumber <<= number;
            sourceNumber |= orderBytes[i] & 0xff;
        }

        return sourceNumber;
    }

    /**
     * 随机生成16位字符串
     *
     * @return 随机串
     */
    public String getRandomStr() {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder stringBuild = new StringBuilder();
        int randStrLength = 16;
        for (int i = 0; i < randStrLength; i++) {
            int number = random.nextInt(base.length());
            stringBuild.append(base.charAt(number));
        }

        return stringBuild.toString();
    }

    /**
     * 对明文进行加密
     *
     * @param text 待加密明文
     * @return 加密后base64编码的字符串
     * @throws XzhException 异常错误信息
     */
    public String encrypt(String randomStr, String text) throws XzhException {
        ByteGroup byteCollector = new ByteGroup();
        byte[] randomStrBytes = randomStr.getBytes(CHARSET);
        byte[] textBytes = text.getBytes(CHARSET);
        byte[] networkBytesOrder = getNetworkBytesOrder(textBytes.length);
        byte[] clientIdBytes = clientId.getBytes(CHARSET);

        byteCollector.addBytes(randomStrBytes);
        byteCollector.addBytes(networkBytesOrder);
        byteCollector.addBytes(textBytes);
        byteCollector.addBytes(clientIdBytes);

        byte[] padBytes = PKCS7Encoder.encode(byteCollector.size());
        byteCollector.addBytes(padBytes);

        byte[] unencrypted = byteCollector.toBytes();

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);

            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            byte[] encrypted = cipher.doFinal(unencrypted);
            return base64.encodeToString(encrypted);
        } catch (Exception e) {
            LOGGER.error(e);
            throw new XzhException(XzhErrorEnum.ENCRYPT_AES_ERROR);
        }
    }

    /**
     * 对密文进行解密
     *
     * @param text 需要解密的密文
     * @return 解密得到的明文
     * @throws XzhException 异常错误信息
     */
    public String decrypt(String text)
            throws XzhException {
        byte[] original;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            byte[] encrypted = Base64.decodeBase64(text);
            original = cipher.doFinal(encrypted);
        } catch (Exception e) {
            LOGGER.error(e);
            throw new XzhException(XzhErrorEnum.DECRYPT_AES_ERROR);
        }

        String xmlContent;
        String fromClientId;
        try {
            // 去除补位字符
            byte[] bytes = PKCS7Encoder.decode(original);
            // 分离16位随机字符串,网络字节序和ClientId
            byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);
            int xmlLength = recoverNetworkBytesOrder(networkOrder);
            xmlContent = new String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), CHARSET);
            fromClientId = new String(Arrays.copyOfRange(bytes, 20 + xmlLength, bytes.length), CHARSET);
        } catch (Exception e) {
            LOGGER.error(e);
            throw new XzhException(XzhErrorEnum.ILLEGAL_BUFFER_ERROR);
        }

        //        if (!fromClientId.equals(clientId)) {
        //            throw new XzhException(XzhErrorEnum.VALIDATE_CLIENT_ID_ERROR);
        //        }

        return xmlContent;
    }

    /**
     * 消息加密打包
     *
     * @param replyMsg  平台待回复用户的消息
     * @param timeStamp 时间戳，可以自己生成，也可以用URL参数的timestamp
     * @param nonce     随机串，可以自己生成，也可以用URL参数的nonce
     * @return 加密后的可以直接回复用户的密文
     * @throws XzhException 异常错误信息
     */
    public String encryptXmlPassiveMsg(String replyMsg, String timeStamp,
                                       String nonce) throws XzhException {
        String encrypt = encrypt(getRandomStr(), replyMsg);

        if ("".equals(timeStamp)) {
            timeStamp = Long.toString(System.currentTimeMillis());
        }
        String signature = SHA1Utils.getSHA1(token, timeStamp, nonce, encrypt);

        return generatePassiveXml(encrypt, signature, timeStamp, nonce);
    }

    /**
     * 检验xml消息的真实性，并且获取解密后的明文
     *
     * @param msgSignature 签名串，对应URL参数的msg_signature
     * @param timeStamp    时间戳，对应URL参数的timestamp
     * @param nonce        随机串，对应URL参数的nonce
     * @param postData     密文，对应POST请求的数据
     * @return 解密后的原文
     * @throws XzhException 异常错误信息
     */
    public String decryptXmlPassiveMsg(String msgSignature, String timeStamp,
                                       String nonce, String postData) throws XzhException {
        String decryptText;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            StringReader sr = new StringReader(postData);
            InputSource is = new InputSource(sr);
            Document document = db.parse(is);

            Element root = document.getDocumentElement();
            decryptText = root.getElementsByTagName("Encrypt").item(0).getTextContent();
        } catch (Exception e) {
            LOGGER.error(e);
            throw new XzhException(XzhErrorEnum.PARSE_XML_ERROR);
        }

        // 验证安全签名
        String signature = SHA1Utils.getSHA1(token, timeStamp, nonce, decryptText);
        if (!signature.equals(msgSignature)) {
            throw new XzhException(XzhErrorEnum.VALIDATE_SIGNATURE_ERROR);
        }

        return decrypt(decryptText);
    }

    /**
     * 生成被动回复格式xml 消息
     *
     * @param encrypt 加密内容
     * @param signature 熊掌号签名
     * @param timestamp 时间戳，对应URL参数的timestamp
     * @param nonce   随机串，对应URL参数的nonce
     * @return json
     */
    private String generatePassiveXml(String encrypt, String signature, String timestamp,
                                      String nonce) {
        String format = "<xml>\n" + "<Encrypt><![CDATA[%1$s]]></Encrypt>\n"
                + "<MsgSignature><![CDATA[%2$s]]></MsgSignature>\n"
                + "<TimeStamp>%3$s</TimeStamp>\n"
                + "<Nonce><![CDATA[%4$s]]></Nonce>\n" + "</xml>";
        return String.format(format, encrypt, signature, timestamp, nonce);
    }
}