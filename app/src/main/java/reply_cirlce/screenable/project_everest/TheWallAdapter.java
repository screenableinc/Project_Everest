package reply_cirlce.screenable.project_everest;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import link.fls.swipestack.SwipeStack;

public class TheWallAdapter extends RecyclerView.Adapter<TheWallAdapter.ViewHolder> {
    LayoutInflater inflater;
    ArrayList arrayList;
    Context context;

    public TheWallAdapter(Context context,ArrayList arrayList){
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.arrayList=arrayList;
        this.setHasStableIds(true);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//        make request to load images

        try {
            new HelperFunctions().new GetCanvas(arrayList.get(i).toString(),viewHolder,context).execute();
////            JSONObject response = new JSONObject(connections);
//            JSONArray array = response.getJSONArray("canvasdata");
//            viewHolder.username.setText( response.getJSONObject("userdata").getString("username"));
//            Picasso.get().load(response.getJSONObject("userdata").getString("profile_picture_url_lg")).into(viewHolder.profile_pic);
//            ArrayList canvas = new ArrayList<>();
//            for (int j = 0; j < array.length() ; j++) {
//
//                Log.w(Globals.TAG,"Here "+array.getString(j));
//
//                canvas.add(array.get(j));
//            }
//            viewHolder.stack.setAdapter(new ChapterVIewAdapter(canvas,context));
        }catch (Exception e){

            e.printStackTrace();
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.chapter_holder,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        SwipeStack stack;
        CircleImageView profile_pic;
        TextView username;
        public ViewHolder(View itemView){
            super(itemView);
            stack = itemView.findViewById(R.id.swipeStack);
            username = itemView.findViewById(R.id.username);
            profile_pic = itemView.findViewById(R.id.profile_image);
//            get canvas images





        }
        @Override
        public void onClick(View view) {

        }
    }
    public class LoadData extends AsyncTask<String,Integer,String>{
        String target;
        public LoadData(String target, ImageView imageView){
            this.target=target;
        }
        @Override
        protected String doInBackground(String... strings) {
            String[] params={"target"};String[] values={target};
            try {
                String accessApi = new AccessApi().sendGET(Globals.urlGetCanvas, params, values);
                JSONObject response = new JSONObject(accessApi);
                if(response.getBoolean("success")){

                    Log.w(Globals.TAG,response.toString());
                    return response.toString();

                }else {
                    return null;
                }

            }catch (NetworkErrorException e){
//                TODO::Exception
                e.printStackTrace();
                return null;
            }catch (Exception e){
//                TODO here also
                e.printStackTrace();
                return null;
            }



        }
    }
}
