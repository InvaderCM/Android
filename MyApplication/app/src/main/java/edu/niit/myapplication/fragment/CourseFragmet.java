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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import edu.niit.myapplication.R;
import edu.niit.myapplication.adapter.AdViewPagerAdapter;
import edu.niit.myapplication.entity.AdImage;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseFragmet extends Fragment implements ViewPager.OnPageChangeListener, ViewPager.OnAdapterChangeListener {
    //广告轮播图相关
    public static final int MSG_AG_ID=1;    //广告自动欢动的消息ID
    private ViewPager viewPager;
    private TextView tvDesc;                //图片的描述
    private LinearLayout llPoint;
    private List<AdImage> adImages;
    private List<ImageView> imageViews;
    private int lastPos;
    private static CourseFragmet fragmet;
    public static CourseFragmet newInstance(){
        if (fragmet==null){
            fragmet=new CourseFragmet();
        }
        return fragmet;
    }

    private void initAdData(){
        adImages=new ArrayList<>();
        for (int i = 0 ; i<3;i++){
            AdImage adImage =new AdImage();
            adImage.setId(i+1);
            switch (i){
                case 0:
                    adImage.setImg("banner_1");
                    adImage.setDesc("新一代Apple watch发布");
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
        viewPager=view.findViewById(R.id.vp_banner);
        viewPager.addOnAdapterChangeListener(this);
        imageViews=new ArrayList<>();
        for (int i=0;i<adImages.size();i++){
            AdImage adImage =adImages.get(i);
            ImageView iv=new ImageView(getContext());
            if ("banner_1".equals(adImage.getImg())){
                iv.setImageResource(R.drawable.banner_1);
            }else if ("banner_2".equals(adImage.getImg())){
                iv.setImageResource(R.drawable.banner_2);
            }else if ("banner_3".equals(adImage.getImg())){
                iv.setImageResource(R.drawable.banner_3);
            }
            imageViews.add(iv);
        }

    }

    private void initIndiactor(View view){
        llPoint = view.findViewById(R.id.ll_point);
        View pointView;
        for (int i=0;i<adImages.size();i++){
            pointView=new View(getContext());
            pointView.setBackgroundResource(R.drawable.indicator_bg);
            pointView.setEnabled(false);

            LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(16,16);
            if (i!=0){
                params.leftMargin=10;
            }
            llPoint.addView(pointView,params);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_course,container,false);
        initAdData();
        initAdView(view);
        initIndiactor(view);

        lastPos =0;
        llPoint.getChildAt(0).setEnabled(true);
        tvDesc.setText(adImages.get(0).getDesc());
        viewPager.setAdapter(new AdViewPagerAdapter(imageViews));
//        adHandler = new AdHandler(viewPager);
//        new AdSlideThread().start();
        return view;
    }

    public CourseFragmet() {
        // Required empty public constructor
    }





    @Override
    public void onPageSelected(int position) {
        int currentPos = position % adImages.size();
        tvDesc.setText(adImages.get(currentPos).getDesc());

        llPoint.getChildAt(lastPos).setEnabled(false);
        llPoint.getChildAt(currentPos).setEnabled(true);
        lastPos=currentPos;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter) {

    }

    private AdHandler adHandler;
    private static class AdHandler extends Handler{
        private WeakReference<ViewPager> reference;

        public AdHandler(ViewPager viewPager){
            reference=new WeakReference<>(viewPager);
        }
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);

            ViewPager viewPager =reference.get();
            if (viewPager == null){
                return;
            }
            if (msg.what==MSG_AG_ID){
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                sendEmptyMessageDelayed(MSG_AG_ID,5000);
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
                if (adHandler !=null){
                    adHandler.sendEmptyMessage(MSG_AG_ID);
                }

            }
        }
    }
}
