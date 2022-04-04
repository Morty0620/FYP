package com.zqh.fyp.Util.Thread;

import com.zqh.fyp.Login_Activity;
import com.zqh.fyp.MainActivity;
import com.zqh.fyp.Util.DB.DBUtil;
import com.zqh.fyp.Util.DB.UsersInfo;

public class LoginThread extends Thread {
    String username;
    String password;

    public LoginThread(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        UsersInfo userInfo = DBUtil.getUserInfo(username);
        if (userInfo.id==-1){
            //网络超时
            Login_Activity.loginState = -1;
            return;
        }
        if (userInfo.password.equals(this.password)) {
            //密码正确
            Login_Activity.loginState = 1;
            MainActivity.user=userInfo;
        } else if (userInfo.id == 0) {
            //用户名不存在
            Login_Activity.loginState = 3;
        } else {
            //密码错误
            Login_Activity.loginState = 2;
        }
    }
}
