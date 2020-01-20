package reply_cirlce.screenable.project_everest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileCanvasAdapter extends RecyclerView.Adapter<ProfileCanvasAdapter.ViewHolder> {
    ArrayList<JSONObject> items;
    Context context;
    Activity activity;

    SearchResultsAdapter.ItemClickListener mClickListener;
    LayoutInflater mInflater;
    public ProfileCanvasAdapter(Activity context, ArrayList<JSONObject> items){
        this.context=context.getApplicationContext();
        mInflater = LayoutInflater.from(context.getApplicationContext());
        this.items=items;
        this.activity=context;

    }


    @Override
    public ProfileCanvasAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.profile_canvas_image,viewGroup,false);
        return new ProfileCanvasAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProfileCanvasAdapter.ViewHolder viewHolder, int i) {
//        loop through and show results
        try{
            Log.w(Globals.TAG,"should have shown: "+items.get(i));
            JSONObject object = (JSONObject) items.get(i);
//            String username = object.getString("UserID");
//            String name = object.getString("fullname");
//            this shoud have the thumnail url
            String profile_picture_url = object.getString("media_url_high");

            Picasso.get().load(profile_picture_url).into(viewHolder.imageView);




        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        ImageView imageView;
        ViewHolder(View itemView){
            super(itemView);

            imageView= itemView.findViewById(R.id.image);
//            nameandusername.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            TODO::remember to filter this and add support for add

            Intent intent = new Intent(context,Profile.class);
//            pass username, name,profile_pic_url so as to parse data on this side
            String tag = view.getTag().toString();
            String pic_url;
            String _username;
            String _name;
            try {
                JSONObject data = new JSONObject(tag);
                Log.w(Globals.TAG,"should show "+view.toString()+data);
                pic_url=data.getString("profile_picture_url_lg");

                _username = data.getString("UserID");
                _name = data.getString("fullname");
                intent.putExtra("UserID",_name);
                intent.putExtra("profile_pic_url_lg",pic_url);
                intent.putExtra("fullname",_username);
            }catch (JSONException e){
                e.printStackTrace();
            }


//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, Pair.create((View) name,"fullname"),Pair.create((View)username,"username"));
//            activity.startActivity(intent,options.toBundle());


            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    String getItem(int id) {
        return items.get(id).toString();
    }

    // allows clicks events to be caught
    void setClickListener(SearchResultsAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
