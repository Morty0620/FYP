package com.zqh.fyp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.zqh.fyp.Util.Thread.RegisterCheckThread;
import com.zqh.fyp.Util.Thread.RegisterThread;

public class Register_Activity extends Activity implements View.OnClickListener {


    private EditText edit_register, edit_setpassword, edit_resetpassword;
    private Button btn_yes, btn_cancel;
    public static int registerState;//-1网络超时 0验证中 1成功 2已注册

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        init();
    }


    protected void init() {
        edit_register = (EditText) findViewById(R.id.edit_register);
        edit_register.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end,
                                               Spanned dest, int dstart, int dend) {
                        for (int i = start; i < end; i++) {
                            if (!Character.isLetterOrDigit(source.charAt(i)) &&
                                    !Character.toString(source.charAt(i)).equals("_")) {
                                Toast.makeText(Register_Activity.this, "只能使用'_'、字母、数字、汉字注册！", Toast.LENGTH_SHORT).show();
                                return "";
                            }
                        }
                        return null;
                    }
                }
        });
        edit_register.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    edit_register.clearFocus();
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edit_register.getWindowToken(), 0);
                }
                return false;
            }
        });
        edit_setpassword = (EditText) findViewById(R.id.edit_setpassword);
        edit_setpassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String s = v.getText().toString();
                    //设置密码长度有问题，判断editText的输入长度需要重新理解
                    System.out.println(" v: ****** v :"+ s.length());
                    if (s.length() >= 6) {
                        System.out.println(" ****** s :"+ s.length());
                        edit_setpassword.clearFocus();
                        InputMethodManager imm =
                                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(edit_setpassword.getWindowToken(), 0);
                    } else {
                        Toast.makeText(Register_Activity.this, "密码设置最少为6位！", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
        edit_resetpassword = (EditText) findViewById(R.id.edit_resetpassword);
        edit_resetpassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    edit_resetpassword.clearFocus();
                    InputMethodManager im =
                            (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(edit_resetpassword.getWindowToken(), 0);
                }
                return false;
            }
        });
        btn_yes = (Button) findViewById(R.id.btn_yes);
        btn_yes.setOnClickListener(this);
        btn_cancel = (Button) findViewById(R.id.btn_cancle);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                new RegisterCheckThread(edit_register.getText().toString(),
                        edit_setpassword.getText().toString()).start();
                int timeout = 0;
                while (registerState==0 && timeout < 1000) {
                    timeout++;
                    try {
                        Thread.sleep(1);
                    } catch (Exception e) {

                    }
                }
                switch (registerState){
                    case -1:{
                        System.out.println("验证超时");
                        Toast.makeText(this, "验证超时！", Toast.LENGTH_SHORT).show();
                        registerState=0;
                        return;
                    }
                    case 1:{
                        if (edit_setpassword.getText().toString().trim().
                                equals(edit_resetpassword.getText().toString())) {
                            registerUserInfo(edit_register.getText().toString(),
                                    edit_setpassword.getText().toString());
                            Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
                            Intent register_intent = new Intent(Register_Activity.this,
                                    Login_Activity.class);
                            startActivity(register_intent);
                        } else {
                            Toast.makeText(this, "两次输入密码不同，请重新输入！",
                                    Toast.LENGTH_SHORT).show();
                        }
                        registerState=0;
                        break;
                    }
                    case 2:{
                        Toast.makeText(this, "该用户名已被注册，注册失败", Toast.LENGTH_SHORT).show();
                        registerState=0;
                        break;
                    }
                }
                break;
            case R.id.btn_cancle:
                Intent login_intent = new Intent(Register_Activity.this, Login_Activity.class);
                startActivity(login_intent);
                break;
            default:
                break;
        }
    }


    /**
     * 利用SharedPreferences进行默认登陆设置
     */
    private void saveUsersInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("UsersInfo", MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", edit_register.getText().toString());
        //判断注册时的两次密码是否相同
        if (edit_setpassword.getText().toString().equals(edit_resetpassword.getText().toString())) {
            editor.putString("password", edit_setpassword.getText().toString());
        }
        editor.commit();
    }

    /**
     * 利用sql创建嵌入式数据库进行注册访问
     */
    private void registerUserInfo(String username, String password) {
        new RegisterThread(username,password).start();
    }
}
