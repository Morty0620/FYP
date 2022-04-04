package com.zqh.fyp.Util.Thread;

import com.zqh.fyp.Util.DB.DBUtil;

public class ContactChangeThread extends Thread{
    int id;
    String contact;

    public ContactChangeThread(int id, String contact){
        this.id=id;
        this.contact=contact;
    }

    @Override
    public void run() {
        DBUtil.updateContact(id,contact);
    }
}
