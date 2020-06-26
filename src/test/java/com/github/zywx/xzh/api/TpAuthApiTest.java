package com.github.zywx.xzh.api;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

/**
 *  TP 授权操作类测试
 *
 * @author fsnail.wang@gmail.com
 * @date 2018/3/17 下午4:40
 */
public class TpAuthApiTest {
    private static final Logger LOGGER = Logger.getLogger(AuthApiTest.class);
    private static String tpClientId = "test";
    private static String tpClientSecret = "test";
    private static String tpAccesstoken = "42.5.7200.1522302983.b_IRnP3PMNi-lrNE2qyfWx6W3SesXh";
    private static TpAuthApi auth;
    
    public TpAuthApiTest() {
        auth = new TpAuthApi();
    }

    /**
     * 获取第三方平台接口调用凭据
     * @throws Exception
     */
    @Test
    public void testGetTpAccessTokenByTpCredentials() throws Exception {
        String tpVerifyTicket = "fedcff1948747828078";
        String ret = auth.getTpAccessToken(tpClientId, tpClientSecret, tpVerifyTicket);
        LOGGER.info(ret);
    }

    /**
     * 熊掌号授权-获取预授权码
     * @throws Exception
     */
    @Test
    public void testGetPreAuthCodeByTpAccessToken() throws Exception {
//        String debug = "1"
        String preAuthCode = auth.getPreAuthCodeByTpAccessToken(tpAccesstoken, null);
        LOGGER.info(preAuthCode);
    }

    /**
     * 熊掌号授权-获取预授权码授权链接
     * @throws UnsupportedEncodingException
     */
    @Test
    public void testGetTpAuthorizeRedirectUrl() throws UnsupportedEncodingException {
        String preAuthCode = "PREAUTHCODE@@@dxgLsxA3sqbwYSA7GmZGxepcGTG6biX7cuvcZwN1WefB5un3kVLs4UBfg0IkH";
        String redirectUri = "http://domain:8080/xzhsdk/tp.php";
        String tpAuthorizeRedirectUrl = auth.getTpAuthorizeRedirectUrl(tpClientId, preAuthCode, redirectUri);
        LOGGER.info(tpAuthorizeRedirectUrl);
    }

    /**
     * 熊掌号授权-获取熊掌号accessToken
     * @throws URISyntaxException
     */
    @Test
    public void testGetAccessTokenByAuthorizationCode() throws URISyntaxException {
        String authorizationCode = "mzs8kqJ623xEZz14w8ZvfSfkRqfIzEfmR9yAsv4TgzL8Tm9yn7gkRYfvZ6w";
        String accessTokenByAuthorCode = auth.getAuthAccessToken(tpAccesstoken, authorizationCode);
        LOGGER.info(accessTokenByAuthorCode);
    }

    /**
     * 熊掌号授权-refresh_token刷新接口调用凭据
     * @throws URISyntaxException
     */
    @Test
    public void testGetAccessTokenByRefreshToken() throws URISyntaxException {
        String refreshToken = "46.4e3.315360000.1837656807.WFX8yCH-uhS8iBMhjgF8q9UaeqHs-lrNE2qyfWx6W3SesXh";
        String accessTokenByRefreshToken = auth.getRefreshToken(tpAccesstoken, refreshToken);
        LOGGER.info(accessTokenByRefreshToken);
    }

    /**
     * 熊掌号授权-获取授权熊掌号信息
     * @throws URISyntaxException
     */
    @Test
    public void testGetAuthorizerInfo() throws URISyntaxException {
        String accessToken = "45.b97.7200.1522304207.W8LIZ8_0Jz2-JCIcRRnq57KhF-lrNE2qyW3SesXh";
        String authorizerInfo = auth.getAuthorizerInfo(accessToken);
        LOGGER.info(authorizerInfo);
    }

    /**
     * 代熊掌号发起网页授权-获取授权码链接
     * @throws UnsupportedEncodingException
     */
    @Test
    public void testGetAuthorizeRedirectUrl() throws UnsupportedEncodingException {
        String clientId = "R6HzvBSGAvkFMfH2No86t1k";
        String redirectUri = "http://domain:8080/xzhsdk/tp.php";
        String scope = "snsapi_userinfo";
        String state = "dtest=1";
        String authorizeRedirectUrl = auth.getAuthorizeRedirectUrl(tpClientId, clientId,
                redirectUri, scope, null, state);
        LOGGER.info(authorizeRedirectUrl);
    }

    /**
     * 代熊掌号发起网页授权-获取网页授权accessToken
     * @throws UnsupportedEncodingException
     * @throws URISyntaxException
     */
    @Test
    public void testGetAccessTokenByTpAuthorizationCode() throws UnsupportedEncodingException, URISyntaxException {
        String clientId = "test";
        String authorizationCode = "13eb882873ecd9d9ed19269e";
        String redirectUri = "http://domain:8080/xzhsdk/tp.php";
        String accessTokenByTpAuthorizationCode = auth.getAccessTokenByTpAuthorizationCode(tpClientId, tpAccesstoken,
            clientId, authorizationCode, redirectUri);
        LOGGER.info(accessTokenByTpAuthorizationCode);
    }

    /**
     * 代熊掌号发起网页授权-拉取用户信息
     * @throws URISyntaxException
     */
    @Test
    public void testGetUserInfoByOpenId() throws URISyntaxException {
        String accessToken = "43.d1cb1c.7200.1522308639.U6YMSycskdxAjD0sbYN_svXeYETZCBFfiJA-lrNE2qyfWx6W3SesXh";
        String clientId = "R6HzvBSGAvkFMUrhELUZayfH2No86t1k";
        String openId = "36GetTfdgWREIDdMUpz_RoM_c2";
        String userInfo = auth.getUserInfoByOpenId(accessToken, clientId, openId);
        LOGGER.info(userInfo);
    }

    /**
     * 代熊掌号发起网页授权-刷新接口调用凭据
     * @throws URISyntaxException
     */
    @Test
    public void testGetAccessTokenByTpRefreshToken() throws URISyntaxException {
        String clientId = "R6HzvBSGAvkFMUrhELUZayfH2No86t1k";
        String rt = "44.37f90e3ca5e.2592000.1524893876.ecM8ha76HF6FEoROp1XtjfLR97KbxjEoc_Y-lrNE2qyfWx6W3SesXh";
        String accessTokenByTpRefreshToken = auth.getAccessTokenByTpRefreshToken(tpClientId, tpAccesstoken,
            clientId, rt);
        LOGGER.info(accessTokenByTpRefreshToken);
    }
}
