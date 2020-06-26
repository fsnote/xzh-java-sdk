package com.github.zywx.xzh.api;

import java.net.URISyntaxException;
import java.util.HashMap;

import org.json.JSONObject;

import com.github.zywx.xzh.consts.XzhConst;
import com.github.zywx.xzh.http.BodyFormatEnum;
import com.github.zywx.xzh.http.XzhResponse;

/**
 * OpenAPI 调用基类
 *
 * @author fsnail.wang@gmail.com
 * @date 2018/3/16 下午4:15
 */
public class OpenApi extends Base {

    @Override
    public XzhResponse getInvoke(String api, HashMap<String, String> params,
                                 Integer conTime, Integer skTime) throws URISyntaxException {
        String url = XzhConst.URI_REST_PREFIXS + '/' + api;
        return super.getInvoke(url, params, conTime, skTime);
    }

    @Override
    public XzhResponse postInvoke(String api, HashMap<String, String> params,
                                  HashMap<String, Object> formParams, JSONObject body, BodyFormatEnum bodyFormat,
                                  Integer connTime, Integer skTime) throws URISyntaxException {
        String url = XzhConst.URI_REST_PREFIXS + '/' + api;
        return super.postInvoke(url, params, formParams, body, bodyFormat, connTime, skTime);
    }

    public XzhResponse postInvokeMid(String url, HashMap<String, String> params,
                                     HashMap<String, Object> formParams, JSONObject body, BodyFormatEnum bodyFormat,
                                     Integer connTime, Integer skTime) throws URISyntaxException {
        return super.postInvoke(url, params, formParams, body, bodyFormat, connTime, skTime);
    }
}
