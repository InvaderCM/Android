package edu.niit.myapplication.activity;
import android.content.Context;
import android.content.Intent;;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import edu.niit.myapplication.R;
import edu.niit.myapplication.entity.User;


public class ChangeUserActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String title;
    private String value;
    private int flag;
    private EditText etContent;
    private ImageView ivDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user);
        initToolbar();
        initDate();
        initView();

    }
    private void initToolbar(){
        toolbar = findViewById(R.id.title_toolbar);
        toolbar.setTitle("设置昵称");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//设置返回键
//            actionBar.setHomeButtonEnabled(true);//设置是否是首页
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeUserActivity.this.finish();
            }
        });
    }

    //加载选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user,menu);
        return true;
    }
    //菜单项的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_save:
                save();
                break;
            case R.id.item_cancel:
                ChangeUserActivity.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void initDate(){
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            title=bundle.getString("title");
            value=bundle.getString("value");
            flag=bundle.getInt("flag");
        }
    }
    private void initView(){
        etContent=findViewById(R.id.et_content);
        etContent.setText(value);
        ivDelete=findViewById(R.id.iv_delete);
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setText("");
            }
        });
    }
    private void save() {
        Intent intent =new Intent();
        //1.获取输入的内容
        String value = etContent.getText().toString();
        switch (flag){
            case 1:
                if (TextUtils.isEmpty(value)){
                    Toast.makeText(ChangeUserActivity.this,"昵称不能为空",Toast.LENGTH_SHORT).show();
                } else {
                    //2.将输入的内容返回给UserActivity
                    intent.putExtra("nickname",value);
                    setResult(RESULT_OK,intent);
                    Toast.makeText(ChangeUserActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    ChangeUserActivity.this.finish();
                }
                break;
            case 2:
                if (TextUtils.isEmpty(value)){
                    Toast.makeText(ChangeUserActivity.this,"签名不能为空",Toast.LENGTH_SHORT).show();
                } else {
                    //2.将输入的内容返回给UserActivity
                    intent.putExtra("signature",value);
                    setResult(RESULT_OK,intent);
                    Toast.makeText(ChangeUserActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    ChangeUserActivity.this.finish();
                }
                break;
        }
    }

}
