package com.zqh.fyp.Util.DB;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBUtil {
    private static Connection con;

    public static void init() {
        String URL = "jdbc:mysql://101.132.139.104:3306/androidsInfo?user=root&password=stzqh!520";
        // 1.加载驱动程序
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // 2.获得数据库链接
            con = DriverManager.getConnection(URL);
            return;
        } catch (Exception ignored) {

        }
        con = null;
    }

    public static Connection getCon() {
        if (con == null) init();
        return con;
    }

    private static ResultSet getAllAccelerateInfo() {
        try {
            con = getCon();
            return con.createStatement().executeQuery("select * from accelerate_Info");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<AccelerateInfo> getInfoArray() {
        ArrayList<AccelerateInfo> list = new ArrayList<>();
        ResultSet result = getAllAccelerateInfo();

        try {
            while (result.next()) {
                list.add(new AccelerateInfo(
                        result.getInt("id"),
                        Long.parseLong(result.getString("runtime")),
                        Double.parseDouble(result.getString("x")),
                        Double.parseDouble(result.getString("y")),
                        Double.parseDouble(result.getString("z")),
                        result.getInt("state")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }

    public static UsersInfo getUserInfo(int id) {
        try {
            con = getCon();
            if (con == null) return null;
            ResultSet resultSet = con.createStatement().executeQuery("select * from user_Info where id = " + id);
            if (resultSet.next()) {
                return new UsersInfo(id, resultSet.getString("username"), resultSet.getString("pwd"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param username
     * @return id：-1网络超时 0用户不存在
     */
    public static UsersInfo getUserInfo(String username) {
        try {
            con = getCon();
            if (con == null) return new UsersInfo(-1, "", "");
            ResultSet resultSet = con.createStatement().executeQuery("select * from user_Info where username = \"" + username + "\"");
            if (resultSet.next()) {
                return new UsersInfo(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("pwd"));
            } else {
                //如果用户不存在 返回特殊值
                return new UsersInfo(0, "", "");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void insertUserInfo(String username, String password) {
        try {
            con = getCon();
            con.createStatement().execute("insert into user_Info values(null,\"" + username + "\",\"" + password + "\")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}




