package com.example.blingfly.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.blingfly.R;
import com.example.blingfly.databinding.FragmentCtrlBinding;
import com.example.blingfly.viewmodel.AirplaneViewModel;
import com.example.blingfly.view.RemoteView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CtrlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CtrlFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AirplaneViewModel mViewModel;
    FragmentCtrlBinding mBinding;

    public CtrlFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CtrlFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CtrlFragment newInstance(String param1, String param2) {
        CtrlFragment fragment = new CtrlFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentCtrlBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mViewModel = new ViewModelProvider(requireActivity()).get(AirplaneViewModel.class);
        mBinding.setAirplaneData(mViewModel);
        mBinding.setLifecycleOwner(this);

        //设置监听器
        mBinding.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_ctrlFragment_to_moreFragment);
            }
        });

        mBinding.btnRtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_ctrlFragment_to_menuFragment);
            }
        });

        mViewModel.getTakePhoto().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean == true)
                    mBinding.ivPhoto.setBackground(getResources().getDrawable(R.drawable.take_photo));
                else
                    mBinding.ivPhoto.setBackground(getResources().getDrawable(R.drawable.take_video));
            }
        });

        mBinding.circleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if (mBinding.circleView.getProgressSweepAngle() >= 360)
                        mViewModel.setSignalSent(true);
                }
                return false;
            }
        });
        mViewModel.getTakeOff().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean == true)
                    mBinding.circleView.setBackground(getResources().getDrawable(R.drawable.down));
                else
                    mBinding.circleView.setBackground(getResources().getDrawable(R.drawable.up));
            }
        });


        //自定义view
        RemoteView remoteView1 = new RemoteView(getContext());                                //实例化自定义的View
        RemoteView remoteView2 = new RemoteView(getContext());
        if (mViewModel.getGreenMode().getValue() == false)
            remoteView1.setMode("thr");
        else
            remoteView1.setMode("dir");
        remoteView1.invalidate();
        mBinding.ctrlL.addView(remoteView1);
        remoteView2.setMode("dir");
        remoteView2.invalidate();
        mBinding.ctrlR.addView(remoteView2);

        remoteView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                remoteView1.limitController();
                mViewModel.setUpwardPWM(remoteView1.getYPWM());
                mViewModel.setRotatePWM(remoteView1.getXPWM());
                return false;
            }
        });

        remoteView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mViewModel.setForwardPWM(remoteView1.getYPWM());
                mViewModel.setRotatePWM(remoteView1.getXPWM());
                return false;
            }
        });
    }
}