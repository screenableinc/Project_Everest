package reply_cirlce.screenable.project_everest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
//    public static final Globals globals = new Globals();
    public static final int version = 7;

//    table creation
private static final String SQL_CREATE_TABLE_MESSAGES =
        "CREATE TABLE IF NOT EXISTS " + FeedReaderContract.FeedEntry.MESSAGE_TABLE_NAME + " (" +
                FeedReaderContract.FeedEntry._id + " TEXT PRIMARY KEY," +
                FeedReaderContract.FeedEntry.TEXT + " TEXT," +
                FeedReaderContract.FeedEntry.STATUS + " TEXT," +
                FeedReaderContract.FeedEntry.TYPE + " TEXT," +
                FeedReaderContract.FeedEntry.THUMBNAIL + " TEXT, "+
                FeedReaderContract.FeedEntry.FROM + " TEXT," +
                FeedReaderContract.FeedEntry.TO + " TEXT," +
                FeedReaderContract.FeedEntry.CHAT_ID + " TEXT," +
                FeedReaderContract.FeedEntry.PARENT_MESSAGE + " TEXT," +
                FeedReaderContract.FeedEntry.MEDIA_URL + " TEXT," +
                FeedReaderContract.FeedEntry.MEDIA_DURATION + " TEXT," +
                FeedReaderContract.FeedEntry.MEDIA_MIME_TYPE + " TEXT," +
                FeedReaderContract.FeedEntry.TIME_SENT +
                 " TEXT)";

    private static final String SQL_CREATE_TABLE_CHAT =
            "CREATE TABLE IF NOT EXISTS " + FeedReaderContract.FeedEntry.CHAT_TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry.CHAT_ID + " TEXT PRIMARY KEY," +
                    FeedReaderContract.FeedEntry.LAST_MESSAGE + " TEXT," +



                    FeedReaderContract.FeedEntry.PARTICIPATION_COUNT + " INTEGER)";

    private static final String SQL_CREATE_TABLE_MAIN =
            "CREATE TABLE IF NOT EXISTS " + FeedReaderContract.FeedEntry.MAIN_TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry.CHAT_ID + " TEXT PRIMARY KEY," +



                    FeedReaderContract.FeedEntry.PARTICIPATION_COUNT + " INTEGER)";

    private static final String SQL_CREATE_TABLE_CONNECTIONS=
            "CREATE TABLE IF NOT EXISTS " + FeedReaderContract.FeedEntry.CANVAS_TABLE_NAME + " (" +

                    FeedReaderContract.FeedEntry.FOLLOWER + " TEXT," +


                    FeedReaderContract.FeedEntry.FOLLOWING + " TEXT)";

    private static final String SQL_CREATE_TABLE_CANVAS =
            "CREATE TABLE IF NOT EXISTS " + FeedReaderContract.FeedEntry.CANVAS_TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._id + " TEXT PRIMARY KEY," +
                    FeedReaderContract.FeedEntry.TEXT + " TEXT," +
                    FeedReaderContract.FeedEntry.STATUS + " TEXT," +
                    FeedReaderContract.FeedEntry.TYPE + " TEXT," +
                    FeedReaderContract.FeedEntry.FROM + " TEXT," +
                    FeedReaderContract.FeedEntry.PARENT_MESSAGE + " TEXT," +
                    FeedReaderContract.FeedEntry.MEDIA_URL + " TEXT," +
                    FeedReaderContract.FeedEntry.MEDIA_DURATION + " TEXT," +
                    FeedReaderContract.FeedEntry.MEDIA_MIME_TYPE + " TEXT," +
                    FeedReaderContract.FeedEntry.TIMESTAMP + " TEXT," +
                    FeedReaderContract.FeedEntry.SEEN
                     + " BOOLEAN," +

                    FeedReaderContract.FeedEntry.TIME_RECEIVED + " TEXT)";




    public DatabaseHelper(Context context) {
        super(context, Globals.DATABASE_NAME, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MAIN);
        db.execSQL(SQL_CREATE_TABLE_MESSAGES);
        db.execSQL(SQL_CREATE_TABLE_CANVAS);
        db.execSQL(SQL_CREATE_TABLE_CHAT);
        db.execSQL(SQL_CREATE_TABLE_CONNECTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
