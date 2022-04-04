package com.zqh.fyp;

import com.zqh.fyp.Util.DB.DBUtil;
import com.zqh.fyp.Util.DB.UsersInfo;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
        DBUtil.updateContact(2,"c@qq.com");
    }

    @Test
    public void net() {

    }
}