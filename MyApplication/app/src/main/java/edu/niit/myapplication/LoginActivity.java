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

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //  1.获取界面上的控件
        initView();
        //  2.
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //  3.1
                String username=etUsername.getText().toString();
                String password=etPassword.getText().toString();
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String name = pref.getString("username", "");
                String pwd = pref.getString("password", "");

                //  3.2
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(LoginActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                } else if (!password.equals(pwd) || TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this,"密码错误，登陆失败",Toast.LENGTH_SHORT).show();
                } else{
                    Intent intent = new Intent(LoginActivity.this , LoginActivity.class);
                    intent.putExtra("username" , username);
                    startActivity(intent);
                }
            }
        });
    }

    private void initView(){
        etUsername = findViewById(R.id.name);
        etPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.login_button);
    }
}
