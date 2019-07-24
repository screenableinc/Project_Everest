package reply_cirlce.screenable.project_everest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Messaging extends Fragment {
    private SQLiteOpenHelper db_helper;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        View rootView = inflater.inflate(R.layout.home, container, false);
//        recyclerView = rootView.findViewById(R.id.my_recycler_view);
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

    public class LoadMessages extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
//            read messages from database
//            eed into a 2 dimensional array
            SQLiteDatabase db = db_helper.getReadableDatabase();
//            String[] projection = {
//                    FeedReaderContract.FeedEntry._id,
//                    FeedReaderContract.FeedEntry.FROM,
//                    FeedReaderContract.FeedEntry.TIMESTAMP,
//                    FeedReaderContract.FeedEntry.TEXT,
//                    FeedReaderContract.FeedEntry.TYPE,
//                    FeedReaderContract.FeedEntry.THUMBNAIL,
//                    FeedReaderContract.FeedEntry.MEDIA_DURATION,
//                    FeedReaderContract.FeedEntry.MEDIA_URL,
//                    FeedReaderContract.FeedEntry.MEDIA_MIME_TYPE,
//                    FeedReaderContract.FeedEntry.PARENT_MESSAGE,
//                    FeedReaderContract.FeedEntry.STATUS
//            };
            String selection = FeedReaderContract.FeedEntry.FROM + " = ?";


            String sortOrder =
                    FeedReaderContract.FeedEntry._id + " DESC";

            Cursor cursor = db.query(
                    FeedReaderContract.FeedEntry.CHAT_TABLE_NAME,
                    null,
                    selection, null, null, null,
                    sortOrder);
            ArrayList<String[]> ind_chat = new ArrayList<String[]>();
            while (cursor.moveToNext()){

                String chat_id = cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.CHAT_ID));
                String last_message = cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.LAST_MESSAGE));
                String participation_count = cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.CHAT_ID));
                String[] info = {chat_id,last_message,participation_count};
                ind_chat.add(info);

//                String [] columnNames = cursor.getColumnNames();
//                for (int i = 0; i<columnNames.length;i++){
//                    cursor.get
//                }

            }
            mAdapter = new MyAdapter(ind_chat);
            recyclerView.setAdapter(mAdapter);

            return null;
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        // Because RecyclerView.Adapter in its current form doesn't natively
        // support cursors, we wrap a CursorAdapter that will do all the job
        // for us.
        private ArrayList<String[]> mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public RelativeLayout relativeLayout;

            public MyViewHolder(RelativeLayout v) {
                super(v);
                relativeLayout = v;
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<String[]> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
            // create a new view
            RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.messageboard, parent, false);

            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
//            holder..setText(mDataset[position]);

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }

    }
}
