package com.zqh.fyp;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import android.widget.TextView;
import com.zqh.fyp.Util.Email.SendEmail;
import com.zqh.fyp.Util.Thread.ContactChangeThread;
import com.zqh.fyp.Util.Thread.SendEmailThread;
import com.zqh.fyp.Util.Thread.SensorDataThread;

import java.math.BigDecimal;

public class UsersInfo_activity extends Activity implements View.OnClickListener {

    private TextView text_name, text_condition, text_contact;
    private String name;
    private Button btn_logout, btn_change;

    //传感器
    private SensorManager sensorManager;
    private Sensor accelerateSensor;
    private Sensor gyroSensor = null;
    private SensorEventListener accelerateSensorListener;
    private SensorEventListener gyroSensorListener;

    //传感器数据
    public static double xAccelerate;
    public static double yAccelerate;
    public static double zAccelerate;
    public static double xGyro;
    public static double yGyro;
    public static double zGyro;

    //进程状态
    public static boolean isPause = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo_activity);
        init();
    }

    protected void init() {
        Intent intent = getIntent();
        name = intent.getStringExtra("Username");
        text_name = (TextView) findViewById(R.id.text_name);
        text_name.setText(name);
        text_condition = (TextView) findViewById(R.id.text_condition);
        text_condition.setText("在线");
        text_contact = (TextView) findViewById(R.id.text_contact);
        text_contact.setText(MainActivity.user.contact);

        //注册按钮
        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);
        btn_change = (Button) findViewById(R.id.btn_change);
        btn_change.setOnClickListener(this);

        //注册加速度传感器
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        //注册监听器
        accelerateSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
                xAccelerate = values[0];
                yAccelerate = values[1];
                zAccelerate = values[2];
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        gyroSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
                xGyro = values[0];
                yGyro = values[1];
                zGyro = values[2];
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        //启动线程
        new SensorDataThread().start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //登出按钮点击
            case R.id.btn_logout: {
                isPause = true;
                MainActivity.user = null;
                Intent i = new Intent(UsersInfo_activity.this, Login_Activity.class);
                startActivity(i);
                break;
            }
            case R.id.btn_change: {
                new ContactChangeThread(MainActivity.user.id, text_contact.getText().toString()).start();
                Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
                MainActivity.user.contact = text_contact.getText().toString();
//                new SendEmailThread(text_contact.getText().toString(), SendEmail.TITLE_UPDATE, "用户:" + MainActivity.user.username + "将你设置为了紧急联系人").start();
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(accelerateSensorListener, accelerateSensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(gyroSensorListener, gyroSensor, SensorManager.SENSOR_DELAY_FASTEST);
        isPause = false;
        new SensorDataThread().start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(accelerateSensorListener);
        sensorManager.unregisterListener(gyroSensorListener);
        isPause = true;
    }

    public static void showInfo() {
        System.out.println(xAccelerate + "-" + yAccelerate + "-" + zAccelerate + "-" + xGyro + "-" + yGyro + "-" + zGyro + "-");
    }
}
