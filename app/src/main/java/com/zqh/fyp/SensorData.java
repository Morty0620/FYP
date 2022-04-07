package com.zqh.fyp;

public class SensorData {
    //传感器数据
    public static double xAccelerate;
    public static double yAccelerate;
    public static double zAccelerate;
    public static double xGyro;
    public static double yGyro;
    public static double zGyro;
    public static int walkState;
    public static int fallState;
    public static int stableState;

    public static void reSet() {
        xAccelerate = 0;
        yAccelerate = 0;
        zAccelerate = 0;
        xGyro = 0;
        yGyro = 0;
        zGyro = 0;
        walkState = 0;
        fallState = 0;
        stableState = 0;
    }

    public static void reSetState() {
        walkState = 0;
        fallState = 0;
        stableState = 0;
    }

    ;
}
