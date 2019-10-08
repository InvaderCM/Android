package edu.niit.myapplication.activity;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import edu.niit.myapplication.R;
import edu.niit.myapplication.fragment.CourseFragmet;
import edu.niit.myapplication.fragment.MySettingFragment;
import edu.niit.myapplication.fragment.PractiseFragment;
import edu.niit.myapplication.fragment.RecyclerViewFragment;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RadioGroup group;
    //定义标题的集合
    private SparseArray<String> titles;
    private SparseArray<Fragment> fragment;

    private void initFragment(){
        fragment=new SparseArray<>();
        fragment.put(R.id.btn_my, MySettingFragment.newInstance());
        fragment.put(R.id.btn_execise, RecyclerViewFragment.newInstance("Activity向Fragment传值",""));
        fragment.put(R.id.btn_course,CourseFragmet.newInstance());
//        fragment.put(R.id.btn_execise, RecyclerViewFragment.newInstance("Activity向Fragment传值", "param2"));

        replaceFragment(fragment.get(R.id.btn_execise));
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction ft=manager.beginTransaction();
        ft.replace(R.id.main_body,fragment);
        ft.addToBackStack(null);
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
//        toolbar = findViewById(R.id.title_toolbar);
//        setToolbar(group.getCheckedRadioButtonId());

        // RadioGroup的选项改变事件的监听
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                Toast.makeText(MainActivity.this , titles.get(checkedId) , Toast.LENGTH_SHORT).show();
//                setToolbar(checkedId);
                replaceFragment(fragment.get(checkedId));
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        initView();
//        initTitles();

    }

}