package reply_cirlce.screenable.project_everest;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONObject;

public class Verification extends AppCompatActivity {
    Globals globals = new Globals();
    Button verify = (Button) findViewById(R.id.verify_number);
    ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);


        EditText vcodeInput = (EditText) findViewById(R.id.vcode);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(globals.SHARED_PREF_LOGIN,MODE_PRIVATE);
        String number = preferences.getString("number",null);
        String credentials = "{\"number\":"+number+",\"verificationCode\":"+vcodeInput.getText()+"}";

    }
    public class Verify extends AsyncTask<String, Integer, JSONObject>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            verify.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

        }


        @Override
        protected JSONObject doInBackground(String... params) {
            try{
                String accessApi = new AccessApi().sendPost(globals.urlVerify, params[0]);
                JSONObject response = new JSONObject(accessApi);

                if(response.getInt("code")==1){
//                    handle success
//                    save token in sharedPref
                    SharedPreferences sharedPreferences = getSharedPreferences(globals.SHARED_PREF_LOGIN,MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("accessToken",response.getString("data"));
                    editor.commit();
//                    create database
                    new DatabaseHelper(getApplicationContext());



                }else if(response.getInt("code")==0){
//                    handle error

                }

            }catch (Exception e){
//                handle device error
            }

            return null;
        }    }
}
