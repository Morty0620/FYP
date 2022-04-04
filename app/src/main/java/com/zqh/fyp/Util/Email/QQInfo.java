package com.zqh.fyp.Util.Email;

public class QQInfo extends EmailInfo{

    // 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
    // PS: 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）,
    //     对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
    public static String myEmailAccount = "zhangqihang0620@qq.com";
    public static String myEmailPassword = "oqivsakcubrdbcbg";

//    public static String myEmailAccount = "18722035@bjtu.edu.cn";
//    public static String myEmailPassword = "mciy4gZktxwxwAZQ";

    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
    // 网易126邮箱的 SMTP 服务器地址为: smtp.126.com
    public static String myEmailSMTPHost = "smtp.qq.com";
    public static String sslPort = "465";


    public static String getMyEmailAccount() {
        return myEmailAccount;
    }

    public static String getMyEmailPassword() {
        return myEmailPassword;
    }

    public static String getMyEmailSMTPHost() {
        return myEmailSMTPHost;
    }

    public static String getSslPort() {
        return sslPort;
    }
}
