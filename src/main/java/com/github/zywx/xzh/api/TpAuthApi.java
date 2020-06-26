package com.github.zywx.xzh.api;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;

import com.github.zywx.xzh.consts.XzhConst;
import com.github.zywx.xzh.http.XzhResponse;
import com.github.zywx.xzh.util.CommonUtils;

/**
 * TP 授权操作类
 * @author fsnail.wang@gmail.com
 * @date 2018/3/17 上午16:15
 */
public class TpAuthApi extends Base {

    /**
     * 获取第三方平台接口调用凭据
     *
     * @param clientId TP开发者 ID
     * @param clientSecret TP开发者密钥
     * @param tpVerifyTicket 给 TP 推送的 Ticket
     * @return
     * @throws URISyntaxException
     */
    public String getTpAccessToken(String clientId, String clientSecret,
                                   String tpVerifyTicket) throws URISyntaxException {
        HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", XzhConst.TP_TOKEN_GRANT_TYPE);
        params.put("tp_client_id", clientId);
        params.put("tp_client_secret", clientSecret);
        params.put("tp_verify_ticket", tpVerifyTicket);
        XzhResponse res = this.getInvoke(XzhConst.URI_OAUTH_TOKEN, params,
                XzhConst.CONNECTION_TIMEOUT_MILLS, XzhConst.SOCKET_TIMEOUT_MILLIS);
        return res.getBody();
    }

    /**
     * 熊掌号授权-获取预授权码
     *
     * @param tpAccessToken tp调用凭证
     * @param debug 非debug模式默认null即可
     * @return 预授权json信息
     * @throws URISyntaxException
     */
    public String getPreAuthCodeByTpAccessToken(String tpAccessToken, String debug)
            throws URISyntaxException {
        HashMap<String, String> params = new HashMap<>();
        params.put("tp_access_token", tpAccessToken);
        if (debug != null) {
            params.put("debug", debug);
        }

        String url = XzhConst.URI_REST_PREFIXS + '/' + XzhConst.OPENAPI_TP_API_CREATE_PREAUTHCODE;
        XzhResponse res = this.getInvoke(url, params, XzhConst.CONNECTION_TIMEOUT_MILLS, XzhConst
            .SOCKET_TIMEOUT_MILLIS);
        return res.getBody();
    }

    /**
     * 熊掌号授权-获取预授权码授权链接
     *
     * @param tpClientId tp 客户端 Id
     * @param preAuthCode 预授权码
     * @param redirectUri 预授权码回调地址
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getTpAuthorizeRedirectUrl(String tpClientId, String preAuthCode,
                                            String redirectUri) throws UnsupportedEncodingException {
        HashMap<String, String> params = new HashMap<>();
        params.put("tp_client_id", tpClientId);
        params.put("pre_auth_code", preAuthCode);
        params.put("redirect_uri", URLEncoder.encode(redirectUri, "utf-8"));

        return XzhConst.URI_AUTH_TP + '?' + CommonUtils.httpBuildQuery(params);
    }

    /**
     * 熊掌号授权-获取熊掌号accessToken
     *
     * @param tpAccessToken tp 调用凭据
     * @param authorizationCode 熊掌号授权码
     * @return
     * @throws URISyntaxException
     */
    public String getAuthAccessToken(String tpAccessToken, String authorizationCode)
            throws URISyntaxException {
        HashMap<String, String> params = new HashMap<>();
        params.put("tp_access_token", tpAccessToken);
        params.put("authorization_code", authorizationCode);

        String url = XzhConst.URI_REST_PREFIXS + '/' + XzhConst.OPENAPI_TP_API_QUERYAUTH;
        XzhResponse res = this.getInvoke(url, params, XzhConst.CONNECTION_TIMEOUT_MILLS, XzhConst
            .SOCKET_TIMEOUT_MILLIS);
        return res.getBody();
    }

    /**
     * 熊掌号授权-refresh_token刷新接口调用凭据
     *
     * @param tpAccessToken tp 调用凭证
     * @param refreshToken 熊掌号授权 refreshToken
     * @return
     * @throws URISyntaxException
     */
    public String getRefreshToken(String tpAccessToken, String refreshToken) throws URISyntaxException {
        HashMap<String, String> params = new HashMap<>();
        params.put("tp_access_token", tpAccessToken);
        params.put("refresh_token", refreshToken);

        String url = XzhConst.URI_REST_PREFIXS + '/' + XzhConst.OPENAPI_TP_API_AUTHORIZER_TOKEN;
        XzhResponse res = this.getInvoke(url, params, XzhConst.CONNECTION_TIMEOUT_MILLS, XzhConst
            .SOCKET_TIMEOUT_MILLIS);
        return res.getBody();
    }

