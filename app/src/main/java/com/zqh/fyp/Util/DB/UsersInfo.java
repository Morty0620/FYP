package com.zqh.fyp.Util.DB;

/**
 * Created by renkai on 17/7/8.
 */

public class UsersInfo {
    public int id;

    public String username;
    public String password;
    public String contact;

    public UsersInfo(int id, String username, String password, String contact) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.contact = contact;
    }
}
