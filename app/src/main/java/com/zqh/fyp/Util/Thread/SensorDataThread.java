package com.zqh.fyp.Util.Thread;

import com.zqh.fyp.MainActivity;
import com.zqh.fyp.UsersInfo_activity;
import com.zqh.fyp.Util.DB.DBUtil;

public class SensorDataThread extends Thread {

    @Override
    public void run() {
        while (!UsersInfo_activity.isPause){
            DBUtil.insertSensorInfo(
                    0,
                    MainActivity.user.id,
                    System.currentTimeMillis(),
                    UsersInfo_activity.xAccelerate,
                    UsersInfo_activity.yAccelerate,
                    UsersInfo_activity.zAccelerate,
                    UsersInfo_activity.xGyro,
                    UsersInfo_activity.yGyro,
                    UsersInfo_activity.zGyro,
                    0);
            try {
                this.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
