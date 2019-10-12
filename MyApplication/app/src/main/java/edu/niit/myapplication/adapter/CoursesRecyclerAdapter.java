package edu.niit.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.niit.myapplication.R;
import edu.niit.myapplication.entity.Course;

public class CoursesRecyclerAdapter extends RecyclerView.Adapter<CoursesRecyclerAdapter.ViewHolder> {
    private List<Course> courses;
    private List<Integer> imgIds;

    private OnItemClickListener itemClickListener;

    public CoursesRecyclerAdapter(List<Course> courses) {
        this.courses = courses;
        setImgIds();
    }

    private void setImgIds() {
        imgIds = new ArrayList<>();
        imgIds.add(R.drawable.banner_1);
        imgIds.add(R.drawable.banner_2);
        imgIds.add(R.drawable.banner_3);
        imgIds.add(R.drawable.banner_1);
        imgIds.add(R.drawable.banner_2);
        imgIds.add(R.drawable.banner_3);
        imgIds.add(R.drawable.banner_1);
        imgIds.add(R.drawable.banner_2);
        imgIds.add(R.drawable.banner_3);
        imgIds.add(R.drawable.banner_1);
        imgIds.add(R.drawable.banner_2);
        imgIds.add(R.drawable.banner_3);
        imgIds.add(R.drawable.banner_1);
        imgIds.add(R.drawable.banner_2);
        imgIds.add(R.drawable.banner_3);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.demo_course, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Course course = courses.get(position);
        holder.ivImg.setImageResource(imgIds.get(position));
        holder.tvTitle.setText(course.getTitle());

        if(itemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImg;
        TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.iv_img);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
