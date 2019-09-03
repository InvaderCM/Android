package edu.niit.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import edu.niit.myapplication.utils.MD5Utils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    //  1.获取界面上的控件
    //  2.button的点击事件的监听
    //  3.处理点击事件
    //  3.1 获取控件的值
    //  3.2 检查数据的有效性
    //  3.3 将注册信息存储
    //  3.4 跳转到登录界面
    private EditText etUsername, etPassword,etPwdAgain;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //  1.获取界面上的控件
        initView();
        //  2.
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //  3.1
                String username=etUsername.getText().toString();
                String password=etPassword.getText().toString();
                String pwdAgain=etPwdAgain.getText().toString();
                //  3.2
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(RegisterActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password) || TextUtils.isEmpty(pwdAgain)){
                    Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                } else if (!password.equals(pwdAgain)){
                    Toast.makeText(RegisterActivity.this,"两次密码必须一致",Toast.LENGTH_SHORT).show();
                } else{
                    savePref(username, MD5Utils.md5(password));
                    Intent intent = new Intent(RegisterActivity.this , LoginActivity.class);
                    intent.putExtra("username" , username);
                    startActivity(intent);
                }
            }
        });
    }

    private void savePref(String username , String password) {
        SharedPreferences sp =getSharedPreferences("userInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        editor.putString("username",username);
        editor.putString("username",password);
        editor.apply();
    }

    private void initView(){
        etUsername = findViewById(R.id.name);
        etPassword = findViewById(R.id.password);
        etPwdAgain = findViewById(R.id.password2);
        btnRegister = findViewById(R.id.register);
    }



}
