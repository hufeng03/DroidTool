package com.hufeng.droidtools;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by feng on 2014-04-14.
 */
public class DemoService extends Service {

    private static final String TAG = "DemoService";

    int mStartMode;       // indicates how to behave if the service is killed
    IBinder mBinder;      // interface for clients that bind
    boolean mAllowRebind; // indicates whether onRebind should be used

    public static final String INTENT_ACTION_DEMO_SERVICE_LIFECYCLE = "INTENT_ACTION_DEMO_SERVICE_LIFECYCLE";

    public static final String INTENT_ACTION_DEMO_SERVICE_LIFECYCLE_EXTRA = "LIFE_CYCLE_EXTRA";

    @Override
    public void onCreate() {
        // The service is being created
        Log.i(TAG, "onCreate");
        broadcastLifeCycle("onCreate");
        mBinder = new DemoServiceBinder();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()
        Log.i(TAG, "onStartCommand");
        broadcastLifeCycle("onStartCommand");
        return mStartMode;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // A client is binding to the service with bindService()
        Log.i(TAG, "onBind");
        broadcastLifeCycle("onBind");
        return mBinder;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        // All clients have unbound with unbindService()
        Log.i(TAG, "onUnbind");
        broadcastLifeCycle("onUnbind");
        return mAllowRebind;
    }
    @Override
    public void onRebind(Intent intent) {
        // A client is binding to the service with bindService(),
        // after onUnbind() has already been called
        broadcastLifeCycle("onRebind");
        Log.i(TAG, "onRebind");
    }
    @Override
    public void onDestroy() {
        // The service is no longer used and is being destroyed
        broadcastLifeCycle("onDestroy");
        Log.i(TAG, "onDestroy");
    }

    public class DemoServiceBinder extends Binder {

        DemoService getService() {
            // Return this instance of LocalService so clients can call public methods
            return DemoService.this;
        }
    }

    private void broadcastLifeCycle(String lifeCycle) {
        Intent intent = new Intent(INTENT_ACTION_DEMO_SERVICE_LIFECYCLE);
        intent.putExtra(INTENT_ACTION_DEMO_SERVICE_LIFECYCLE_EXTRA, lifeCycle);
        sendBroadcast(intent);
    }


}
