package reply_cirlce.screenable.project_everest;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

public class Search extends Fragment {
    RecyclerView recyclerView;
    SpinKitView search_indicator;
    SearchResultsAdapter adapter;
    ArrayList<JSONObject> items = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
//        live audio and video broadcasts

        final View rootView = inflater.inflate(R.layout.search, container, false);
        recyclerView = rootView.findViewById(R.id.search_results);
        final AutoCompleteTextView search = rootView.findViewById(R.id.search);
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode()==KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_UP){
//                    search
                    search_indicator = rootView.findViewById(R.id.search_indicator);
                    Log.w(Globals.TAG,"hit");
                    new SearchUser().execute(search.getText().toString());
                }
                return false;
            }
        });



        return rootView;

    }
    public class SearchUser extends AsyncTask<String,Integer,String>{
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
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

            adapter=new SearchResultsAdapter(getActivity(),items);
            recyclerView.setAdapter(adapter);

        }
    }
}
