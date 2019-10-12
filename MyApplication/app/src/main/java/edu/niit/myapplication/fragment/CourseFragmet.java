package edu.niit.myapplication.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import edu.niit.myapplication.R;
import edu.niit.myapplication.adapter.AdViewPagerAdapter;
import edu.niit.myapplication.adapter.CoursesRecyclerAdapter;
import edu.niit.myapplication.adapter.RecyclerViewAdapter;
import edu.niit.myapplication.entity.AdImage;
import edu.niit.myapplication.entity.Course;
import edu.niit.myapplication.utils.HttpsUtil;
import edu.niit.myapplication.utils.IOUtils;
import edu.niit.myapplication.utils.NetworkUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseFragmet extends Fragment implements  ViewPager.OnPageChangeListener {
    public static final int MSG_AD_ID =1;

    private ViewPager viewPager;
    private TextView tvDesc; //图片的描述
    private LinearLayout llPoint; //指示器的布局

    private List<AdImage> adImages; //数据
    private List<ImageView> imageViews;//图片的集合
    private int lastPos;//之前的位置
    private AdHandler adhandler;


    private RecyclerView rvCourse;
    private List<Course> courses;


    private static class AdHandler extends Handler {
        private WeakReference<ViewPager> reference;




        public AdHandler(ViewPager viewPager) {
            reference = new WeakReference<>(viewPager);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ViewPager viewPager = reference.get();
            if (viewPager == null) {
                return;
            }
            if (msg.what == MSG_AD_ID) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                sendEmptyMessageDelayed(MSG_AD_ID, 5000);
            }
        }
    }

    public CourseFragmet(){

    }
    public static  CourseFragmet fragment;
    public  static  CourseFragmet newInstance(){
        if (fragment==null){
            fragment=new CourseFragmet();
        }
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course,container,false);

        initAdData();
        initAdView(view);
        initIndicator(view);

        lastPos=0;
        llPoint.getChildAt(0).setEnabled(true);
        tvDesc.setText(adImages.get(0).getDesc());
        viewPager.setAdapter(new AdViewPagerAdapter(imageViews));
        adhandler=new AdHandler(viewPager);
        adhandler.sendEmptyMessageDelayed(MSG_AD_ID,5000);

        rvCourse=view.findViewById(R.id.rv_courses);
//        initCourses();
//        initCourseView(view);
//        update(courses);
//        loadCourseByNet();
        loadCourseByOkHttp();
        return view;

    }

    private void initCourses() {
        courses =new ArrayList<>();
        try{
            InputStream is=getResources().getAssets().open("chapter_intro.json");
            String json= IOUtils.convert(is, StandardCharsets.UTF_8);
            courses = JSON.parseArray(json, Course.class);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void initCourseView(View view) {
        rvCourse=view.findViewById(R.id.rv_courses);
        initCourses();
        update(courses);
    }
    private void update(final List<Course> courses){
        CoursesRecyclerAdapter adapter=new CoursesRecyclerAdapter(courses);
        rvCourse.setLayoutManager(new GridLayoutManager(getContext(),2));
        rvCourse.setAdapter(adapter);
        adapter.setOnItemClickListener(new CoursesRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Course course=courses.get(position);
                Toast.makeText(getContext(),"点击了："+course.getTitle(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initAdData() {
        adImages =new ArrayList<>();
        for(int i=0;i<3;i++){
            AdImage adImage=new AdImage();
            adImage.setId(i+1);
            switch (i){
                case 0:
                    adImage.setImg("banner_1");
                    adImage.setDesc("新一代Apple Watch发布");
                    break;
                case 1:
                    adImage.setImg("banner_2");
                    adImage.setDesc("寒武纪发布AI芯片");
                    break;
                case 2:
                    adImage.setImg("banner_3");
                    adImage.setDesc("Google发布AI语音助手");
                    break;
                default:
                    break;
            }
            adImages.add(adImage);
        }
    }
    private void initAdView(View view){
        tvDesc=view.findViewById(R.id.tv_desc);
        viewPager =view.findViewById(R.id.vp_banner);
        viewPager.addOnPageChangeListener(this);

        imageViews=new ArrayList<>();
        for(int i=0;i<adImages.size();i++){
            AdImage adImage=adImages.get(i);

            ImageView iv=new ImageView(getContext());
            if("banner_1".equals(adImage.getImg())){
                iv.setBackgroundResource(R.drawable.banner_1);
            }else if("banner_2".equals(adImage.getImg())){
                iv.setBackgroundResource(R.drawable.banner_2);
            }else if("banner_3".equals(adImage.getImg())){
                iv.setBackgroundResource(R.drawable.banner_3);
            }
            imageViews.add(iv);
        }
    }
    private void initIndicator(View view){
        llPoint=view.findViewById(R.id.ll_point);
        View pointView;
        for(int i=0;i<adImages.size();i++){
            pointView=new View(getContext());
            pointView.setBackgroundResource(R.drawable.indicator_bg);
            pointView.setEnabled(false);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(16,16);
            if(i!=0){
                params.leftMargin=10;
            }
            llPoint.addView(pointView,params);
        }



    }
    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }


    @Override
    public void onPageSelected(int i) {
        int currentPos=i %adImages.size();
        tvDesc.setText(adImages.get(currentPos).getDesc());

        llPoint.getChildAt(lastPos).setEnabled(false);
        llPoint.getChildAt(currentPos).setEnabled(true);
        lastPos=currentPos;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private void loadCourseByNet(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json= NetworkUtils.get("https://www.fastmock.site/mock/b46332ceba020b46458f016deac2c275/course/chapter");
                List<Course> courses =JSON.parseArray(json,Course.class);
                if (courses !=null){
                    Message msg =new Message();
                    msg.what = MSG_AD_ID;
                    msg.obj=courses;
                    courseHandler.sendMessage(msg);
                }
            }
        }).start();
    }

    private Handler courseHandler =new CourseHandler(this);
    private static class CourseHandler extends Handler{
        private WeakReference<CourseFragmet> ref;

        public CourseHandler(CourseFragmet fragmet){
            this.ref=new WeakReference<>(fragmet);
        }

        public void handleMessage(Message msg){
            CourseFragmet target =ref.get();

            if (msg.what == MSG_AD_ID){
                target.update((List<Course>) msg.obj);
            }
        }
    }
    /*
     * 使用多线程实现广告自动切换
     * */
    private class AdSlideThread extends Thread{
        @Override
        public void run(){
            super.run();
            while (true){
                try {
                    sleep(5000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                if (adhandler !=null){
                    adhandler.sendEmptyMessage(MSG_AD_ID);
                }

            }
        }
    }

    private void loadCourseByOkHttp(){
        Request request =new Request.Builder()
                .url("https://www.fastmock.site/mock/b46332ceba020b46458f016deac2c275/course/chapter")
                .addHeader("Accept","application/json")
                .method("GET",null)
                .build();
        HttpsUtil.handleSSLHandshakeByOkHttp().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    if (response.body() !=null){
                        String json=response.body().string();
                        final List<Course> courses=JSON.parseArray(json,Course.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                update(courses);
                            }
                        });
                    }
                }

            }
        });
    }

}