    /**
     * 熊掌号授权-获取授权熊掌号信息
     *
     * @param accessToken 熊掌号授权 token
     * @return
     * @throws URISyntaxException
     */
    public String getAuthorizerInfo(String accessToken) throws URISyntaxException {
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", accessToken);

        String url = XzhConst.URI_REST_PREFIXS + '/' + XzhConst.OPENAPI_TP_AUTHORIZER_INFO;
        XzhResponse res = this.getInvoke(url, params, XzhConst.CONNECTION_TIMEOUT_MILLS, XzhConst
            .SOCKET_TIMEOUT_MILLIS);
        return res.getBody();
    }

    /**
     * 代熊掌号发起网页授权-获取授权码链接
     *
     * @param tpClientId TP 开发者 ID
     * @param clientId 熊掌号 ID
     * @param redirectUri Code回调地址
     * @param scope 授权权限
     * @param noLogin 是否验证登录
     * @param state 透传参数
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getAuthorizeRedirectUrl(String tpClientId, String clientId, String redirectUri,
                                          String scope, Integer noLogin, String state)
            throws UnsupportedEncodingException {
        HashMap<String, String> params = new HashMap<>();
        params.put("response_type", XzhConst.RESPONSE_TYPE);
        params.put("client_id", clientId);
        params.put("redirect_uri", URLEncoder.encode(redirectUri, "utf-8"));
        params.put("scope", scope);
        params.put("tp_client_id", tpClientId);

        if (noLogin != null) {
            params.put("pass_no_login", String.valueOf(noLogin));
        }

        if (state != null) {
            params.put("state", state);
        }

        return XzhConst.URI_AUTHORIZE + '?' + CommonUtils.httpBuildQuery(params);
    }

    /**
     * 代熊掌号发起网页授权-获取网页授权accessToken
     *
     * @param tpClientId  TP 开发者 ID
     * @param tpAccessToken TP 调用凭证
     * @param clientId 熊掌号 ID
     * @param authorizationCode 熊掌号授权码
     * @param redirectUri 网页授权完毕回调地址
     * @return json
     * @throws UnsupportedEncodingException
     * @throws URISyntaxException
     */
    public String getAccessTokenByTpAuthorizationCode(String tpClientId, String tpAccessToken,
                                                      String clientId, String authorizationCode, String redirectUri)
            throws UnsupportedEncodingException, URISyntaxException {
        HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", XzhConst.TP_AUTHORIZATION_CODE);
        params.put("code", authorizationCode);
        params.put("client_id", clientId);
        params.put("tp_client_id", tpClientId);
        params.put("tp_access_token", tpAccessToken);
        params.put("redirect_uri", URLEncoder.encode(redirectUri, "utf-8"));

        XzhResponse res = this.getInvoke(XzhConst.URI_OAUTH_TOKEN, params, XzhConst.CONNECTION_TIMEOUT_MILLS,
            XzhConst.SOCKET_TIMEOUT_MILLIS);
        return res.getBody();
    }

    /**
     * 代熊掌号发起网页授权-拉取用户信息
     *
     * @param accessToken 用户授权 token
     * @param clientId 熊掌号 ID
     * @param openId 用户 openId
     * @return json
     * @throws URISyntaxException
     */
    public String getUserInfoByOpenId(String accessToken, String clientId, String openId)
            throws URISyntaxException {
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", accessToken);
        params.put("client_id", clientId);
        params.put("openid", openId);

        String url = XzhConst.URI_REST_PREFIXS + '/' + XzhConst.OPENAPI_SNS_USERINFO;
        XzhResponse res = this.getInvoke(url, params, XzhConst.CONNECTION_TIMEOUT_MILLS, XzhConst
            .SOCKET_TIMEOUT_MILLIS);
        return res.getBody();
    }

    /**
     * 代熊掌号发起网页授权-刷新接口调用凭据
     *
     * @param tpClientId  TP 开发者 ID
     * @param tpAccessToken TP调用凭证
     * @param clientId  熊掌号 ID
     * @param refreshToken 用户授权 refreshToken
     * @return json
     * @throws URISyntaxException
     */
    public String getAccessTokenByTpRefreshToken(String tpClientId, String tpAccessToken, String clientId,
                                                 String refreshToken) throws URISyntaxException {
        HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", XzhConst.TP_REFRESH_CODE);
        params.put("refresh_token", refreshToken);
        params.put("client_id", clientId);
        params.put("tp_client_id", tpClientId);
        params.put("tp_access_token", tpAccessToken);

        XzhResponse res = this.getInvoke(XzhConst.URI_OAUTH_TOKEN, params, XzhConst.CONNECTION_TIMEOUT_MILLS,
            XzhConst.SOCKET_TIMEOUT_MILLIS);
        return res.getBody();
    }

}
