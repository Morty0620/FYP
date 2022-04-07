package com.zqh.fyp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import android.widget.TextView;
import com.zqh.fyp.Util.Thread.DebugSensorDataThread;

public class Debug_Activity extends Activity implements View.OnClickListener {

    private TextView text_name, text_condition;
    private String name;
    private Button btn_logout, btn_walk, btn_stop, btn_stable, btn_fall;

    //传感器
    private SensorManager sensorManager;
    private Sensor accelerateSensor;
    private Sensor gyroSensor = null;
    private SensorEventListener accelerateSensorListener;
    private SensorEventListener gyroSensorListener;

    //进程状态
    public static boolean isPause = true;

    //必要初始化属性
    public static boolean isInit = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_activity);
        init();
    }

    protected void init() {
        Intent intent = getIntent();
        name = intent.getStringExtra("Username");
        text_name = (TextView) findViewById(R.id.text_name);
        text_name.setText(name);
        text_condition = (TextView) findViewById(R.id.text_condition);
        text_condition.setText("在线");
        if (isPause) {
            ((TextView) findViewById(R.id.btn_stop)).setText("Resume");
            ((TextView) findViewById(R.id.btn_stop)).setTextColor(0xffff00ff);
        }

        //注册按钮
        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);
        btn_walk = (Button) findViewById(R.id.btn_walk);
        btn_walk.setOnClickListener(this);
        btn_fall = (Button) findViewById(R.id.btn_fall);
        btn_fall.setOnClickListener(this);
        btn_stable = (Button) findViewById(R.id.btn_stable);
        btn_stable.setOnClickListener(this);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(this);

        //注册加速度传感器
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        //注册监听器
        accelerateSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
                SensorData.xAccelerate = values[0];
                SensorData.yAccelerate = values[1];
                SensorData.zAccelerate = values[2];
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        gyroSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
                SensorData.xGyro = values[0];
                SensorData.yGyro = values[1];
                SensorData.zGyro = values[2];
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //登出按钮点击
            case R.id.btn_logout: {
                isPause = true;
                MainActivity.user = null;
                Intent i = new Intent(Debug_Activity.this, Login_Activity.class);
                startActivity(i);
                break;
            }
            case R.id.btn_stop: {
                SensorData.reSetState();
                if (isPause) {
                    ((TextView) findViewById(R.id.btn_stop)).setText("Stop");
                    ((TextView) findViewById(R.id.btn_stop)).setTextColor(Color.rgb(0, 0, 0));
                    onResume();
                } else {
                    ((TextView) findViewById(R.id.btn_stop)).setText("Resume");
                    ((TextView) findViewById(R.id.btn_stop)).setTextColor(0xffff00ff);
                    onPause();
                }
                ((TextView) findViewById(R.id.btn_walk)).setTextColor(Color.rgb(0, 0, 0));
                ((TextView) findViewById(R.id.btn_fall)).setTextColor(Color.rgb(0, 0, 0));
                ((TextView) findViewById(R.id.btn_stable)).setTextColor(Color.rgb(0, 0, 0));
                break;
            }
            case R.id.btn_walk: {
                ((TextView) findViewById(R.id.btn_walk)).setTextColor(0xffff00ff);
                ((TextView) findViewById(R.id.btn_fall)).setTextColor(Color.rgb(0, 0, 0));
                ((TextView) findViewById(R.id.btn_stable)).setTextColor(Color.rgb(0, 0, 0));
                ((TextView) findViewById(R.id.btn_stop)).setTextColor(Color.rgb(0, 0, 0));
                SensorData.reSetState();
                SensorData.walkState = 1;
                break;
            }
            case R.id.btn_fall: {
                ((TextView) findViewById(R.id.btn_walk)).setTextColor(Color.rgb(0, 0, 0));
                ((TextView) findViewById(R.id.btn_fall)).setTextColor(0xffff00ff);
                ((TextView) findViewById(R.id.btn_stable)).setTextColor(Color.rgb(0, 0, 0));
                ((TextView) findViewById(R.id.btn_stop)).setTextColor(Color.rgb(0, 0, 0));
                SensorData.reSetState();
                SensorData.fallState = 1;
                break;
            }
            case R.id.btn_stable: {
                ((TextView) findViewById(R.id.btn_walk)).setTextColor(Color.rgb(0, 0, 0));
                ((TextView) findViewById(R.id.btn_fall)).setTextColor(Color.rgb(0, 0, 0));
                ((TextView) findViewById(R.id.btn_stable)).setTextColor(0xffff00ff);
                ((TextView) findViewById(R.id.btn_stop)).setTextColor(Color.rgb(0, 0, 0));
                SensorData.reSetState();
                SensorData.stableState = 1;
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(accelerateSensorListener, accelerateSensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(gyroSensorListener, gyroSensor, SensorManager.SENSOR_DELAY_FASTEST);
        if (isInit && isPause) {
            isInit = false;
            return;
        }
        isInit = false;
        isPause = false;
        new DebugSensorDataThread().start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(accelerateSensorListener);
        sensorManager.unregisterListener(gyroSensorListener);
        isPause = true;
    }
}
