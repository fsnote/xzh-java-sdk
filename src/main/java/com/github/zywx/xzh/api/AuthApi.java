package com.github.zywx.xzh.api;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;

import com.github.zywx.xzh.consts.XzhConst;
import com.github.zywx.xzh.http.XzhResponse;
import com.github.zywx.xzh.util.CommonUtils;

/**
 * 熊掌号授权操作类
 * @author fsnail.wang@gmail.com
 * @date 2018/3/16 下午3:25
 */
public class AuthApi extends Base {

    /**
     * 获取熊掌号调用凭据accessToken
     *
     * @param clientId 熊掌号开发者 Id
     * @param clientSecret 开发者设置密钥
     * @return
     * @throws URISyntaxException
     */
    public String getAccessToken(String clientId, String clientSecret)
            throws URISyntaxException {
        HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", XzhConst.TOKEN_GRANT_TYPE);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        XzhResponse res = this.getInvoke(XzhConst.URI_OAUTH_TOKEN, params, XzhConst.CONNECTION_TIMEOUT_MILLS,
            XzhConst.SOCKET_TIMEOUT_MILLIS);
        return res.getBody();
    }

    /**
     * 网页授权-获取授权码链接
     *
     * @param clientId 熊掌号开发者 Id
     * @param redirectUri 授权码回调地址
     * @param scope 授权权限
     * @param noLogin 是否登录
     * @param state 透传参数
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getAuthorizeRedirectUrl(String clientId, String redirectUri, String scope,
                                          Integer noLogin, String state) throws UnsupportedEncodingException {
        HashMap<String, String> params = new HashMap<>();
        params.put("response_type", XzhConst.RESPONSE_TYPE);
        params.put("client_id", clientId);
        params.put("redirect_uri", URLEncoder.encode(redirectUri, "utf-8"));
        params.put("scope", scope);

        if (noLogin != null) {
            params.put("pass_no_login", String.valueOf(noLogin));
        }

        if (state != null) {
            params.put("state", state);
        }

        return XzhConst.URI_AUTHORIZE + '?' + CommonUtils.httpBuildQuery(params);
    }

    /**
     * 网页授权-根据授权码获取用户accessToken
     *
     * @param clientId 熊掌号开发者 Id
     * @param clientSecret 开发者设置密钥
     * @param authorCode 用户授权码
     * @param redirectUri 授权码回调地址
     * @return
     * @throws Exception
     */
    public String getAuthAccessToken(String clientId, String clientSecret, String authorCode,
                                     String redirectUri) throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", XzhConst.AUTHORIZATION_CODE);
        params.put("code", authorCode);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("redirect_uri", URLEncoder.encode(redirectUri, "utf-8"));

        XzhResponse res = this.getInvoke(XzhConst.URI_OAUTH_TOKEN, params, XzhConst.CONNECTION_TIMEOUT_MILLS,
            XzhConst.SOCKET_TIMEOUT_MILLIS);
        return res.getBody();
    }

    /**
     * 网页授权-根据openId获取用户信息
     *
     * @param accessToken 用户授权 token
     * @param openId  用户 ID
     * @return
     * @throws URISyntaxException
     */
    public String getUserInfoByOpenId(String accessToken, String openId)
            throws URISyntaxException {
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", accessToken);
        params.put("openid", openId);

        String url = XzhConst.URI_REST_PREFIXS + '/' + XzhConst.OPENAPI_SNS_USERINFO;
        XzhResponse res = this.getInvoke(url, params, XzhConst.CONNECTION_TIMEOUT_MILLS, XzhConst
            .SOCKET_TIMEOUT_MILLIS);
        return res.getBody();
    }

    /**
     * 网页授权-根据refreshAccessToken获取AccessToken
     *
     * @param clientId 熊掌号开发者 Id
     * @param clientSecret 开发者设置密钥
     * @param refreshToken 刷新 refreshToken
     * @return
     * @throws URISyntaxException
     */
    public String getAccessTokenByRefreshToken(String clientId, String clientSecret, String refreshToken)
            throws URISyntaxException {
        HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", XzhConst.REFRESH_CODE);
        params.put("refresh_token", refreshToken);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);

        XzhResponse res = this.getInvoke(XzhConst.URI_OAUTH_TOKEN, params, XzhConst.CONNECTION_TIMEOUT_MILLS,
            XzhConst.SOCKET_TIMEOUT_MILLIS);
        return res.getBody();
    }
}
