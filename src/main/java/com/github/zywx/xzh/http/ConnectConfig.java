package com.github.zywx.xzh.http;

/**
 * 连接配置
 *
 * @author fsnail.wang@gmail.com
 * @date 2018/3/14 上午10:15
 */
public class ConnectConfig {

    private int connectionTimeoutMillis;
    private int socketTimeoutMillis;

    public ConnectConfig() {
        this.connectionTimeoutMillis = 0;
        this.socketTimeoutMillis = 0;
    }

    public ConnectConfig(int connectionTimeoutMillis, int socketTimeoutMillis) {
        this.connectionTimeoutMillis = connectionTimeoutMillis;
        this.socketTimeoutMillis = socketTimeoutMillis;
    }

    public int getConnectionTimeoutMillis() {
        return connectionTimeoutMillis;
    }

    public void setConnectionTimeoutMillis(int connectionTimeoutMillis) {
        this.connectionTimeoutMillis = connectionTimeoutMillis;
    }

    public int getSocketTimeoutMillis() {
        return socketTimeoutMillis;
    }

    public void setSocketTimeoutMillis(int socketTimeoutMillis) {
        this.socketTimeoutMillis = socketTimeoutMillis;
    }

}
