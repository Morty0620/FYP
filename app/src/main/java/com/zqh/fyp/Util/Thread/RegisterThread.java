package com.zqh.fyp.Util.Thread;

import com.zqh.fyp.Register_Activity;
import com.zqh.fyp.Util.DB.DBUtil;

public class RegisterThread extends Thread {
    String username;
    String password;

    public RegisterThread(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        DBUtil.insertUserInfo(username, password,"");
    }
}
