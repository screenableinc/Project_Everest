package reply_cirlce.screenable.project_everest;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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

        for (int i = 0; i < columns.length; i++) {
            contentValues.put(columns[i],values[0]);
        }
        long newRowId = db.insert(table,null,contentValues);
        if(newRowId==-1){
//            error occured
            return false;
        }else {
            return true;
        }



    }
}
