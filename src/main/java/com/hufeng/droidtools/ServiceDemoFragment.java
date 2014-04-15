package com.hufeng.droidtools;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hufeng.droidtool.R;

/**
 * Created by feng on 2014-04-14.
 */
public class ServiceDemoFragment extends BaseFragment implements View.OnClickListener, ServiceConnection{

    public static final String FRAGMENT_TAG = "ServiceDemoFragment";

    private static final String TAG = "ServiceDemoFragment";

    private Button mBtnBindService, mBtnUnbindService, mBtnStartService, mBtnStopService;

    private TextView mTvStatusInfo;

    private DemoService mService = null;

    private BroadcastReceiver mReceiver = null;

    public static ServiceDemoFragment newFragmentInstance() {
        ServiceDemoFragment demo = new ServiceDemoFragment();
        return demo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_service_demo, container, false);

        mBtnBindService = (Button)rootView.findViewById(R.id.btn_bindService);
        mBtnUnbindService = (Button)rootView.findViewById(R.id.btn_unbindService);
        mBtnStartService = (Button)rootView.findViewById(R.id.btn_startService);
        mBtnStopService = (Button)rootView.findViewById(R.id.btn_stopService);
        mTvStatusInfo = (TextView)rootView.findViewById(R.id.tv_status_info);

        mBtnBindService.setOnClickListener(this);
        mBtnStartService.setOnClickListener(this);
        mBtnUnbindService.setOnClickListener(this);
        mBtnStopService.setOnClickListener(this);

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (DemoService.INTENT_ACTION_DEMO_SERVICE_LIFECYCLE.equals(action)) {
                    String life_cycle = intent.getStringExtra(DemoService.INTENT_ACTION_DEMO_SERVICE_LIFECYCLE_EXTRA);
                    reportStatus(life_cycle);
                }
            }
        };

        getActivity().registerReceiver(mReceiver, new IntentFilter(DemoService.INTENT_ACTION_DEMO_SERVICE_LIFECYCLE));

        return rootView;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(mReceiver);
    }

    public void reportStatus(String status) {
        CharSequence chars = mTvStatusInfo.getText();
        StringBuffer buffer = chars == null ? new StringBuffer():new StringBuffer(chars);
        buffer.append("DemoService ......").append(status).append("\n");
        mTvStatusInfo.setText(buffer);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bindService:
                getActivity().bindService(new Intent(getActivity(), DemoService.class), this, Service.BIND_AUTO_CREATE);
                break;
            case R.id.btn_startService:
                getActivity().startService(new Intent(getActivity(), DemoService.class));
                break;
            case R.id.btn_unbindService:
                    try {
                        getActivity().unbindService(this);
                    }catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), R.string.please_bind_service_before_unbind, Toast.LENGTH_SHORT).show();
                    }
                    mService = null;
                break;
            case R.id.btn_stopService:
                getActivity().stopService(new Intent(getActivity(), DemoService.class));
                mService = null;
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mService = ((DemoService.DemoServiceBinder)service).getService();
        Log.i(TAG, name + " is Connected!");
        Toast.makeText(getActivity(), name + " is Connected!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mService = null;
        Log.i(TAG, name + " is UnConnected!");
        Toast.makeText(getActivity(), name + " is UnConnected!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onFragmentAttached("ServiceDemo");
    }
}
