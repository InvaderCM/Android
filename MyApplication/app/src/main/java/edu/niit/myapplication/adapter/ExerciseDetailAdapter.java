package edu.niit.myapplication.adapter;

import android.text.Layout;
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
import edu.niit.myapplication.entity.ExerciseDetail;

public class ExerciseDetailAdapter extends RecyclerView.Adapter<ExerciseDetailAdapter.ViewHolder> {
    private List<ExerciseDetail> details;
    private List<String> selectedPods;
    private OnSelectListener onSelectListener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView subject,tvA,tvB,tvC,tvD;
        ImageView ivA,ivB,ivC,ivD;
        public ViewHolder(View itemView){
            super(itemView);
            subject=itemView.findViewById(R.id.tv_subject);
            tvA=itemView.findViewById(R.id.tv_a);
            tvB=itemView.findViewById(R.id.tv_b);
            tvC=itemView.findViewById(R.id.tv_c);
            tvD=itemView.findViewById(R.id.tv_d);
            ivA=itemView.findViewById(R.id.iv_a);
            ivB=itemView.findViewById(R.id.iv_b);
            ivC=itemView.findViewById(R.id.iv_c);
            ivD=itemView.findViewById(R.id.iv_d);
        }
    }
    public interface OnSelectListener{
        void onSelectA(int position,ImageView ivA,ImageView ivB,ImageView ivC,ImageView ivD);
        void onSelectB(int position,ImageView ivA,ImageView ivB,ImageView ivC,ImageView ivD);
        void onSelectC(int position,ImageView ivA,ImageView ivB,ImageView ivC,ImageView ivD);
        void onSelectD(int position,ImageView ivA,ImageView ivB,ImageView ivC,ImageView ivD);
    }
    public ExerciseDetailAdapter(List<ExerciseDetail> details,OnSelectListener onSelectListener){
        this.details=details;
        selectedPods=new ArrayList<>();
        this.onSelectListener=onSelectListener;
    }
    @NonNull
    @Override
    public ExerciseDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exercises_detail,parent,false);
        ViewHolder holder =new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ExerciseDetailAdapter.ViewHolder holder, final int position) {
        //1.获取数据
        ExerciseDetail detail = details.get(position);
        //2.给控件赋值
        holder.subject.setText(detail.getSubject());
        holder.tvA.setText(detail.getA());
        holder.tvB.setText(detail.getB());
        holder.tvC.setText(detail.getC());
        holder.tvD.setText(detail.getD());

        holder.ivA.setImageResource(R.drawable.ic_exercise_a);
        holder.ivB.setImageResource(R.drawable.ic_exercise_b);
        holder.ivC.setImageResource(R.drawable.ic_exercise_c);
        holder.ivD.setImageResource(R.drawable.ic_exercise_d);

        //3.设置监听，并处理事件（根据选项判断答案是否正确，显示相应的图标）
        holder.ivA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pos=String.valueOf(position);
                if (selectedPods.contains(pos)){
                    selectedPods.remove(pos);
                } else {
                    selectedPods.add(pos);
                }
                onSelectListener.onSelectA(position,holder.ivA,holder.ivB,holder.ivC,holder.ivD);
            }
        });
        holder.ivB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String pos=String.valueOf(position);
                if (selectedPods.contains(pos)){
                    selectedPods.remove(pos);
                } else {
                    selectedPods.add(pos);
                }
                onSelectListener.onSelectB(position,holder.ivA,holder.ivB,holder.ivC,holder.ivD);
            }
        });
        holder.ivC.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String pos=String.valueOf(position);
                if (selectedPods.contains(pos)){
                    selectedPods.remove(pos);
                } else {
                    selectedPods.add(pos);
                }
                onSelectListener.onSelectC(position,holder.ivA,holder.ivB,holder.ivC,holder.ivD);
            }
        });
        holder.ivD.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String pos=String.valueOf(position);
                if (selectedPods.contains(pos)){
                    selectedPods.remove(pos);
                } else {
                    selectedPods.add(pos);
                }
                onSelectListener.onSelectD(position,holder.ivA,holder.ivB,holder.ivC,holder.ivD);
            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}
