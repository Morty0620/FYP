package com.zqh.fyp.Util.Thread;

import com.zqh.fyp.Register_Activity;
import com.zqh.fyp.Util.DB.DBUtil;
import com.zqh.fyp.Util.DB.UsersInfo;

public class RegisterCheckThread extends Thread {
    String username;
    String password;

    public RegisterCheckThread(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        UsersInfo userInfo = DBUtil.getUserInfo(username);
        if (userInfo.id > 0) {
            Register_Activity.registerState = 2;
            return;
        }
        if (userInfo.id == -1) {
            Register_Activity.registerState = -1;
            return;
        }
        Register_Activity.registerState = 1;
    }
}
