package com.hufeng.droidtools;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hufeng.droidtool.R;

import java.util.ArrayList;

/**
 * Created by feng on 2014-04-10.
 */
public class StorageFragment extends Fragment {

    public static final String FRAGMENT_TAG = "StorageFragment";

    private TextView mAllStorageInfo;
    private TextView mDefiniedExternalInfo;
    private TextView mDefiniedEnvironmentInfo;
    private TextView mMediaStoreInfo;

    public static StorageFragment newFragmentInstance() {
        StorageFragment fragment = new StorageFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_storage, container, false);
        mAllStorageInfo = (TextView)rootView.findViewById(R.id.all_storage_info);
        mDefiniedExternalInfo = (TextView)rootView.findViewById(R.id.definied_external_info);
        mDefiniedEnvironmentInfo = (TextView)rootView.findViewById(R.id.definied_environment_info);
        mMediaStoreInfo = (TextView)rootView.findViewById(R.id.media_store_info);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity act = getActivity();

        StorageManager manager = StorageManager.getInstance(act);
        ArrayList<StorageUnit> units = manager.mStorageUnits;

        mAllStorageInfo.setText(printStorageUnits(units));

        StorageUnit unit =  manager.getDefiniedExternalStorage();

        mDefiniedExternalInfo.setText(printStorageUnit(unit));

        mMediaStoreInfo.setText(MediaStoreUtil.getSampleImageFiles(act));

        mDefiniedEnvironmentInfo.setText(EnvironmentUtil.test()+"\n\n"+ EnvironmentUtil.test2(act));

    }

    private String printStorageUnits(ArrayList<StorageUnit> units) {
        StringBuilder builder = new StringBuilder();
        if (units != null) {
            for (StorageUnit unit : units) {
                builder.append(unit.toString()).append('\n');
            }
        }
        return builder.toString();
    }


    private String printStorageUnit(StorageUnit unit) {
        StringBuilder builder = new StringBuilder();
        if (unit != null) {
                builder.append(unit.toString()).append('\n');
        }
        return builder.toString();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onFragmentAttached("Storage");
    }

}
