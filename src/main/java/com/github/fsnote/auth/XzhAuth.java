package com.github.fsnote.auth;

import com.github.fsnote.client.BaseClient;
import com.github.fsnote.http.ClientConnectConfig;
import com.github.fsnote.http.XzhHttpClient;
import com.github.fsnote.http.XzhRequest;
import com.github.fsnote.http.XzhResponse;
import com.github.fsnote.util.CommonUtil;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple class desc
 *
 * @author fsnail.wang@gmail.com
 * @date 2018/3/15 下午7:24
 */
public class XzhAuth extends BaseClient {
    private static final String OAUTH_URL = "https://openapi.baidu.com/oauth/2.0/token";

    /**
     * get access_token from openapi
     *
     * @param apiKey    API key from console
     * @param secretKey Secret Key from console
     * @param config    network config settings
     * @return JsonObject of response from OAuth server
     */
    public static JSONObject oauth(String apiKey, String secretKey, ClientConnectConfig config) {
        try {
            XzhRequest request = new XzhRequest();
            request.setUri(new URI(OAUTH_URL));
            HashMap<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("grant_type", "client_credentials");
            bodyMap.put("client_id", apiKey);
            bodyMap.put("client_secret", secretKey);

            request.setBody(bodyMap);
            request.setConfig(config);
            XzhResponse response = XzhHttpClient.post(request);
            String res = response.getBodyStr();
            int statusCode = response.getStatus();
            if (res != null && !res.equals("")) {
                return new JSONObject(res);
            } else {
                return CommonUtil.getGeneralError(statusCode, "Server response code: " + statusCode);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
