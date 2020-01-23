package reply_cirlce.screenable.project_everest;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {
    ArrayList <JSONObject> items;
    Context context;
    Activity activity;
    String _ID="";
    ItemClickListener mClickListener;
    LayoutInflater mInflater;
    public SearchResultsAdapter(Activity context, ArrayList<JSONObject> items){
        this.context=context.getApplicationContext();
        mInflater = LayoutInflater.from(context.getApplicationContext());
        this.items=items;
        this.activity=context;
//        shared preference to get userID
        _ID=context.getSharedPreferences(Globals.SHARED_PREF_LOGIN,Context.MODE_PRIVATE).getString(Globals.USERID_KN,"");

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_search_result,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
//        loop through and show results
        try{
            Log.w(Globals.TAG,"should have shown: "+items.get(i));
            JSONObject object = (JSONObject) items.get(i);
            String username = object.getString("UserID");
            String name = object.getString("fullname");

            String profile_picture_url = object.getString("profile_picture_url_md");
            viewHolder.name.setText(name);
            viewHolder.username.setText(username);
            viewHolder.nameandusername.setTag(items.get(i).toString());
            viewHolder.add.setTag(username);
            viewHolder.add.setText("Invite");
//            viewHolder.add.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_add));
            Picasso.get().load(profile_picture_url).into(viewHolder.circleImageView);




        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CircleImageView circleImageView;
        TextView name;
        TextView username;
        Button add;
//        TODO remember to show diffrent icons for already added user

        View nameandusername;
        SpinKitView spinKitView;
        ViewHolder(View itemView){
            super(itemView);

            circleImageView= (CircleImageView) itemView.findViewById(R.id.profile_image);
            nameandusername = itemView.findViewById(R.id.nameandusername);
            name = (TextView) itemView.findViewById(R.id.name);
            username=(TextView) itemView.findViewById(R.id.username);
            add=itemView.findViewById(R.id.addtocircle);
            spinKitView=itemView.findViewById(R.id.search_indicator);
            nameandusername.setOnClickListener(this);add.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

//
            if (view.getId() == R.id.addtocircle) {
                Log.w(Globals.TAG,"statr");

                new HelperFunctions().new AddToCircle(add,spinKitView).execute(view.getTag().toString(),_ID);
//
            } else {

                Intent intent = new Intent(context, Profile.class);
//            pass username, name,profile_pic_url so as to parse data on this side
                String tag = view.getTag().toString();
                String pic_url;
                String _user_id;
                String _name;
                String _username;
                try {
                    JSONObject data = new JSONObject(tag);
                    Log.w(Globals.TAG, "should show " + view.toString() + data);
                    pic_url = data.getString("profile_picture_url_lg");
                    _username=data.getString("username");
                    _user_id = data.getString("UserID");
                    _name = data.getString("fullname");
                    intent.putExtra("UserID", _user_id);
                    intent.putExtra(Globals.USERNAME_KN,_username);
                    intent.putExtra("profile_pic_url", pic_url);
                    intent.putExtra("fullname", _name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, Pair.create((View) name, "fullname"), Pair.create((View) username, "username"));
//                ActivityOptions options = ActivityOptions
//                        .makeSceneTransitionAnimation(activity, (View) name, "fullname");
                activity.startActivity(intent, options.toBundle());


                if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
    String getItem(int id) {
        return items.get(id).toString();
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
