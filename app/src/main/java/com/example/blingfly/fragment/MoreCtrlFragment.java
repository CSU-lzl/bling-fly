package com.example.blingfly.fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.blingfly.databinding.MoreCtrlFragmentBinding;
import com.example.blingfly.viewmodel.AirplaneViewModel;
import com.google.android.material.tabs.TabLayout;

public class MoreCtrlFragment extends Fragment {

    AirplaneViewModel mViewModel;
    MoreCtrlFragmentBinding mBinding;

    public static MoreCtrlFragment newInstance() {
        return new MoreCtrlFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = MoreCtrlFragmentBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
        mViewModel = new ViewModelProvider(requireActivity()).get(AirplaneViewModel.class);
        mBinding.setAirplaneData(mViewModel);
        mBinding.setLifecycleOwner(this);

        mBinding.tlSpeed.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewModel.getSpeedState();
                if(tab.getText().equals("平稳挡")){
                    mViewModel.setSpeedState("平稳挡");
                }
                else if (tab.getText().equals("普通挡")){
                    mViewModel.setSpeedState("普通挡");
                }
                else if (tab.getText().equals("运动挡")){
                    mViewModel.setSpeedState("运动挡");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        mBinding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mViewModel.setGreenMode(b);
            }
        });
    }

}