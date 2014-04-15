package com.hufeng.droidtools;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hufeng.droidtool.R;

/**
 * Created by feng on 2014-04-14.
 */
public class ModelFragment extends BaseFragment {

    public static final String FRAGMENT_TAG = "ModelFragment";

    TextView mModelInfoView;

    public static ModelFragment newFragmentInstance() {
        ModelFragment fragment = new ModelFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_model, container, false);
        mModelInfoView = (TextView) rootView.findViewById(R.id.model_info);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StringBuilder info = new StringBuilder();
        info.append("BOARD: ").append(Build.BOARD).append('\n')
                .append("BOOTLOADER: ").append(Build.BOOTLOADER).append('\n')
                .append("BRAND: ").append(Build.BRAND).append('\n')
                .append("CPU_ABI: ").append(Build.CPU_ABI).append('\n')
                .append("CPU_ABI2: ").append(Build.CPU_ABI2).append('\n')
                .append("DEVICE: ").append(Build.DEVICE).append('\n')
                .append("DISPLAY: ").append(Build.DISPLAY).append('\n')
                .append("FINGERPRINT: ").append(Build.FINGERPRINT).append('\n')
                .append("RADIO_VERSION: ").append(Build.getRadioVersion()).append('\n')
                .append("HARDWARE: ").append(Build.HARDWARE).append('\n')
                .append("HOST: ").append(Build.HOST).append('\n')
                .append("ID: ").append(Build.ID).append('\n')
                .append("MANUFACTURER: ").append(Build.MANUFACTURER).append('\n')
                .append("MODEL: ").append(Build.MODEL).append('\n')
                .append("PRODUCT: ").append(Build.PRODUCT).append('\n')
                .append("SERIAL: ").append(Build.SERIAL).append('\n')
                .append("TAGS: ").append(Build.TAGS).append('\n')
                .append("TIME: ").append(Build.TIME).append('\n')
                .append("TYPE: ").append(Build.TYPE).append('\n')
                .append("USER: ").append(Build.USER).append('\n')
                .append("SDK: ").append(Build.VERSION.SDK).append('\n');

        mModelInfoView.setText(info);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onFragmentAttached("Model");
    }
}
