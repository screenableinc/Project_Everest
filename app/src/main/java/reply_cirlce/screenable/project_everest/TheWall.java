package reply_cirlce.screenable.project_everest;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import link.fls.swipestack.SwipeStack;


public class TheWall extends Fragment {
    ArrayList<String> following=new ArrayList<>();
    RecyclerView recyclerView;
    TheWallAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();


        View rootView = inflater.inflate(R.layout.thewall, container, false);
        recyclerView= rootView.findViewById(R.id.wall);
        new LoadWall().execute();
//        SwipeStack swipeStack = (SwipeStack) rootView.findViewById(R.id.swipeStack);
//        List<String> mData=new ArrayList<>();
//        mData.add("http://www.screenableinc.com/everest/10.jpg");
//        mData.add("http://www.screenableinc.com/everest/602.jpg");
//        mData.add("http://www.screenableinc.com/everest/601.jpg");
//        mData.add("http://www.screenableinc.com/everest/600.jpg");
//
//        swipeStack.setAdapter(new ChapterVIewAdapter(mData,getActivity()));



        return rootView;

    }
    public class LoadWall extends AsyncTask<String, Integer,String>{
        @Override
        protected String doInBackground(String... strings) {
//            steps(for now
            String userID = getContext().getSharedPreferences(Globals.SHARED_PREF_LOGIN, Context.MODE_PRIVATE).getString(Globals.USERID_KN,"");
            Log.w(Globals.TAG,"reached here ");
            try {
                String connections = new HelperFunctions().GetConnections("followedby", userID,getContext());
                JSONArray array = new JSONObject(connections).getJSONArray("data");
                Log.w(Globals.TAG,"reached "+connections);
                for (int i = 0; i < array.length() ; i++) {
                    String f = array.getJSONObject(i).getString("following");
                    following.add(f);
                }

            }catch (Exception e){

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter=new TheWallAdapter(getContext(),following);
            recyclerView.setAdapter(adapter);
        }
    }
}
