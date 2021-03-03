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

import com.example.blingfly.databinding.MoreAboutFragmentBinding;
import com.example.blingfly.viewmodel.AirplaneViewModel;

public class MoreAboutFragment extends Fragment {

    AirplaneViewModel mViewModel;
    MoreAboutFragmentBinding mBinding;

    public static MoreAboutFragment newInstance() {
        return new MoreAboutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = MoreAboutFragmentBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
        mViewModel = new ViewModelProvider(requireActivity()).get(AirplaneViewModel.class);
        mBinding.setAirplaneData(mViewModel);
        mBinding.setLifecycleOwner(this);
    }

}