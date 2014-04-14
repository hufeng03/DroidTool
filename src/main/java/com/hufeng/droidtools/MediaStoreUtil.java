package com.hufeng.droidtools;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by feng on 2014-04-10.
 */
public class MediaStoreUtil {

    public static String getSampleImageFiles(Context context){
        StringBuilder builder = new StringBuilder();
        String volumeName = "external";
        //image
        Uri uri = MediaStore.Images.Media.getContentUri(volumeName);
        String[] projection = new String[]{
                MediaStore.MediaColumns.DATA,
                MediaStore.MediaColumns.SIZE,
                MediaStore.MediaColumns.DATE_MODIFIED,
                MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.WIDTH,
                MediaStore.MediaColumns.HEIGHT,
        };

        String selection = null;
        Cursor cursor = null;
        try{
            cursor = context.getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null) {
                int i = 0;
                while (cursor.moveToNext()) {
                    if (i >5) {
                        break;
                    }
                     builder.append(cursor.getString(0)).append(" | ")
                             .append(cursor.getLong(1)).append(" | ")
                             .append(cursor.getLong(2)).append(" | ")
                             .append(cursor.getString(3)).append(" | ")
                             .append(cursor.getInt(4)).append(" | ")
                             .append(cursor.getInt(5)).append("\n");
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return builder.toString();

    }

}
