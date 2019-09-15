package edu.niit.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import edu.niit.myapplication.utils.SharedUtils;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView exitLayout;
    private TextView modifyLayout ,secretLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initToolbar();
        initView();
    }
    private void initToolbar(){
        toolbar = findViewById(R.id.title_toolbar);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//设置返回键
//            actionBar.setHomeButtonEnabled(true);//设置是否是首页
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingActivity.this.finish();
            }
        });
    }
   private void initView(){
        modifyLayout = findViewById(R.id.modifyPassword);
        secretLayout = findViewById(R.id.setSecret);
        exitLayout = findViewById(R.id.exitLogin);
        modifyLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this , ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
       secretLayout.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View view) {
               Intent intent = new Intent(SettingActivity.this , ProtectPasswordActivity.class);
               startActivity(intent);
           }
       });
       // 退出登录
       exitLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(SettingActivity.this, "退出登录", Toast.LENGTH_SHORT).show();

               new AlertDialog.Builder(SettingActivity.this)
                       .setTitle("退出")
                       .setMessage("确认退出登录？")
                       .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               SharedUtils.clearLoginInfo(SettingActivity.this);

                               // 返回我的界面
                               Intent intent = new Intent();
                               intent.putExtra("isLogin", false);
                               setResult(RESULT_OK, intent);
                               SettingActivity.this.finish();
                           }
                       })
                       .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                           }
                       })
                       .show();

           }
       });
   }

}
