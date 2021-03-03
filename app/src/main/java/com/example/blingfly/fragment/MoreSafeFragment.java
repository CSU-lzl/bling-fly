package com.example.blingfly.fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.blingfly.databinding.MoreSafeFragmentBinding;
import com.example.blingfly.viewmodel.AirplaneViewModel;

public class MoreSafeFragment extends Fragment {

    AirplaneViewModel mViewModel;
    MoreSafeFragmentBinding mBinding;

    public static MoreSafeFragment newInstance() {
        return new MoreSafeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = MoreSafeFragmentBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
        mViewModel = new ViewModelProvider(requireActivity()).get(AirplaneViewModel.class);
        mBinding.setAirplaneData(mViewModel);
        mBinding.setLifecycleOwner(this);

        mBinding.sbHeight.setProgress(mViewModel.getLimitHeight().getValue());
        mBinding.sbHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mViewModel.setLimitHeight(i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mBinding.sbDistance.setProgress(mViewModel.getLimitDistance().getValue());
        mBinding.sbDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mViewModel.setLimitDistance(i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mBinding.sbRtHeight.setProgress(mViewModel.getLimitReturnHeight().getValue());
        mBinding.sbRtHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mViewModel.setLimitReturnHeight(i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mViewModel.getCalibrateIMU().observe(this, new Observer<Boolean>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean == false) {
                    mBinding.tvImu.setTextColor(Color.RED);
                    mBinding.tvImu.setText("未校准");
                }
                else {
                    mBinding.tvImu.setTextColor(Color.BLUE);
                    mBinding.tvImu.setText("校准");
                }
            }
        });

        mViewModel.getCalibrateESC().observe(this, new Observer<Boolean>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean == false) {
                    mBinding.tvEsc.setTextColor(Color.RED);
                    mBinding.tvEsc.setText("未校准");
                }
                else {
                    mBinding.tvEsc.setTextColor(Color.BLUE);
                    mBinding.tvEsc.setText("校准");
                }
            }
        });
    }

}