package com.zqh.fyp;

import com.zqh.fyp.Util.DB.DBUtil;
import com.zqh.fyp.Util.DB.UsersInfo;
import org.junit.Test;

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
        UsersInfo info = DBUtil.getUserInfo(1);
        System.out.println(info.username);
    }
}