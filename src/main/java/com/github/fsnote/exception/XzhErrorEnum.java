package com.github.fsnote.exception;

/**
 * A simple class desc
 *
 * @author fsnail.wang@gmail.com
 * @date 2018/3/15 下午7:23
 */
public enum XzhErrorEnum {
    SUCCESS("0", "Success"),
    SIGN_SHA1_ERROR("JSDK100", "sha1 sign error"),
    PARSE_XML_ERROR("JSDK101", "xml parse error"),
    ILLEGAL_AES_KEY_ERROR("JSDK102", "aes key illegal"),
    ENCRYPT_AES_ERROR("JSDK103", "aes encrypt error"),
    DECRYPT_AES_ERROR("JSDK104", "aes decrypt error"),
    ILLEGAL_BUFFER_ERROR("JSDK105", "aes illegal buffer error"),
    VALIDATE_APPID_ERROR("JSDK106", "validate appid error"),
    VALIDATE_SIGNATURE_ERROR("JSDK107", "validate signature error");

    private final String errorCode;
    private final String errorMsg;

    XzhErrorEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
