package edu.niit.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import java.security.acl.Group;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RadioGroup group;
    //定义标题的集合
    private SparseArray<String> titles;
    private SparseArray<Fragment> fragments ;
    private void initFragment(){
        //1.创建fragment的列表
        fragments.put(R.id.btn_my , MySettingFragment.newInstance());
        //2.加载默认的Fragment
        replaceFragment(fragments.get(R.id.btn_my));
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.main_body , fragment);
        ft.commit();
    }
    private void initTitles(){
        titles = new SparseArray<>();
        titles.put(R.id.btn_course , "课程");
        titles.put(R.id.btn_execise , "练习");
        titles.put(R.id.btn_message , "资讯");
        titles.put(R.id.btn_my , "我的");
    }
    private void setToolbar(int checkedId) {
        if (checkedId == R.id.btn_my){
            toolbar.setVisibility(View.GONE);
        }else {
            toolbar.setVisibility(View.VISIBLE);
            toolbar.setTitle(titles.get(checkedId));
        }
    }
    private void initView(){
        group = findViewById(R.id.btn_group);
        toolbar = findViewById(R.id.title_toolbar);
        setToolbar(group.getCheckedRadioButtonId());

       // RadioGroup的选项改变事件的监听
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Toast.makeText(MainActivity.this , titles.get(checkedId) , Toast.LENGTH_SHORT).show();
                setToolbar(checkedId);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
