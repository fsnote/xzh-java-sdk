package com.github.zywx.xzh.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.json.JSONObject;

import com.github.zywx.xzh.consts.XzhConst;
import com.github.zywx.xzh.http.BodyFormatEnum;
import com.github.zywx.xzh.http.ConnectConfig;
import com.github.zywx.xzh.http.HttpMethodEnum;
import com.github.zywx.xzh.http.XzhHttpClient;
import com.github.zywx.xzh.http.XzhRequest;
import com.github.zywx.xzh.http.XzhResponse;

/**
 * API 基类
 * @author fsnail.wang@gmail.com
 * @date 2018/3/16 上午10:00
 */
public class Base {
    /**
     * get
     * @param uri
     * @param params
     * @param conTime
     * @param skTime
     * @return
     * @throws URISyntaxException
     */
    protected XzhResponse getInvoke(String uri, HashMap<String, String> params,
                                    Integer conTime, Integer skTime) throws URISyntaxException {
        XzhRequest xzhRequest = new XzhRequest();
        xzhRequest.setUri(new URI(uri));
        xzhRequest.setHttpMethod(HttpMethodEnum.GET);
        xzhRequest.setContentEncoding("utf-8");
        xzhRequest.setParams(params);
        xzhRequest.setBodyFormat(BodyFormatEnum.FORM_KV);
        xzhRequest.setFormParams(new HashMap<String, Object>());

        Integer conTimeoutMillis = (conTime != null) ? conTime : XzhConst.CONNECTION_TIMEOUT_MILLS;
        Integer skTimeoutMillis = (skTime != null) ? skTime : XzhConst.SOCKET_TIMEOUT_MILLIS;
        ConnectConfig clientConnectConfig = new ConnectConfig(conTimeoutMillis, skTimeoutMillis);
        xzhRequest.setConfig(clientConnectConfig);

        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("connection", "Keep-Alive");
        xzhRequest.setHeaders(headerMap);

        return XzhHttpClient.get(xzhRequest);
    }

    /**
     * post
     * @param uri
     * @param params
     * @param formParams
     * @param body
     * @param bodyFormat
     * @param connTime
     * @param skTime
     * @return
     * @throws URISyntaxException
     */
    protected XzhResponse postInvoke(String uri, HashMap<String, String> params,
                                     HashMap<String, Object> formParams, JSONObject body, BodyFormatEnum bodyFormat,
                                     Integer connTime, Integer skTime) throws URISyntaxException {
        XzhRequest xzhRequest = new XzhRequest();
        xzhRequest.setUri(new URI(uri));
        xzhRequest.setHttpMethod(HttpMethodEnum.POST);
        xzhRequest.setContentEncoding("utf-8");
        xzhRequest.setParams(params);
        xzhRequest.setFormParams(formParams);
        xzhRequest.setBody(body);
        xzhRequest.setBodyFormat(bodyFormat);

        Integer conTimeoutMillis = (connTime != null) ? connTime : XzhConst.CONNECTION_TIMEOUT_MILLS;
        Integer skTimeoutMillis = (skTime != null) ? skTime : XzhConst.SOCKET_TIMEOUT_MILLIS;
        ConnectConfig clientConnectConfig = new ConnectConfig(conTimeoutMillis, skTimeoutMillis);
        xzhRequest.setConfig(clientConnectConfig);

        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("connection", "Keep-Alive");
        xzhRequest.setHeaders(headerMap);

        return XzhHttpClient.post(xzhRequest);
    }
}
