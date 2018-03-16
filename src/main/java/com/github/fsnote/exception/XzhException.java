package com.github.fsnote.exception;

/**
 * A simple class desc
 *
 * @author fsnail.wang@gmail.com
 * @date 2018/3/15 下午7:23
 */
public class XzhException extends Exception {

    private String errorCode;
    private String errorMsg;

    public XzhException(XzhErrorEnum xzhErrorEnum) {
        this.errorCode = xzhErrorEnum.getErrorCode();
        this.errorMsg = xzhErrorEnum.getErrorMsg();
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
