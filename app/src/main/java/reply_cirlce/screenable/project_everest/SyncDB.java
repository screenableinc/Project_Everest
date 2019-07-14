package reply_cirlce.screenable.project_everest;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class SyncDB {
    String id;
    String accessToken;
    Context context;
    String category;
//    copy database from server
    Globals globals = new Globals();
    public SyncDB(String id, Context context,String category){
        this.id = id;

        this.context = context;
        this.category = category;

        SharedPreferences sharedPreferences = context.getSharedPreferences(globals.SHARED_PREF_LOGIN,MODE_PRIVATE);
        this.accessToken = sharedPreferences.getString(globals.ACCESS_TOKEN_KN,null);
        switch (category) {
            case "messages":
                syncMessages();
        }


    }

    private void syncMessages() {
        String [] params = {"accessToken"};
        String [] values = {this.accessToken};
        try {
            String messages = new AccessApi().sendGET(globals.urlMessages, params, values);
//            parse JSON data
            JSONObject
        }catch (Exception e){
//            TODO: catch exception

        }
    }
}
