package edu.niit.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class login extends AppCompatActivity {

    private Button buttonRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonRegist = findViewById(R.id.register_login);
        buttonRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 给bnt1添加点击响应事件
                Intent intent = new Intent(login.this, regist.class);
                //启动
                startActivity(intent);
            }
        });
    }
}
