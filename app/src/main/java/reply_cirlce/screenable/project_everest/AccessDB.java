package reply_cirlce.screenable.project_everest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

public class AccessDB{
    Context context;
//    SQLiteDatabase db;
    public AccessDB(Context context){
        this.context=context;

    }
    public boolean Push(String table,String[] columns, String[] values){

        ContentValues contentValues = new ContentValues();
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor dbCursor = helper.getReadableDatabase().query(table, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();
        for (int i = 0; i <columnNames.length ; i++) {
            Log.w(Globals.TAG,columnNames[i]);
        }


        for (int i = 0; i < columns.length; i++) {
            contentValues.put(columns[i],values[i]);
        }

        long newRowId=-1;
        if(table.equals(FeedReaderContract.FeedEntry.CHAT_TABLE_NAME)){
            newRowId = db.insertWithOnConflict(table, FeedReaderContract.FeedEntry.CHAT_ID,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        }else{
        newRowId = db.insert(table,null,contentValues);
        }
        if(newRowId==-1){
//            error occured
            return false;
        }else {
            return true;
        }



    }
}
