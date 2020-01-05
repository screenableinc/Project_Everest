package reply_cirlce.screenable.project_everest;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.AsyncTask;

public class HelperFunctions {
    Context context;
    public HelperFunctions(Context context){
        this.context=context;
    }
    public class AddToCircle extends AsyncTask<String,Integer,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String[] params={"following","follower"};String[] values={strings[0],strings[1]};
            try {
                String accessApi = new AccessApi().sendGET(Globals.urlAddToCircle, params, values);

            }catch (NetworkErrorException e){
//                TODO::Exception
            }catch (Exception e){
//                TODO here also
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
