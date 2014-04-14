package com.hufeng.droidtools;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/***
 * 
 * @author feng
 *
 */
public class StorageManager {

    private static final String LOG_TAG = StorageManager.class.getSimpleName();
	
	public ArrayList<StorageUnit> mStorageUnits = new ArrayList<StorageUnit>();
	
	private static StorageManager instance;

    private StorageManager() {

    }
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static StorageManager getInstance(Context context) {
		if ( instance == null ) {
			instance = new StorageManager();
			instance.refreshStorageVolume(context);
		}
		return instance;
	}


	
	private void refreshStorageVolume(Context context){
		android.os.storage.StorageManager sStorageManager = (android.os.storage.StorageManager)context.getSystemService(Context.STORAGE_SERVICE);
		boolean flag_reflection_error = false;
		if(sStorageManager==null) {
			flag_reflection_error = true;
		}else{
			Class<?> StorageVolume;
			try {
				StorageVolume = Class.forName("android.os.storage.StorageVolume");
				Method method_getVolumeList = android.os.storage.StorageManager.class.getDeclaredMethod("getVolumeList");
				Method method_getPath = StorageVolume.getDeclaredMethod("getPath");
				//Method method_getDescription = StorageVolume.getDeclaredMethod("getDescription");
				Method method_isRemovable = StorageVolume.getDeclaredMethod("isRemovable");
				Method method_getVolumeState = sStorageManager.getClass().getDeclaredMethod("getVolumeState", String.class);
				try {
					Object volumeList = method_getVolumeList.invoke(sStorageManager);
					if(volumeList.getClass().isArray()){
						int len = Array.getLength(volumeList);
						mStorageUnits.clear();
						for(int i=0;i<len;i++){
							Object volume = Array.get(volumeList, i);
							Object real_volume = StorageVolume.cast(volume);
							
							String path = (String) method_getPath.invoke(real_volume);
                            if (TextUtils.isEmpty(path)) return;
                            path = new File(path).getAbsolutePath();
                            if (TextUtils.isEmpty(path)) return;
							//String description = (String) method_getPath.invoke(real_volume);
							boolean isRemovable = (Boolean) method_isRemovable.invoke(real_volume);
							String state = (String) method_getVolumeState.invoke(sStorageManager, path);
							
							boolean flag = false;
							for(StorageUnit unit:mStorageUnits){
								if(unit.path.equals(path)){
									flag = true;
								}
							}
							if(!flag){
								long availableSize = StorageUtil.getAvailaleSize(path);
								long allSize = StorageUtil.getAllSize(path);
								mStorageUnits.add(new StorageUnit(path, null, isRemovable, state, availableSize, allSize ));
							}
						}
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					flag_reflection_error = true;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					flag_reflection_error = true;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					flag_reflection_error = true;
				}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
				flag_reflection_error = true;
			} catch (SecurityException e) {
				e.printStackTrace();
				flag_reflection_error = true;
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				flag_reflection_error = true;
			}
		}

        if (mStorageUnits.size() == 0) {
            File file = Environment.getExternalStorageDirectory();
            String path = file.getAbsolutePath();
            if (!TextUtils.isEmpty(path)) {
                boolean flag = false;
                for (StorageUnit unit : mStorageUnits) {
                    if (unit.path.equals(path)) {
                        flag = true;
                    }
                }
                if (!flag) {
                    long availableSize = StorageUtil.getAvailaleSize(path);
                    long allSize = StorageUtil.getAllSize(path);
                    mStorageUnits.add(new StorageUnit(path, null, false, Environment.getExternalStorageState(), availableSize, allSize));
                }
            }
        }

        if(mStorageUnits.size()>0) {
            Collections.sort(mStorageUnits, new Comparator<StorageUnit>() {
                @Override
                public int compare(StorageUnit lhs, StorageUnit rhs) {
                    return lhs.path.compareTo(rhs.path);
                }
            });
            int idx = 0;
            for(StorageUnit storage:mStorageUnits) {
                Log.i(LOG_TAG, "storage unit "+idx+":"+storage.toString());
                idx ++;
            }
        } else {
            Log.i(LOG_TAG, "opps, no storage unit");
        }

		return;
	}

    public StorageUnit getDefiniedExternalStorage() {
        File file = Environment.getExternalStorageDirectory();
        String path = file.getAbsolutePath();
        StorageUnit unit = null;
        if (!TextUtils.isEmpty(path) && new File(path).exists()) {
                long availableSize = StorageUtil.getAvailaleSize(path);
                long allSize = StorageUtil.getAllSize(path);
                unit = new StorageUnit(path, null, false, Environment.getExternalStorageState(), availableSize, allSize);
        }
        return unit;
    }
}
