package com.hufeng.droidtools;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;

public class WorkFragment extends Fragment {

    public static final String WorkFragmentTag = "WorkFragmentTag";

    public static final String ARGUMENT_REINSTANCE = "ARGUMENT_REINSTANCE";

    private static final String FIRST_TASK_NAME = "TASK1";
    private static final String SECOND_TASK_NAME = "TASK2";

    WorkAsyncTask mTask1 = null;
    WorkAsyncTask mTask2 = null;

    public static WorkFragment newWorkFragment(boolean reinstance) {
        WorkFragment fragment = new WorkFragment();
        Bundle data = new Bundle();
        data.putBoolean(ARGUMENT_REINSTANCE, reinstance);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().getBoolean(ARGUMENT_REINSTANCE)) {
            setRetainInstance(true);
        }
    }

    public void startToWork(boolean parallel) {
        if (mTask1 == null) {
            mTask1 = new WorkAsyncTask(FIRST_TASK_NAME);
            if (parallel) {
                mTask1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                mTask1.execute();
            }
        }
        if (mTask2 == null) {
            mTask2 = new WorkAsyncTask(SECOND_TASK_NAME);
            if (parallel) {
                mTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                mTask2.execute();
            }
        }
    }

    public void reportProgress(String task_name, int progress) {
        if (progress%10 != 0) {
            return;
        }
        Fragment fragment = getTargetFragment();
        if (fragment!=null && fragment instanceof FragmentDemoFragment) {
            ((FragmentDemoFragment)fragment).reportProgress(task_name, progress);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public class WorkAsyncTask extends AsyncTask<Void, Integer, Void> {

        String mName = "WorkAsyncTask";

        public WorkAsyncTask(String name) {
            mName = name;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            reportProgress(mName, 0);
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i+1);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            reportProgress(mName, values[0] == null ? 0 : values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mName.equals(FIRST_TASK_NAME)) {
                mTask1 = null;
            } else if (mName.equals(SECOND_TASK_NAME)) {
                mTask2 = null;
            }
        }
    }
}
