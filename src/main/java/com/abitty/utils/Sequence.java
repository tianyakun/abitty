package com.abitty.utils;

/**
 * Created by yak on 17/6/15.
 */
public class Sequence {

    private static  final SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);

    public static long next() {
        return idWorker.nextId();
    }
}
