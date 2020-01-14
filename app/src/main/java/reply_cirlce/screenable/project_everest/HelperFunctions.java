package reply_cirlce.screenable.project_everest;

import android.accounts.NetworkErrorException;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
//TODO make code dry infuture versions


public class HelperFunctions {
    Context context;
    SpinKitView addingIndicator;
    ImageView add;
    public Bitmap Thumnnail(Context context, Long id){
        Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(), id, MediaStore.Images.Thumbnails.MINI_KIND, (BitmapFactory.Options)null);
        return bitmap;


    }


    public static String removeStartandEndSpaces(String string){
//        String final_;
        while (string.startsWith(" ") || string.startsWith("\n")){
            string=string.substring(1);
        }
        while (string.endsWith(" ") || string.endsWith("\n")){
            string=string.substring(0, string.length()-1);
        }
        return string;
    }

    public class GetCanvas extends AsyncTask<String,Integer,String>{
        String target;
        TheWallAdapter.ViewHolder viewHolder;

        public GetCanvas(String target, TheWallAdapter.ViewHolder viewHolder,Context context){
//            this.connectionType=followedByOrFollowed;
            this.target=target;
            this.viewHolder=viewHolder;
        }

        @Override
        protected String doInBackground(String... strings) {
            String[] params={"target"};String[] values={target};
            try {
                String accessApi = new AccessApi().sendGET(Globals.urlGetCanvas, params, values);
                final JSONObject response = new JSONObject(accessApi);
                if(response.getBoolean("success")){

                    final String username= response.getJSONObject("userdata").getString("username");
                    final String pic_url=response.getJSONObject("userdata").getString("profile_picture_url_lg");

                    Log.w(Globals.TAG,response.toString());
                    JSONArray array = response.getJSONArray("canvasdata");
                    final ArrayList canvas = new ArrayList<>();
                    for (int j = 0; j < array.length() ; j++) {

                        Log.w(Globals.TAG,"Here "+array.getString(j));

                        canvas.add(array.get(j));
                    }

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            viewHolder.username.setText(username);
                            Picasso.get().load(pic_url).into(viewHolder.profile_pic);

                            viewHolder.stack.setAdapter(new ChapterVIewAdapter(canvas,context));

                        }
                    });


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
        return null;

        }
    }

    public String GetConnections(String followedByOrFollowed, String target, Context context){




            String[] params={"connectionType","target"};String[] values={followedByOrFollowed,target};
            try {
                String accessApi = new AccessApi().sendGET(Globals.urlGetAllConnections, params, values);
                JSONObject response = new JSONObject(accessApi);
                if(response.getBoolean("success")){
//                    add connection to local instance of database
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
    public class AddToCircle extends AsyncTask<String,Integer,String>{
        public AddToCircle(ImageView addIcon,SpinKitView spinKitView){
            add=addIcon;
            addingIndicator=spinKitView;
        }
        @Override
        protected void onPreExecute() {
            add.setVisibility(View.GONE);
            addingIndicator.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... strings) {
            String[] params={"following","follower"};String[] values={strings[0],strings[1]};
            try {
                String accessApi = new AccessApi().sendGET(Globals.urlAddToCircle, params, values);
                JSONObject response = new JSONObject(accessApi);
                if(response.getBoolean("success")){
//                    add connection to local instance of database
                }

            }catch (NetworkErrorException e){
//                TODO::Exception
                e.printStackTrace();
            }catch (Exception e){
//                TODO here also
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            add.setVisibility(View.GONE);
            addingIndicator.setVisibility(View.VISIBLE);
        }
    }
}
