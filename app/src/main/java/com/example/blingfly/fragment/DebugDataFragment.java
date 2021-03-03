package com.example.blingfly.fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blingfly.R;
import com.example.blingfly.databinding.DebugDataFragmentBinding;
import com.example.blingfly.databinding.MoreAboutFragmentBinding;
import com.example.blingfly.viewmodel.AirplaneViewModel;
import com.example.blingfly.viewmodel.DebugDataViewModel;

public class DebugDataFragment extends Fragment {

    DebugDataViewModel dataViewModel;
    DebugDataFragmentBinding mBinding;

    public static DebugDataFragment newInstance() {
        return new DebugDataFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DebugDataFragmentBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dataViewModel = new ViewModelProvider(requireActivity()).get(DebugDataViewModel.class);
        mBinding.setDebugData(dataViewModel);
        mBinding.setLifecycleOwner(this);

        mBinding.btnRtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_debugDataFragment_to_debugFragment);
            }
        });
    }

}