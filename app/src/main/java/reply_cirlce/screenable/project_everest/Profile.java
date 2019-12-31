package reply_cirlce.screenable.project_everest;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.Adapter;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<JSONObject> items=new ArrayList<>();
    ProfileCanvasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        supportPostponeEnterTransition();
        Bundle bundle = getIntent().getExtras();
        String pic_url = bundle.getString("profile_pic_url_lg",null);
        String name=bundle.getString("fullname");
        String username = bundle.getString("UserID");
        TextView _username=findViewById(R.id.username);
        recyclerView = findViewById(R.id.profile_activity_canvas);
        new LoadProfileCanvas().execute(username);
        TextView _name = findViewById(R.id.name);
        _name.setText(name);_username.setText(username);
        CircleImageView circleImageView = findViewById(R.id.profile_image);


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
                JSONArray results = response.getJSONArray("data");
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
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            SpaceItemDecoration decoration = new SpaceItemDecoration(16);
            recyclerView.addItemDecoration(decoration);
            adapter=new ProfileCanvasAdapter(Profile.this,items);
            recyclerView.setAdapter(adapter);

        }
    }
}
