package edu.niit.myapplication.activity;

import android.app.AppComponentFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.niit.myapplication.R;
import edu.niit.myapplication.adapter.ExerciseAdapter;
import edu.niit.myapplication.adapter.ExerciseDetailAdapter;
import edu.niit.myapplication.entity.ExerciseDetail;
import edu.niit.myapplication.utils.IOUtils;

public class ExerciseDetailActivity extends AppCompatActivity implements ExerciseDetailAdapter.OnSelectListener {
    //获取从ExerciseFragment中传来的数据
    private int id;
    private String title;
    //从xml文件中获得
    private List<ExerciseDetail> details;
    //控件及Adapter
    private RecyclerView lvDetails;
    private ExerciseDetailAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recycler_view);
        initData();
        initView();
    }
    public void initData(){
        id =getIntent().getIntExtra("id",0);
        title=getIntent().getStringExtra("title");

        details=new ArrayList<>();
        try{
            InputStream is=getResources().getAssets().open("chapter"+id+".xml");
            details= IOUtils.getXmlContents(is);
            is.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void initView(){
        lvDetails=findViewById(R.id.recycle_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        lvDetails.setLayoutManager(manager);
        adapter=new ExerciseDetailAdapter(details,this);
        lvDetails.setAdapter(adapter);
    }
    public void onSelectA(int position, ImageView ivA,ImageView ivB,ImageView ivC,ImageView ivD){
        ExerciseDetail detail = details.get(position);
        if (detail.getAnswer() !=1){
            detail.setSelect(1);
        } else {
            detail.setSelect(0);
        }
        switch (detail.getAnswer()){
            case 1:
                ivA.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 2:
                ivB.setImageResource(R.drawable.ic_exercise_answer_right);
                ivA.setImageResource(R.drawable.ic_exercise_answer_error);
                break;
            case 3:
                ivC.setImageResource(R.drawable.ic_exercise_answer_right);
                ivA.setImageResource(R.drawable.ic_exercise_answer_error);
                break;
            case 4:
                ivD.setImageResource(R.drawable.ic_exercise_answer_right);
                ivA.setImageResource(R.drawable.ic_exercise_answer_error);
                break;
        }

    }

    @Override
    public void onSelectB(int position, ImageView ivA, ImageView ivB, ImageView ivC, ImageView ivD) {
        ExerciseDetail detail = details.get(position);
        if (detail.getAnswer() !=2){
            detail.setSelect(2);
        } else {
            detail.setSelect(0);
        }
        switch (detail.getAnswer()){
            case 1:
                ivA.setImageResource(R.drawable.ic_exercise_answer_error);
                ivB.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 2:
                ivB.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 3:
                ivC.setImageResource(R.drawable.ic_exercise_answer_error);
                ivB.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 4:
                ivD.setImageResource(R.drawable.ic_exercise_answer_error);
                ivB.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
        }
    }

    @Override
    public void onSelectC(int position, ImageView ivA, ImageView ivB, ImageView ivC, ImageView ivD) {
        ExerciseDetail detail = details.get(position);
        if (detail.getAnswer() !=3){
            detail.setSelect(3);
        } else {
            detail.setSelect(0);
        }
        switch (detail.getAnswer()){
            case 1:
                ivA.setImageResource(R.drawable.ic_exercise_answer_error);
                ivC.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 2:
                ivB.setImageResource(R.drawable.ic_exercise_answer_error);
                ivC.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 3:
                ivC.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 4:
                ivD.setImageResource(R.drawable.ic_exercise_answer_error);
                ivC.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
        }
    }

    @Override
    public void onSelectD(int position, ImageView ivA, ImageView ivB, ImageView ivC, ImageView ivD) {
        ExerciseDetail detail = details.get(position);
        if (detail.getAnswer() !=4){
            detail.setSelect(4);
        } else {
            detail.setSelect(0);
        }
        switch (detail.getAnswer()){
            case 1:
                ivA.setImageResource(R.drawable.ic_exercise_answer_error);
                ivD.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 2:
                ivB.setImageResource(R.drawable.ic_exercise_answer_error);
                ivD.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 3:
                ivC.setImageResource(R.drawable.ic_exercise_answer_error);
                ivD.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 4:
                ivD.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
        }
    }
}
