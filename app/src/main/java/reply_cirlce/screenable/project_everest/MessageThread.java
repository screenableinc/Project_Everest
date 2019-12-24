package reply_cirlce.screenable.project_everest;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MessageThread extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagethread);
        final View my_message = findViewById(R.id.my_message);
        ImageView insert = findViewById(R.id.insert);
        final View attach = findViewById(R.id.attach);
        final EditText message = findViewById(R.id.editText);
        View send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!message.getText().toString().equals("")){
                    long unixTime = System.currentTimeMillis() / 1000L;
                    SharedPreferences preferences =getSharedPreferences(Globals.SHARED_PREF_LOGIN,MODE_PRIVATE);
                    String number = preferences.getString("number","260954806566");
//                    TODO::Add if clause to log out and delete data if number==null
                    if(number==null){

                    }
                    String[][] req_body = {{"message_id",""},{"text",message.getText().toString()},
                            {"time_received",""},
                            {"status",""},{"type",""},
                            {"sender",number},{"parent_message_id",""},
                            {"media_duration",""},{"media_duration",""},
                            {"media_url",""},{"media_mime_type",""},
                            {"chat_id",""},{"has_attachments",""},{"recipient_count",""},
                            {"recipients",""},
                            {"time_sent",unixTime+""}};

                    new SendMessage().execute(req_body);

                }
            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(attach.getVisibility()==View.VISIBLE){
                    attach.setVisibility(View.GONE);
                }else {
                    attach.setVisibility(View.VISIBLE);
                }
            }
        });




//        my_message.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Log.w("TOUCHLISTEN", motionEvent.toString());
//                return false;
//            }
//        });

//        message thread animations


    }
    private static class SendMessage extends AsyncTask<String[],Integer,String>{

        @Override
        protected String doInBackground(String[]... objects) {

            JSONObject body = new JSONObject();
            try{

            for (int i = 0; i < objects.length; i++) {
                String[] innerobj = objects[i];

//                    add into json
                    Log.w(Globals.TAG,innerobj[0].toString());

                    body.put(innerobj[0].toString(), innerobj[1]);



            }
//            send request to server
                Log.w(Globals.TAG,body.toString());
                String accessAPI = new AccessApi().sendPost(Globals.url_send_message,body.toString());
                Log.w(Globals.TAG,accessAPI);
            }catch (Exception e){
//                        TODO:: handle this exception
                e.printStackTrace();
                Log.w( Globals.TAG,"failed");
            }
            return null;
        }
    }


}
