package reply_cirlce.screenable.project_everest;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONObject;
//TODO make code dry infuture versions


public class HelperFunctions {
    Context context;
    SpinKitView addingIndicator;
    ImageView add;
    public HelperFunctions(Context context){
        this.context=context;
    }

    public class GetCanvas extends AsyncTask<String,Integer,String>{
        String target;

        public GetCanvas(String target){
//            this.connectionType=followedByOrFollowed;
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

    public String GetConnections(String followedByOrFollowed, String target){




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
