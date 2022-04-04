package com.zqh.fyp.Util.DB;

public class AccelerateInfo {
    public int id;
    public int uid;
    public long currentTime;
    public double xA;
    public double yA;
    public double zA;
    public double xG;
    public double yG;
    public double zG;
    public int state;

    public AccelerateInfo(int id, int uid, long currentTime, double xA, double yA, double zA, double xG, double yG, double zG, int state) {
        this.id = id;
        this.uid = uid;
        this.currentTime = currentTime;
        this.xA = xA;
        this.yA = yA;
        this.zA = zA;
        this.xG = xG;
        this.yG = yG;
        this.zG = zG;
        this.state = state;
    }

    public double getTotalA() {
        return Math.pow(xA * xA + yA * yA + zA * zA, 1.0 / 2.0);
    }

    public double getTotalG() {
        return Math.pow(xG * xG + yG * yG + zG * zG, 1.0 / 2.0);
    }
}
