package reply_cirlce.screenable.project_everest;

import android.accounts.NetworkErrorException;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.PersistableBundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgabrielfreitas.core.BlurImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.picasso.transformations.BlurTransformation;


public class Profile extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<JSONObject> items=new ArrayList<>();
    ProfileCanvasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile2_0);
//        FrameLayout bg_overlay = findViewById(R.id.bg);
        ImageView imageview = findViewById(R.id.background);
//        Log.w(Globals.TAG,bg_overlay.toString());



        supportPostponeEnterTransition();
        Bundle bundle = getIntent().getExtras();
        final String pic_url = bundle.getString(Globals.PROFILE_PIC_URL_KN);
        String name=bundle.getString(Globals.FULLNAME_KN);
        final String user_id = bundle.getString(Globals.USER_ID_KN);
        final String user_name = bundle.getString(Globals.USERNAME_KN);

        TextView _username=findViewById(R.id.username);
        Picasso.get().load(pic_url).transform(new BlurTransformation(Profile.this,12,1)).into(imageview);

        recyclerView = findViewById(R.id.profile_activity_canvas);
        new LoadProfileCanvas().execute(user_id);
        TextView _name = findViewById(R.id.name);
        _name.setText(name);_username.setText(user_name);
        CircleImageView circleImageView = findViewById(R.id.profile_image);
        ImageView dm = findViewById(R.id.dm);
        dm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, MessageThread.class);
                intent.putExtra(Globals.USER_ID_KN,user_id);
                intent.putExtra(Globals.USERNAME_KN,user_name);
                intent.putExtra(Globals.PROFILE_PIC_URL_KN,pic_url);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(Profile.this).toBundle());

            }
        });


        Picasso.get()
                .load(pic_url)
                .noFade()
                .into(circleImageView, new Callback() {
                    @Override
                    public void onError(Exception e) {
                        supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onSuccess() {
                        supportStartPostponedEnterTransition();
                    }


                });

    }



    private class LoadProfileCanvas extends AsyncTask<String, Integer,String>{
        @Override
        protected String doInBackground(String... strings) {
            String[] param={"target"};String[] value={strings[0]};
            try {
                String accessApi = new AccessApi().sendGET(Globals.urlCanvas, param, value);
                JSONObject response = new JSONObject(accessApi);
                Log.w(Globals.TAG,response.toString());
                JSONArray results = response.getJSONArray("canvasdata");
                for (int i = 0; i < results.length(); i++) {
                    Log.w(Globals.TAG, results.getJSONObject(i).toString());
                    items.add(results.getJSONObject(i));
                }
                Log.w(Globals.TAG,"here"+accessApi);
            }catch (NetworkErrorException e){
//                shpwerror network
                e.printStackTrace();

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
//            TODO:::null check on items list
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));


            adapter=new ProfileCanvasAdapter(Profile.this,items);
            recyclerView.setAdapter(adapter);

        }
    }
}
