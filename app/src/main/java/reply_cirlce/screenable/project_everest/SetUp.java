package reply_cirlce.screenable.project_everest;

import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class SetUp extends AppCompatActivity {
    Button finalize;
    ProgressBar progressBar;
    ImageView error;
    String u_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        finalize = findViewById(R.id.finalize);
        final EditText fullnameView = findViewById(R.id.fullname);
        final EditText usernameView = findViewById(R.id.username);
        error=findViewById(R.id.error);
        final String userID = getSharedPreferences(Globals.SHARED_PREF_LOGIN,MODE_PRIVATE).getString("number","");
        usernameView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(error.getVisibility()==View.VISIBLE){
                    error.setVisibility(View.GONE);
                }
                return false;
            }
        });
        finalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                u_name=usernameView.getText().toString();
                String body = "{\"username\":\""+usernameView.getText().toString()+"\",\"fullname\":\""+fullnameView.getText().toString()+"\",\"UserID\":\""+userID+"\"}";
                new Finalize().execute(body);
            }
        });

        progressBar = findViewById(R.id.progress);
    }


    public class Finalize extends AsyncTask<String,Integer,String> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            finalize.setVisibility(View.GONE);

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String accessAPI = new AccessApi().sendPost(Globals.urlFinalize, strings[0]);
                JSONObject object = new JSONObject(accessAPI);
                if(object.getInt("code")==200){
                    SharedPreferences sharedPreferences = getSharedPreferences(Globals.SHARED_PREF_LOGIN,MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //                    create database
                    new DatabaseHelper(getApplicationContext());
                    editor.putString("stage","loggedin");
                    editor.putString(Globals.USERNAME_KN,u_name);
                    editor.commit();
//                    send another request to get connections

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(SetUp.this,MainActivity.class));
                            finish();
                        }
                    });

                }else if(object.getInt("code")==201) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SetUp.this,"Username Already exists",Toast.LENGTH_LONG).show();
                            error.setVisibility(View.VISIBLE);
                        }
                    });
                                    }
                else if(object.getInt("code")==500){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SetUp.this,"Something went wrong, try again",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }catch (JSONException e){
                e.printStackTrace();
            }catch (NetworkErrorException e){
e.printStackTrace();
            }catch (Exception e){
    e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.GONE);
            finalize.setVisibility(View.VISIBLE);
        }
    }
}
