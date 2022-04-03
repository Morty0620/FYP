package com.zqh.fyp.Util.DB;

public class AccelerateInfo {
    int id;
    long currentTime;
    double x;
    double y;
    double z;
    int state;

    public AccelerateInfo(int id, long currentTime, double x, double y, double z, int state) {
        this.id = id;
        this.currentTime = currentTime;
        this.x = x;
        this.y = y;
        this.z = z;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getTotalAcceleration(){
        return Math.pow(x*x+y*y+z*z,1.0/2.0);
    }
}
