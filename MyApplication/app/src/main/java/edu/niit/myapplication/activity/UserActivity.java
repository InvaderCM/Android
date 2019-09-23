package edu.niit.myapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import edu.niit.myapplication.R;
import edu.niit.myapplication.entity.User;
import edu.niit.myapplication.service.impl.UserService;
import edu.niit.myapplication.service.impl.UserServiceImpl;
import edu.niit.myapplication.utils.SharedUtils;
/*
* 完成显示个人信息的功能
* 完成修改昵称、修改性别功能
*/

public class UserActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    //1.定义界面上的控件对象
    private TextView tvNickname, tvSignature, tvUsername, tvSex;
    private LinearLayout nicknameLayout, signatureLayout, sexLayout;

    //2.定义所需的变量
    private String spUsername;
    private User user;
    private UserService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initToolbar();
        //4.从数据库、网络、intent或存储中获取数据，初始化界面控件
        initData();
        //5.获取所有控件对象，设置监听器（必须）
        initView();
    }

    private void initView() {
        //1.获取控件对象
        tvUsername = findViewById(R.id.yonghuming);
        tvNickname = findViewById(R.id.nicheng);
        tvSex = findViewById(R.id.sex);
        tvSignature = findViewById(R.id.qianming);

        nicknameLayout = findViewById(R.id.nicknamelayout);
        sexLayout = findViewById(R.id.sexlayout);
        signatureLayout = findViewById(R.id.qianminglayout);

        //2.设置数据库获取的数据
        tvUsername.setText(user.getUsername());
        tvNickname.setText(user.getNickname());
        tvSex.setText(user.getSex());
        tvSignature.setText(user.getSignature());

        //3.设置监听器
        nicknameLayout.setOnClickListener(this);
        sexLayout.setOnClickListener(this);
        signatureLayout.setOnClickListener(this);
    }

    private void initData() {
        spUsername = SharedUtils.readValue(this,"loginUser");
        service = new UserServiceImpl(this);
        user = service.get(spUsername);
        if (user == null){
            user = new User();
            user.setUsername(spUsername);
            user.setNickname("课程助手");
            user.setSignature("课程助手");
            user.setSex("女");
            service.save(user);
        }
    }

    //3.设置标题栏
    private void initToolbar(){
        toolbar = findViewById(R.id.title_toolbar);
        toolbar.setTitle("个人信息");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//设置返回键
//            actionBar.setHomeButtonEnabled(true);//设置是否是首页
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               UserActivity.this.finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nicknamelayout:
                //将用户名、昵称等信息传给ChangeUserInfoActivity进行修改保存并返回
                modifyNickname();
                break;
            case R.id.qianminglayout:
                //将用户名、昵称等信息传给ChangeUserInfoActivity进行修改保存并返回
                modifySignature();
                break;
            case  R.id.sexlayout:
                //通过对话框修改
                modifySex();
                break;
        }
    }

    private void modifyNickname() {
        //1.获取已有的内容
        String nickname = tvNickname.getText().toString();
        //2.根据需要传递数据到下一个Activity
        Intent intent = new Intent(UserActivity.this,ChangeUserActivity.class);
        //Bundle对象用于传递有明确类型的见答案类型和复杂数据类型的数据（简单类型数据也可以用Intent传递）
        //Bundle需要加载到Intent中才能传递
        Bundle bundle = new Bundle();
        bundle.putString("title","设置昵称");//标题栏的标题
        bundle.putString("value",nickname);//内容
        bundle.putInt("flag",1);//用于区分修改昵称还是标题
        intent.putExtras(bundle);
        //3.启动下一个界面
        startActivityForResult(intent , 1);
    }

    private void modifySignature() {
        String signature = tvSignature.getText().toString();
        Intent intent = new Intent(UserActivity.this,ChangeUserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title","设置签名");//标题栏的标题
        bundle.putString("value",signature);//内容
        bundle.putInt("flag",2);//用于区分修改昵称还是标题
        intent.putExtras(bundle);
        startActivityForResult(intent , 2);
    }
    private void modifySex() {
        final String[] datas = {"男" , "女"};
        String sex = tvSex.getText().toString();

        //获取性别所在的索引
        final List<String> sexs = Arrays.asList(datas);
        int selected = sexs.indexOf(sex);

        new AlertDialog.Builder(this)
                .setTitle("性别")
                .setSingleChoiceItems(datas, selected,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String sex = datas[which];
                                tvSex.setText(sex);
                                user.setSex(sex);
                                service.modify(user);
                                dialog.dismiss();
                            }
                        }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //1.对空数据、返回异常做判断
        if (data==null || resultCode != RESULT_OK){
            Toast.makeText(this,"未知错误",Toast.LENGTH_SHORT).show();
            return;
        }
        //2.根据requestCode进行对应的保存
        //2.1获取data数据
        if (requestCode == 1){
            //2.2设置user对应的属性值，更新界面对应的控件内容
            String value=data.getStringExtra("nickname");
            tvNickname.setText(value);
            user.setNickname(value);
        } else if (requestCode == 2){
            String value=data.getStringExtra("signature");
            tvSignature.setText(value);
            user.setSignature(value);
        }
        //2.3保存到数据库
        service.modify(user);
    }
}
