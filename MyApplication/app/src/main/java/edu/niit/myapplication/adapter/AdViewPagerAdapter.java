package edu.niit.myapplication.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class AdViewPagerAdapter extends PagerAdapter {
    private List<ImageView> imageViews;
    private AdViewPagerAdapter(){
        this(null);
        imageViews=new ArrayList<>();
    }
    public AdViewPagerAdapter(List<ImageView> imageViews){
        super();
        this.imageViews=imageViews;
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    //指定复用的判断逻辑，固定写法：view == object
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        //当创建新的条目，又反回来，判断view是否可以被复用（即是否存在）
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container,int position){
        //contanier 容器相当于用来存放imageView
        //从集合中获得图片
        ImageView imageView =imageViews.get(position %imageViews.size());
        //检查imageView是否已经添加到容器中
        ViewParent parent =imageView.getParent();
        if (parent != null){
            ((ViewGroup) parent).removeView(imageView);
        }
        //把图片添加到container中
        container.addView(imageView);
        //把图片返回给框架，用来缓存
        return imageView;
    }
    @Override
    public int getItemPosition(@NonNull Object object){
        return POSITION_NONE;
    }

    public int getSize(){
        return imageViews.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }
}
