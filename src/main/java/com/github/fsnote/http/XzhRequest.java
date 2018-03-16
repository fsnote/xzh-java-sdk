package com.github.fsnote.http;

import com.github.fsnote.util.CommonUtil;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple class desc
 *
 * @author fsnail.wang@gmail.com
 * @date 2018/3/16 下午6:55
 */
public class XzhRequest {
    private HashMap<String, String> headers;
    private HashMap<String, String> params;
    private HashMap<String, Object> body;
    private URI uri;
    private HttpMethodEnum httpMethod;
    private BodyFormatEnum bodyFormat;
    private String contentEncoding;
    private ClientConnectConfig config;

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public void setBody(HashMap<String, Object> body) {
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

    public void setConfig(ClientConnectConfig config) {
        this.config = config;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public HashMap<String, Object> getBody() {
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

    public ClientConnectConfig getConfig() {
        return config;
    }

    public String getBodyStr() {
        ArrayList<String> arr = new ArrayList<String>();
        if (bodyFormat.equals(BodyFormatEnum.FORM_KV)) {
            for (Map.Entry<String, Object> entry : body.entrySet()) {
                if (entry.getValue() == null || entry.getValue().equals("")) {
                    arr.add(CommonUtil.uriEncode(entry.getKey(), true));
                } else {
                    arr.add(String.format("%s=%s", CommonUtil.uriEncode(entry.getKey(), true),
                        CommonUtil.uriEncode(entry.getValue().toString(), true)));
                }
            }
            return CommonUtil.mkString(arr.iterator(), '&');
        }
        else if (bodyFormat.equals(BodyFormatEnum.RAW_JSON)) {
            JSONObject json = new JSONObject();
            for (Map.Entry<String, Object> entry : body.entrySet()) {
                json.put(entry.getKey(), entry.getValue());
            }
            return json.toString();
        }
        return "";
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
