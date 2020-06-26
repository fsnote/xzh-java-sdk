package com.github.zywx.xzh.http;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * http 工具类
 *
 * @author fsnail.wang@gmail.com
 * @date 2018/3/14 上午10:15
 */
public class XzhHttpClient {
    private static Logger LOGGER = Logger.getLogger(XzhHttpClient.class);
    private static final String ENCODING = "utf-8";

    /**
     * post curl
     *
     * @param request 请求实体
     * @return 响应实体
     */
    public static XzhResponse post(XzhRequest request) {
        XzhResponse xzhResponse = null;
        try {
            String url;
            xzhResponse = new XzhResponse();
            if (request.getParams().isEmpty()) {
                url = request.getUri().toString();
            } else {
                url = String.format("%s?%s", request.getUri().toString(), request.getParamStr());
            }

            String result = "";
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            int timeout = request.getConfig().getSocketTimeoutMillis();
            int connTime = request.getConfig().getConnectionTimeoutMillis();
            RequestConfig.Builder custom = RequestConfig.custom();
            RequestConfig requestConfig = custom.setSocketTimeout(timeout).setConnectTimeout(connTime).build();
            httpPost.setConfig(requestConfig);

            if (request.getHeaders() != null) {
                for (Entry<String, String> entry : request.getHeaders().entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }

            // 参数处理并放到请求对象中
            if (request.getBodyFormat().equals(BodyFormatEnum.FORM_KV)) {
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                if (request.getFormParams() != null) {
                    for (Entry<String, Object> entry : request.getFormParams().entrySet()) {
                        String paramKey = entry.getKey();
                        Object value = entry.getValue();
                        if (value instanceof File) {
                            FileBody fileBody = new FileBody((File) value);
                            builder.addPart(paramKey, fileBody);
                        } else {
                            StringBody name = new StringBody(value.toString(),
                                    ContentType.TEXT_PLAIN.withCharset(ENCODING));
                            builder.addPart(paramKey, name);
                        }
                    }
                }

                HttpEntity entity = builder.build();
                httpPost.setEntity(entity);
            } else if (request.getBodyFormat().equals(BodyFormatEnum.RAW_JSON)) {
                httpPost.setEntity(new StringEntity(request.getBodyStr()));
            }

            // 发送请求，获取结果（同步阻塞）
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, ENCODING);
            }
            EntityUtils.consume(entity);
            int statusCode = response.getStatusLine().getStatusCode();
            xzhResponse.setStatus(statusCode);
            xzhResponse.setBody(result);
            response.close();
        } catch (IOException e) {
            LOGGER.error(e);
        }

        return xzhResponse;
    }

    /**
     * get curl
     *
     * @param request 请求实体
     * @return 响应实体
     */
    public static XzhResponse get(XzhRequest request) {
        XzhResponse xzhResponse = null;
        try {
            String url;
            xzhResponse = new XzhResponse();
            if (request.getParams().isEmpty()) {
                url = request.getUri().toString();
            } else {
                url = String.format("%s?%s", request.getUri().toString(), request.getParamStr());
            }

            String result = "";
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            // 设置请求和传输超时时间
            int timeout = request.getConfig().getSocketTimeoutMillis();
            int connTime = request.getConfig().getConnectionTimeoutMillis();
            RequestConfig.Builder custom = RequestConfig.custom();
            RequestConfig requestConfig = custom.setSocketTimeout(timeout).setConnectTimeout(connTime).build();
            httpGet.setConfig(requestConfig);

            if (request.getHeaders() != null) {
                for (Entry<String, String> entry : request.getHeaders().entrySet()) {
                    httpGet.setHeader(entry.getKey(), entry.getValue());
                }
            }

            CloseableHttpResponse response = httpClient.execute(httpGet);
            // 获取结果实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, ENCODING);
            }
            EntityUtils.consume(entity);

            int statusCode = response.getStatusLine().getStatusCode();
            xzhResponse.setStatus(statusCode);
            xzhResponse.setBody(result);
            response.close();
        } catch (IOException e) {
            LOGGER.error(e);
        }

        return xzhResponse;
    }
}