package com.zqh.fyp.Util.Thread;

import com.zqh.fyp.Util.Email.SendEmail;

public class SendEmailThread extends Thread {
    String receiveMailAccount;
    String title;
    String content;

    public SendEmailThread(String receiveMailAccount,
                           String title,
                           String content) {
        this.receiveMailAccount = receiveMailAccount;
        this.title = title;
        this.content = content;
    }

    @Override
    public void run() {
        try {
            SendEmail.setInfo(title, content);
            SendEmail.send(receiveMailAccount);
//            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
