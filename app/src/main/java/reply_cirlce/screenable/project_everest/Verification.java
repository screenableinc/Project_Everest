package reply_cirlce.screenable.project_everest;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONObject;

public class Verification extends AppCompatActivity {
    Button verify;
    ProgressBar progressBar;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        verify =  findViewById(R.id.verify_number);
        progressBar = findViewById(R.id.progress);

        logo=findViewById(R.id.logo);
        final EditText vcodeInput = findViewById(R.id.vcode);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(Globals.SHARED_PREF_LOGIN,MODE_PRIVATE);
        final String number = preferences.getString("number",null);
        final String vcode = preferences.getString("verification_code",null);
        if(vcode==null || number==null){
//            return to login screen
            startActivity(new Intent(Verification.this,LoginActivity.class));
        }

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String credentials = "{\"number\":"+number+",\"verificationCode\":"+vcodeInput.getText().toString()+"}";

                Log.w(Globals.TAG,credentials);
                new Verify().execute(credentials);
            }
        });

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
                Log.w(Globals.TAG,params[0]);
                String accessApi = new AccessApi().sendPost(Globals.urlVerify, params[0]);
                JSONObject response = new JSONObject(accessApi);
                Log.w(Globals.TAG,response.toString());

                if(response.getInt("code")==1){
//                    handle success
//                    save token in sharedPref
                    SharedPreferences sharedPreferences = getSharedPreferences(Globals.SHARED_PREF_LOGIN,MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

//                    create database
                    new DatabaseHelper(getApplicationContext());
                                  runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Verification.this, logo, getResources().getResourceName(R.string.logo_trans_name));

                            startActivity(new Intent(Verification.this, SetUp.class),options.toBundle());


                        }
                    });


                    editor.putString("accessToken",response.getString("data"));
                    editor.putString("stage","setup");
                    editor.commit();

                }else if(response.getInt("code")==0){
//                    handle error
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Verification.this,"wrong code",Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }catch (Exception e){
//                handle device error
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            progressBar.setVisibility(View.GONE);
            verify.setVisibility(View.VISIBLE);
        }
    }

}
