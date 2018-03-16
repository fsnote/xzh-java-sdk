package com.github.fsnote.http;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple class desc
 *
 * @author fsnail.wang@gmail.com
 * @date 2018/3/15 下午7:24
 */
public class XzhHttpClient {
    public static XzhResponse post(XzhRequest request) {
        String url;
        String charset = request.getContentEncoding();
        String content = request.getBodyStr();
        HashMap<String, String> header = request.getHeaders();
        XzhResponse response = new XzhResponse();
        try {
            if (request.getParams().isEmpty()) {
                url = request.getUri().toString();
            } else {
                url = String.format("%s?%s", request.getUri().toString(), request.getParamStr());
            }
            URL urlConsole = new URL(url);
            Proxy proxy = request.getConfig() == null ? Proxy.NO_PROXY : request.getConfig().getProxy();
            URLConnection urlConnection = urlConsole.openConnection(proxy);
            HttpURLConnection conn = (HttpURLConnection) urlConnection;

            // set timeout
            if (request.getContentEncoding() != null) {
                conn.setConnectTimeout(0);
                conn.setReadTimeout(0);
            }

            // set header
            for (Map.Entry<String, String> entry : header.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }

            conn.setDoOutput(true);
            // build connect
            conn.connect();
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.write(content.getBytes(charset));
            out.flush();
            out.close();

            int statusCode = conn.getResponseCode();
            response.setHeader(conn.getHeaderFields());
            response.setStatus(statusCode);
            response.setCharset(charset);
            if (statusCode != 200) {
                return response;
            }

            InputStream is = conn.getInputStream();
            if (is != null) {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                is.close();
                response.setBody(outStream.toByteArray());
            }
            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
