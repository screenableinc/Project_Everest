package reply_cirlce.screenable.project_everest;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TheWall extends Fragment {
    RecyclerView recyclerView;
    SpinKitView search_indicator;
    SearchResultsAdapter adapter;
    ArrayList<JSONObject> items = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
//        live audio and video broadcasts

        final View rootView = inflater.inflate(R.layout.thewall, container, false);
        recyclerView = rootView.findViewById(R.id.search_results);
        final AutoCompleteTextView search = rootView.findViewById(R.id.search);
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode()==KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_UP){
//                    search
                    search_indicator = rootView.findViewById(R.id.search_indicator);
                    Log.w(Globals.TAG,"hit");
                    new Search().execute(search.getText().toString());
                }
                return false;
            }
        });



        return rootView;

    }
    public class Search extends AsyncTask<String,Integer,String>{
        @Override
        protected void onPreExecute() {
            search_indicator.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String[] param={"qs"};String[] value={strings[0]};
            try {
                String accessApi = new AccessApi().sendGET(Globals.urlSearch, param, value);
                JSONObject response = new JSONObject(accessApi);
                JSONArray results = response.getJSONArray("data");
                for (int i = 0; i < results.length(); i++) {
                    Log.w(Globals.TAG, results.getJSONObject(i).toString());
                    items.add(results.getJSONObject(i));
                }
                Log.w(Globals.TAG,"here"+accessApi);
            }catch (NetworkErrorException e){
//                shpwerror network

            }catch (Exception e){
                Log.w(Globals.TAG,e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            search_indicator.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            adapter=new SearchResultsAdapter(getActivity(),items);
            recyclerView.setAdapter(adapter);

        }
    }
}
