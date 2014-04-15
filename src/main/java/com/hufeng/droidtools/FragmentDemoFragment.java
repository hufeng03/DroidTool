package com.hufeng.droidtools;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hufeng.droidtool.R;

/**
 * Created by feng on 2014-04-14.
 */
public class FragmentDemoFragment extends BaseFragment implements View.OnClickListener, CheckBox.OnCheckedChangeListener{

    public static final String FRAGMENT_TAG = "FragmentDemoFragment";

    private Button mBtnStart;
    private TextView mTvProgressInfo;
    private WorkFragment mWorkFragment;
    private CheckBox mCbWorkFragmentReinstance, mCbAsyncTaskParallelExecute, mCbSaveViewStatus;
    private boolean mWorkFragmentReinstance;
    private boolean mAsyncTaskParallelExecute;
    private boolean mSaveViewStatus;

    public static FragmentDemoFragment newFragmentInstance() {
        FragmentDemoFragment demo = new FragmentDemoFragment();
        return demo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_fragment_demo, container, false);
        mBtnStart = (Button) root.findViewById(R.id.btn_start_to_work);
        mTvProgressInfo = (TextView) root.findViewById(R.id.tv_progress_info);
        mCbWorkFragmentReinstance = (CheckBox)root.findViewById(R.id.cb_work_fragment_reinstance);
        mCbAsyncTaskParallelExecute = (CheckBox)root.findViewById(R.id.cb_asynctask_parallel_execute);
        mCbSaveViewStatus = (CheckBox)root.findViewById(R.id.cb_save_view_status);

        restoreSavedInstanceState(savedInstanceState);

        mBtnStart.setOnClickListener(this);
        mCbSaveViewStatus.setOnCheckedChangeListener(this);
        mCbWorkFragmentReinstance.setOnCheckedChangeListener(this);
        mCbAsyncTaskParallelExecute.setOnCheckedChangeListener(this);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addWorkFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mSaveViewStatus) {
            outState.putBoolean("WORK_FRAGMENT_REINSTANCE", mWorkFragmentReinstance);
            outState.putBoolean("ASYNCTASK_PARALLEL_EXECUTE", mAsyncTaskParallelExecute);
            outState.putBoolean("SAVE_VIEW_STATUS", mSaveViewStatus);
            outState.putCharSequence("TEXTVIEW_PROGRESS_INFO", mTvProgressInfo.getText());
        }
    }

    private void restoreSavedInstanceState(Bundle bundle) {
        if (bundle == null) return;

        mWorkFragmentReinstance = bundle.getBoolean("WORK_FRAGMENT_REINSTANCE", false);
        mAsyncTaskParallelExecute = bundle.getBoolean("ASYNCTASK_PARALLEL_EXECUTE", false);
        mSaveViewStatus = bundle.getBoolean("SAVE_VIEW_STATUS", false);

        mCbWorkFragmentReinstance.setChecked(mWorkFragmentReinstance);
        mCbAsyncTaskParallelExecute.setChecked(mAsyncTaskParallelExecute);
        mCbSaveViewStatus.setChecked(mSaveViewStatus);

        mTvProgressInfo.setText(bundle.getCharSequence("TEXTVIEW_PROGRESS_INFO", ""));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mWorkFragment.setTargetFragment(null, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_to_work:
                startToWork();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_work_fragment_reinstance:
                mWorkFragmentReinstance = isChecked;
                break;
            case R.id.cb_asynctask_parallel_execute:
                mAsyncTaskParallelExecute = isChecked;
                break;
            case R.id.cb_save_view_status:
                mSaveViewStatus = isChecked;
                break;
        }
    }

    private void addWorkFragment() {
        Fragment fragment = getFragmentManager().findFragmentByTag(WorkFragment.WorkFragmentTag);
        if (fragment == null) {
            fragment = WorkFragment.newWorkFragment(mWorkFragmentReinstance);
            getFragmentManager().beginTransaction().add(fragment, WorkFragment.WorkFragmentTag).commit();
        }
        mWorkFragment = (WorkFragment)fragment;
        mWorkFragment.setTargetFragment(this, 0);
    }

    private void startToWork() {
        if (mWorkFragment != null) {
            mWorkFragment.startToWork(mAsyncTaskParallelExecute);
        }
    }

    public void reportProgress(String name, int progress) {
        CharSequence chars = mTvProgressInfo.getText();
        StringBuffer buffer = chars == null ? new StringBuffer():new StringBuffer(chars);
        buffer.append(name).append("......").append(progress==0?"waiting":String.valueOf(progress)).append("\n");
        mTvProgressInfo.setText(buffer);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onFragmentAttached("FragmentDemo");
    }


}
