package com.zqh.fyp;

import com.zqh.fyp.Util.DB.DBUtil;
import com.zqh.fyp.Util.DB.UsersInfo;
import com.zqh.fyp.Util.Email.SendEmail;
import org.junit.Test;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void DB() {
//        DBUtil.insertUserInfo("test2", "123");
//        DBUtil.insertSensorInfo(1,1,1,1,1,1,1,1,1,1);
        DBUtil.updateContact(2, "c@qq.com");
    }

    @Test
    public void net() {

    }

    @Test
    public void mail() throws InterruptedException {
        new Thread(){
            @Override
            public void run() {
                try {
                    SendEmail.send("zhangqihang0620@qq.com");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        Thread.sleep(5000);
    }
}