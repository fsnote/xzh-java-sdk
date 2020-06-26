package com.github.zywx.xzh.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 字节处理
 * @author fsnail.wang@gmail.com
 * @date 2018/3/15 下午2:20
 */
public class ByteGroup {

    private List<Byte> bytesList = new ArrayList<>();

    /**
     * 返回字节数组
     *
     * @return
     */
    public byte[] toBytes() {
        byte[] bytes = new byte[bytesList.size()];
        for (int i = 0; i < bytesList.size(); i++) {
            bytes[i] = bytesList.get(i);
        }
        return bytes;
    }

    /**
     * 添加字节
     *
     * @param bytes
     *
     * @return
     */
    public ByteGroup addBytes(byte[] bytes) {
        for (byte b : bytes) {
            bytesList.add(b);
        }
        return this;
    }

    /**
     * 求字节大小
     *
     * @return
     */
    public int size() {
        return bytesList.size();
    }
}