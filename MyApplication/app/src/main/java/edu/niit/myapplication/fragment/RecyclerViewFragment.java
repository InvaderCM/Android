package edu.niit.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.niit.myapplication.R;
import edu.niit.myapplication.activity.ExerciseDetailActivity;
import edu.niit.myapplication.adapter.Exercise;
import edu.niit.myapplication.adapter.RecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecyclerViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecyclerViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecyclerViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<Exercise> exercises;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecyclerViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecyclerViewFragment newInstance(String param1, String param2) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1. 初始化数据
        initData();
        // 2. 获取控件
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        // 3. 设置布局和分割线
        LinearLayoutManager manager = new LinearLayoutManager(container.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(container.getContext(),
                DividerItemDecoration.VERTICAL));
        // 4. 创建适配器
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(exercises);
        // 5. 设置适配器
        recyclerView.setAdapter(adapter);

        // 6. 设置监听器
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Exercise exercise = exercises.get(position);
                //跳转到相应的章节习题
                Intent intent = new Intent(getContext(), ExerciseDetailActivity.class);
                intent.putExtra("id", exercise.getId());//用于识别是哪个xml文件
                intent.putExtra("title", exercise.getTitle());//用于设置详情的标题栏
                getContext().startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        return view;
    }

    private void initData() {
        exercises=new ArrayList<>();
        try{
            //1.从assets目录中获取资源的输入流
            InputStream input = getResources().getAssets().open("exercise_title.json");

        }catch (Exception e){
            e.printStackTrace();
        }

        for(int i=0;i<15;i++){
            Exercise exercise=new Exercise();
            exercise.setId(i+1);
            switch (i){
                case 0:
                    exercise.setTitle("第一章 安卓基础入门");
                    exercise.setSubTitle("共计5题");
                    exercise.setBackground(R.drawable.circle);
                    break;
                case 1:
                    exercise.setTitle("第二章 安卓基础入门");
                    exercise.setSubTitle("共计5题");
                    exercise.setBackground(R.drawable.circle);
                    break;
                case 2:
                    exercise.setTitle("第三章 安卓基础入门");
                    exercise.setSubTitle("共计5题");
                    exercise.setBackground(R.drawable.circle);
                    break;
                default:
                    exercise.setTitle("第四章 安卓基础入门");
                    exercise.setSubTitle("共计5题");
                    exercise.setBackground(R.drawable.circle);
                    break;
            }
            exercises.add(exercise);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
