package edu.niit.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class regist extends AppCompatActivity {
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        buttonRegister = findViewById(R.id.register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 给bnt1添加点击响应事件
                Intent intent = new Intent(regist.this, MainActivity.class);
                //启动
                startActivity(intent);
            }
        });
    }
}
