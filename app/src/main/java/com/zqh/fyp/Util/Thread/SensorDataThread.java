package com.zqh.fyp.Util.Thread;

import android.app.Activity;
import com.zqh.fyp.MainActivity;
import com.zqh.fyp.SensorData;
import com.zqh.fyp.UsersInfo_Activity;
import com.zqh.fyp.Util.DB.DBUtil;

public class SensorDataThread extends Thread {

    @Override
    public void run() {
        try {
            this.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!UsersInfo_Activity.isPause) {
            System.out.println("running");
            DBUtil.insertSensorInfo(
                    0,
                    MainActivity.user.id,
                    System.currentTimeMillis(),
                    SensorData.xAccelerate,
                    SensorData.yAccelerate,
                    SensorData.zAccelerate,
                    SensorData.xGyro,
                    SensorData.yGyro,
                    SensorData.zGyro,
                    SensorData.walkState,
                    SensorData.fallState,
                    SensorData.stableState);
            try {
                this.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
