package com.github.zywx.xzh.api;

import com.github.zywx.xzh.consts.XzhConst;
import com.github.zywx.xzh.http.BodyFormatEnum;
import com.github.zywx.xzh.http.XzhResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.testng.annotations.AfterTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * OpenAPI 调用基类测试
 *
 * @author fsnail.wang@gmail.com
 * @date 2018/3/17 上午10:40
 */
public class OpenApiTest {
    private static final Logger LOGGER = Logger.getLogger(OpenApiTest.class);
    private static OpenApi openApi = null;
    private static String accessToken = "24.c0ac92490b3bf3f5a.7200.1522244806.28235-10229144";

    public OpenApiTest() {
        openApi = new OpenApi();
    }

    /**
     * 测试用例-获取用户基本信息接口
     */
    @Test
    public void testUserInfo() throws Exception {
        String userInfoApi = "user/info";
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", accessToken);

        JSONObject json = new JSONObject();
        JSONArray userList = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("openid", "36GetTQ2Qh0kpMluyUsRp2_c14");
        userList.put(jsonObject);
        json.put("user_list", userList);
        LOGGER.info(json.toString());

        XzhResponse res = openApi.postInvoke(userInfoApi, params, null, json, BodyFormatEnum.RAW_JSON,
            XzhConst.CONNECTION_TIMEOUT_MILLS, XzhConst.SOCKET_TIMEOUT_MILLIS);
        LOGGER.info(res.getBody());
    }

    /**
     * 测试用例-获取粉丝列表
     */
    @AfterTest
    public void testUserGet() throws Exception {
        String userGetApi = "user/get";
        String startIndex = "0";

        HashMap<String, String> params = new HashMap<>();
        params.put("start_index", startIndex);
        params.put("access_token", accessToken);
        XzhResponse res = openApi.getInvoke(userGetApi, params, XzhConst.CONNECTION_TIMEOUT_MILLS, XzhConst
            .SOCKET_TIMEOUT_MILLIS);
        LOGGER.info(res.getBody());
    }

    /**
     * 测试用例-上传图片
     */
    @Test
    public void testMediaUploadimg() throws Exception {
        String mediaUploadImgApi = "media/uploadimg";
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", accessToken);

        HashMap<String, Object> formParams = new HashMap<>();
        String file = "/Users/xx/Desktop/PIC-/test.jpg";
        File fileObj = new File(file);
        formParams.put("media", fileObj);

        XzhResponse res = openApi.postInvoke(mediaUploadImgApi, params, formParams, null, BodyFormatEnum.FORM_KV,
            XzhConst.CONNECTION_TIMEOUT_MILLS, XzhConst.SOCKET_TIMEOUT_MILLIS);
        LOGGER.info(res.getBody());
    }

    /**
     * 测试用例-上传图片
     */
    @Test
    public void testMediaUploadimg2() throws Exception {
        String mediaUploadImgApi = "media/uploadimg";
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", accessToken);

        HashMap<String, Object> formParams = new HashMap<>();
        String file = "/Users/xx/Desktop/PIC-/test.jpg";
        File fileObj = new File(file);
        formParams.put("media", fileObj);

        XzhResponse res = openApi.postInvoke(mediaUploadImgApi, params, formParams, null, BodyFormatEnum.FORM_KV,
                XzhConst.CONNECTION_TIMEOUT_MILLS, XzhConst.SOCKET_TIMEOUT_MILLIS);
        LOGGER.info(res.getBody());
    }

    /**
     * 测试用例-上传图片
     */
    @Test
    public void testMediaUploadimg3() throws Exception {
        String mediaUploadImgApi = XzhConst.OPEN_DOMAIN + "/file/2"
                + ".0/midplatform_test/api/account_upload";
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", "24.fa32d0d16b254bbe5db064e2d6f2010a.7200.1574770219.282335-17727393");

        HashMap<String, Object> formParams = new HashMap<>();
        String file = "/Users/zywx/Desktop/PIC-/test.jpg";
        File fileObj = new File(file);
        formParams.put("media", fileObj);

        XzhResponse res = openApi.postInvokeMid(mediaUploadImgApi, params, formParams, null, BodyFormatEnum.FORM_KV,
                XzhConst.CONNECTION_TIMEOUT_MILLS, XzhConst.SOCKET_TIMEOUT_MILLIS);
        LOGGER.info(res.getBody());
    }

    /**
     * 对图片进行Base64编码处理
     * @param imgFile
     * @return
     */
    public String base64Image(String imgFile) {
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(Base64.encodeBase64(data));
    }
}
