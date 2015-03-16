package com.blackboxgaming.engine.util;

public class Stopwatch {

    private static long time;
    private static int diff;

    public static void start() {
        time = System.currentTimeMillis();
        diff = 0;
    }

    public static int stop() {
        diff = (int) (System.currentTimeMillis() - time);
        time = 0;
        return diff;
    }

    public static int time() {
        return diff;
    }
}
