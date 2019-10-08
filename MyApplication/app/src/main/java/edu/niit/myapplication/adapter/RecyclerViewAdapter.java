package edu.niit.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.niit.myapplication.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Exercise> exercises;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvtvOder,tvTitle,tvSubTitle;

        public ViewHolder(View view){
            super(view);
            tvtvOder= view.findViewById(R.id.tv_order);
            tvTitle= view.findViewById(R.id.tv_title);
            tvSubTitle= view.findViewById(R.id.tv_sub_title);
        }
    }
    public RecyclerViewAdapter(List<Exercise> exerList){
        exercises=exerList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exercise , parent ,false);
        ViewHolder holder = new ViewHolder(view);
        //设置Item点击的监听器
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Exercise exercise= exercises.get(position);
        holder.tvTitle.setText(exercise.getTitle());
        holder.tvtvOder.setText(String.valueOf(position+1));
        holder.tvSubTitle.setText(exercise.getSubTitle());
        //设置监听器
        if (itemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(holder.itemView,position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    itemClickListener.onItemLongClick(holder.itemView,position);
                    return true;
                }
            });
        }
    }
    //1.回调事件的接口
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }
    //2.在Adapter类中设置事件接口对象
    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
    @Override
    public int getItemCount() {
        return exercises.size();
    }


}
