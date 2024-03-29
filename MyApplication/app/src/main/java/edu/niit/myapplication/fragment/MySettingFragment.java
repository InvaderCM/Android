package edu.niit.myapplication.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import edu.niit.myapplication.R;
import edu.niit.myapplication.activity.LoginActivity;
import edu.niit.myapplication.activity.SettingActivity;
import edu.niit.myapplication.activity.UserActivity;
import edu.niit.myapplication.entity.User;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MySettingFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Context mContext;
    private TextView tvUsername;
    private LinearLayout headLayout;
    private TextView historyLayout,settingLayout;
    private boolean isLogin;
    public MySettingFragment() {
        // Required empty public constructor
    }

    private boolean checkLoginStatus(){
        SharedPreferences sp = mContext.getSharedPreferences("loginInfo" , Context.MODE_PRIVATE);
        return sp.getBoolean("isLogin" , false);
    }
    public static MySettingFragment newInstance() {
        MySettingFragment fragment = new MySettingFragment();
        //从Activity传递给Fragment传参的方法
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fragment接收参数的方法
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    //初始化Fragment的xml界面上的所有控件和数据，相当于Activity的onCreate()方法的作用
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1.获取fragment的父Activity，以及目前的登录状态
        this.mContext = getContext();
        this.isLogin = checkLoginStatus();

        //2.获取fragment界面上需要处理的控件对象
        //Infalte the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_setting, container, false);
        tvUsername = view.findViewById(R.id.login_clickLogin);
        setUsername(isLogin);
        headLayout = view.findViewById(R.id.ll_head);
        historyLayout = view.findViewById(R.id.live);
        settingLayout =view.findViewById(R.id.setting);
        //3.设置事件监听器
        headLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (isLogin){
                    Intent intent = new Intent(mContext , UserActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent =new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent,1);
                }
            }
        });
        settingLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (isLogin){
                    Intent intent = new Intent(mContext , SettingActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent =new Intent(mContext,LoginActivity.class);
                    startActivityForResult(intent,1);
                }
            }
        });
        historyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
    private void setUsername(boolean isLogin){
        if (isLogin){
            tvUsername.setText(readLoginInfo());
        }else {
            tvUsername.setText("点击登录");
        }
    }
    private String readLoginInfo(){
        SharedPreferences sp = mContext.getSharedPreferences("loginInfo" , Context.MODE_PRIVATE);
        return sp.getString("loginUser" , "");
    }
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==1&&resultCode== Activity.RESULT_OK&&data!=null){
            isLogin=data.getBooleanExtra("isLogin",false);
            setUsername(isLogin);
        }
    }
}
