package com.github.zywx.xzh.api;

import org.apache.log4j.Logger;
import org.junit.Test;


/**
 * 熊掌号授权操作类测试
 *
 * @author fsnail.wang@gmail.com
 * @date 2018/3/17 上午11:40
 */
public class AuthApiTest {
    private AuthApi auth;
    private static String clientId = "test";
    private static String clientSecret = "test";
    private static final Logger LOGGER = Logger.getLogger(AuthApiTest.class);

    public AuthApiTest() {
        auth = new AuthApi();
    }

    @Test
    public void testGetAccessTokenByClientCredentials() throws Exception {
        String ret = auth.getAccessToken(clientId, clientSecret);
        LOGGER.info(ret);
    }

    @Test
    public void testGetAuthorizeRedirectUrl() throws Exception {
        String redirectUri = "http://domain:8080/wx2.php";
        String scope = "snsapi_userinfo";
        String state = "dtest=1";
        String ret = auth.getAuthorizeRedirectUrl(clientId, redirectUri, scope, null, state);
        LOGGER.info(ret);
    }

    @Test
    public void testGetAccessTokenByAuthorizationCode() throws Exception {
        String authorizationCode = "c65e4dbe011fb52ae307d2f73b";
        String redirectUri = "http://domain:8080/wx2.php";
        String ret = auth.getAuthAccessToken(clientId, clientSecret, authorizationCode, redirectUri);
        LOGGER.info(ret);
    }

    @Test
    public void testGetUserInfoByOpenId() throws Exception {
        String accessToken = "90.8d.7200.1522135988.RrDXGYz9EbajIW1no_KoHoo-36GetTS5Gr6hZ9Fscj";
        String openId = "36GetTfdgDdMUpz_RoM_c2";
        String ret = auth.getUserInfoByOpenId(accessToken, openId);
        LOGGER.info(ret);
    }

    @Test
    public void testGetAccessTokenByRefreshToken() throws Exception {
        String refreshToken = "41.b4.2592000.1524721499.TwZdPjMghtruAFI4U4kD7q73ESw-36GetTS5Gr6hZ9Fscj";
        String ret = auth.getAccessTokenByRefreshToken(clientId, clientSecret, refreshToken);
        LOGGER.info(ret);
    }

}
