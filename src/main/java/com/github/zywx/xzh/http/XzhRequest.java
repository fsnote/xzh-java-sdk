package com.github.zywx.xzh.http;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * http 请求类
 *
 * @author fsnail.wang@gmail.com
 * @date 2018/3/14 上午10:15
 */
public class XzhRequest {
    private HashMap<String, String> headers;
    private HashMap<String, String> params;
    private HashMap<String, Object> formParams;
    private JSONObject body;
    private URI uri;
    private HttpMethodEnum httpMethod;
    private BodyFormatEnum bodyFormat;
    private String contentEncoding;
    private ConnectConfig config;

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public void setFormParams(HashMap<String, Object> formParams) {
        this.formParams = formParams;
    }

    public void setBody(JSONObject body) {
        this.body = body;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public void setHttpMethod(HttpMethodEnum httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    public void setBodyFormat(BodyFormatEnum bodyFormat) {
        this.bodyFormat = bodyFormat;
    }

    public void setConfig(ConnectConfig config) {
        this.config = config;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public HashMap<String, Object> getFormParams() {
        return formParams;
    }

    public JSONObject getBody() {
        return body;
    }

    public URI getUri() {
        return uri;
    }

    public HttpMethodEnum getHttpMethod() {
        return httpMethod;
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public BodyFormatEnum getBodyFormat() {
        return bodyFormat;
    }

    public ConnectConfig getConfig() {
        return config;
    }

    public String getBodyStr() {
        if (body == null) {
            return "";
        }
        return body.toString();
    }

    public String getParamStr() {
        StringBuffer buffer = new StringBuffer();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            buffer.append(String.format("%s=%s&", entry.getKey(), entry.getValue()));
        }
        if (buffer.length() > 0) {
            buffer.deleteCharAt(buffer.length() - 1);
        }
        return buffer.toString();
    }
}
