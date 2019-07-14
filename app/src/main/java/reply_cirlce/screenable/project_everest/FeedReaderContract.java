package reply_cirlce.screenable.project_everest;

import android.provider.BaseColumns;

public class FeedReaderContract {


    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
//        messages table schema
        public static final String MESSAGE_TABLE_NAME = "messages";
        public static final String THUMBNAIL = "thumbnail";
        public static final String _id = "_id";
        public static final String TEXT = "text";
        public static final String TIME_RECEIVED="time_received";
        public static final String STATUS = "status";
        public static final String TYPE="type";

        public static final String FROM = "from";


        public static final String PARENT_MESSAGE = "parent_message_id";
        public static final String MEDIA_URL="media_url";

        public static final String CANVAS_TABLE_NAME = "canvas";

        public static final String TIMESTAMP = "timestamp";
//        seen is a device specific boolean
        public static final String SEEN="seen";

        public static final String MEDIA_DURATION = "media_duration";
        public static final String MEDIA_MIME_TYPE="media_mime_type";
        public static final String CHAT_ID="chat_id";
//        chats______________________________________________________________-

        public static final String PARTICIPATION_COUNT = "participation_count";
        public static final String CHAT_TABLE_NAME = "chats";
            public static final String LAST_MESSAGE = "last_message";

        //        canvas
        public static final String LIKES_COUNT = "likes_count";
        public static final String COPY_COUNT="copy_count";
        public static final String CAPTION="caption";


//        main
public static final String USER_ID = "UserID";
        public static final String PROFILE_PICTURE="profile_picture";
        public static final String MAIN_TABLE_NAME="main";
        public static final String PROFILE_PICTURE_URL ="profile_picture_url";

//        canvas





    }
    }

