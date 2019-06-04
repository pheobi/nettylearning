package com.cn.module.fuben.response;

import com.cn.serial.Serializer;

/**
 * @author liyahui
 * @create 2019-06-04
 */
public class FightResponse extends Serializer {
    /**获取金币*/
    private int gold;

    @Override
    protected void read() {
        this.gold = readInt();
    }

    @Override
    protected void write() {
        writeInt(gold);
    }
}
