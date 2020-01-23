package reply_cirlce.screenable.project_everest.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import reply_cirlce.screenable.project_everest.DatabaseHelper;
import reply_cirlce.screenable.project_everest.FeedReaderContract;
import reply_cirlce.screenable.project_everest.Globals;
import reply_cirlce.screenable.project_everest.R;

public class DMAdapter extends RecyclerView.Adapter<DMAdapter.ViewHolder> {
    Context context;
    String myUsername;

    ArrayList<HashMap<Object,Object>> chats;
    public DMAdapter(Context context, ArrayList chats){
        this.context=context;
        this.chats=chats;
        myUsername=context.getSharedPreferences(Globals.SHARED_PREF_LOGIN,Context.MODE_PRIVATE).getString("number",null);
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String chat_id = chats.get(position).get(FeedReaderContract.FeedEntry.CHAT_ID).toString();
        holder.username.setText(chat_id.replace(myUsername,"").replace("_",""));
        String last_message_id = chats.get(position).get(FeedReaderContract.FeedEntry.LAST_MESSAGE).toString();
        new LoadMessagesForChat(holder).execute(last_message_id);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.messageboard,parent,false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CircleImageView profileImage;
        TextView message_count;
        TextView last_message;
        TextView time_sent;
        FrameLayout message_count_parent;
        FrameLayout status_ind;
        TextView username;
        public ViewHolder(View itemView){
            super(itemView);
            username = itemView.findViewById(R.id.username);
            last_message = itemView.findViewById(R.id.message_highlight);
            message_count = itemView.findViewById(R.id.message_count);
            time_sent = itemView.findViewById(R.id.time_sent);
            profileImage = itemView.findViewById(R.id.profile_image);
            status_ind = itemView.findViewById(R.id.status_ind);
            message_count_parent = itemView.findViewById(R.id.message_count_parent);

        }

        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.clickable_part){

            }
        }
    }
    public class LoadMessagesForChat extends AsyncTask<String,Integer,String>{
        ViewHolder itemView;
        String last_message;
        String from;
        String to;String type;
        String time_sent;
        String status;


        public LoadMessagesForChat(ViewHolder itemView){
            this.itemView=itemView;
        }
        @Override
        protected String doInBackground(String... strings) {
            String last_message_id=strings[0];
            DatabaseHelper helper = new DatabaseHelper(context);
            SQLiteDatabase db = helper.getReadableDatabase();
            String[] projection = {

                    FeedReaderContract.FeedEntry.TEXT,
                    FeedReaderContract.FeedEntry.TIME_SENT,
                    FeedReaderContract.FeedEntry.FROM,
                    FeedReaderContract.FeedEntry.TO,
                    FeedReaderContract.FeedEntry.TYPE,
                    FeedReaderContract.FeedEntry.STATUS


            };
            String selection = FeedReaderContract.FeedEntry._id + " = ?";

            String [] selectionArgs={last_message_id};
            String sortOrder =
                    FeedReaderContract.FeedEntry._id + " DESC";

            Cursor cursor = db.query(
                    FeedReaderContract.FeedEntry.MESSAGE_TABLE_NAME,
                    null,
                    selection, selectionArgs, null, null,
                    sortOrder);

            while (cursor.moveToNext()){

                last_message = cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.TEXT));

                time_sent = cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.TIME_SENT));
                SimpleDateFormat sdf = new SimpleDateFormat("MM dd,yyyy-HH:mm aa");

                Date date = new Date(Long.parseLong(time_sent));
                time_sent = sdf.format(date).split("-",-1)[1];

                from = cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.FROM));
                to = cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.TO));
                type = cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.TYPE));
                status = cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.STATUS));


//                String [] columnNames = cursor.getColumnNames();
//                for (int i = 0; i<columnNames.length;i++){
//                    cursor.get
//                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            itemView.time_sent.setText(time_sent);
            itemView.last_message.setText(last_message);
            Log.w(Globals.TAG,from+"__"+myUsername+"__"+status);
            if(from.equals(myUsername)){
//                i sent, load status indicators
                itemView.status_ind.setVisibility(View.VISIBLE);
                if (status.equals(Globals.MESSAGE_STATUS_PENDING)){
                    itemView.status_ind.setBackgroundResource(R.drawable.bg_message_pending);

                }else if (status.equals(Globals.MESSAGE_STATUS_SENT)){
                    itemView.status_ind.setBackgroundResource(R.drawable.bg_message_sent);

                }else if (status.equals(Globals.MESSAGE_STATUS_RECEIEVED)){
                    itemView.status_ind.setBackgroundResource(R.drawable.bg_message_received);
                }else if (status.equals(Globals.MESSAGE_STATUS_READ)){
                    itemView.status_ind.setBackgroundResource(R.drawable.bg_message_read);
                }
            }
            else {

            }


//            Picasso.get().load()


        }
    }
}
