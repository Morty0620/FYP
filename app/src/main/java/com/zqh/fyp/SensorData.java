package com.zqh.fyp;

public class SensorData {
    //传感器数据
    public static double xAccelerate;
    public static double yAccelerate;
    public static double zAccelerate;
    public static double xGyro;
    public static double yGyro;
    public static double zGyro;
    public static int state; //0不确定 1walk 2fall 3stable

    public static void reSet() {
        xAccelerate = 0;
        yAccelerate = 0;
        zAccelerate = 0;
        xGyro = 0;
        yGyro = 0;
        zGyro = 0;
        state = 0;
    }
}
