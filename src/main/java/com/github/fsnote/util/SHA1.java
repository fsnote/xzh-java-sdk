package com.github.fsnote.util;

import com.github.fsnote.exception.XzhErrorEnum;
import com.github.fsnote.exception.XzhException;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * SHA1 签名
 *
 * @author fsnail.wang@gmail.com
 * @date 2018/2/25 下午4:13
 */
class SHA1 {

    /**
     * 用SHA1算法生成安全签名
     *
     * @param token     票据
     * @param timestamp 时间戳
     * @param nonce     随机字符串
     * @param encrypt   密文
     * @return 安全签名
     * @throws XzhException
     */
    public static String getSHA1(String token, String timestamp, String nonce, String encrypt) throws XzhException {
        try {
            String[] array = new String[]{token, timestamp, nonce, encrypt};
            StringBuffer sb = new StringBuffer();
            Arrays.sort(array);
            for (int i = 0; i < 4; i++) {
                sb.append(array[i]);
            }
            String str = sb.toString();
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexStr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(shaHex);
            }
            return hexStr.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new XzhException(XzhErrorEnum.SIGN_SHA1_ERROR);
        }
    }
}
