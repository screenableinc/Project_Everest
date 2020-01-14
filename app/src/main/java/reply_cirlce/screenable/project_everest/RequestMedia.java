package reply_cirlce.screenable.project_everest;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

public class RequestMedia {
    Context context;
    public RequestMedia(Context context){
        this.context=context;
    }
    public ArrayList<Uri> Begin(){

        String[] projection = new String[] {

                MediaStore.Images.Media._ID,


        };
//        String selection = sql-where-;
//        String[] selectionArgs = new String[] {
//                values-of-placeholder-variables
//        };
        String sortOrder = MediaStore.Images.Media._ID + " ASC";

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
        );
        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
//        int data_column = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        ArrayList<Uri> uris = new ArrayList<>();
        while (cursor.moveToNext()) {
            long id = cursor.getLong(idColumn);
//            String data = cursor.getString(data_column);
            Uri contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,id);
            uris.add(contentUri);
//            Log.w(Globals.TAG,data+" kkkkkkkkkkk");

            // Use an ID column from the projection to get
            // a URI representing the media item itself.
        }

        return uris;
    }
    public ArrayList<Long> Ids(){
        String[] projection = new String[] {

                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA


        };
//        String selection = sql-where-;
//        String[] selectionArgs = new String[] {
//                values-of-placeholder-variables
//        };
        String sortOrder = MediaStore.Images.Media._ID + " DESC";

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
        );
        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
        int data_column = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        ArrayList<Long> ids = new ArrayList<>();
        while (cursor.moveToNext()) {
            long id = cursor.getLong(idColumn);
            String data = cursor.getString(data_column);
            Uri contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,id);
            ids.add(id);
            Log.w(Globals.TAG,data+" kkkkkkkkkkk");

            // Use an ID column from the projection to get
            // a URI representing the media item itself.
        }

        return ids;
    }
}
