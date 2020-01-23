package reply_cirlce.screenable.project_everest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;

import reply_cirlce.screenable.project_everest.Adapters.DMAdapter;

public class Messaging extends Fragment {
    private DatabaseHelper db_helper;
    private RecyclerView recyclerView;

    DMAdapter dmAdapter;
    ArrayList<HashMap<Object,Object>> chat_list = new ArrayList<HashMap<Object, Object>>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        View rootView = inflater.inflate(R.layout.home, container, false);
        recyclerView = rootView.findViewById(R.id.chats);
        chat_list.clear();
        new LoadChats().execute();
//
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        recyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);

//        // specify an adapter (see also next example)
//        mAdapter = new MyAdapter(myDataset);
//        recyclerView.setAdapter(mAdapter);

//        loadmessages

        return rootView;


    }
    private void insertIntoHashArray(String chat_id, String last_message_id){
        HashMap<Object,Object> hashMap = new HashMap<>();
        hashMap.put(FeedReaderContract.FeedEntry.CHAT_ID,chat_id);
        hashMap.put(FeedReaderContract.FeedEntry.LAST_MESSAGE,last_message_id);
        chat_list.add(hashMap);
    }

    public class LoadChats extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
//            read messages from database
//            eed into a 2 dimensional array
            db_helper=new DatabaseHelper(getContext());
            SQLiteDatabase db = db_helper.getReadableDatabase();
            String[] projection = {
                    FeedReaderContract.FeedEntry.CHAT_ID,
                    FeedReaderContract.FeedEntry.LAST_MESSAGE

            };
            String selection = FeedReaderContract.FeedEntry.FROM + " = ?";


            String sortOrder =
                    FeedReaderContract.FeedEntry.CHAT_ID + " DESC";

            Cursor cursor = db.query(
                    FeedReaderContract.FeedEntry.CHAT_TABLE_NAME,
                    projection,
                    null, null, null, null,
                    sortOrder);
            ArrayList<String[]> ind_chat = new ArrayList<String[]>();
            while (cursor.moveToNext()){

                String chat_id = cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.CHAT_ID));
                String last_message = cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.LAST_MESSAGE));

                insertIntoHashArray(chat_id,last_message);

//                String [] columnNames = cursor.getColumnNames();
//                for (int i = 0; i<columnNames.length;i++){
//                    cursor.get
//                }

            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            dmAdapter=new DMAdapter(getContext(),chat_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(dmAdapter);

        }
    }


}
