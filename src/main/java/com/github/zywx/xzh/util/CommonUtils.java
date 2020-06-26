package com.github.zywx.xzh.util;

import java.util.Map;

/**
 * 公共工具类
 *
 * @author fsnail.wang@gmail.com
 * @date 2018/3/15 下午3:24
 */
public class CommonUtils {

    /**
     * http params build
     *
     * @param maps
     * @return
     */
    public static String httpBuildQuery(Map<String, String> maps) {
        String reString = "";
        for (String key : maps.keySet()) {
            String value = maps.get(key);
            reString += key + "=" + value + "&";
        }

        return reString.substring(0, reString.length() - 1);
    }
}