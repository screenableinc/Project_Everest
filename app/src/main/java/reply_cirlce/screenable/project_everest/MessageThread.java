package reply_cirlce.screenable.project_everest;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiPopup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageThread extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagethread);

//        start emoji keyboard code
        EmojiEditText emojiEditText = findViewById(R.id.editText);

        final EmojiPopup emojiPopup = EmojiPopup.Builder.fromRootView(getLayoutInflater().inflate(R.layout.messagethread,null)).build(emojiEditText);
        emojiPopup.toggle(); // Toggles visibility of the Popup.
        emojiPopup.dismiss(); // Dismisses the Popup.
        emojiPopup.isShowing(); // Returns true when Popup is showing.

        final View my_message = findViewById(R.id.my_message);
        ImageView insert = findViewById(R.id.insert);
        final View attach = findViewById(R.id.attach);
        final EditText message = findViewById(R.id.editText);
        View send = findViewById(R.id.send);
        RecyclerView rv = (RecyclerView) findViewById(R.id.dynamicchatview);
        PickerLayoutManager pickerLayoutManager = new PickerLayoutManager(this, PickerLayoutManager.HORIZONTAL, false);
        pickerLayoutManager.setChangeAlpha(true);
        pickerLayoutManager.setScaleDownBy(0.99f);
        pickerLayoutManager.setScaleDownDistance(0.8f);
        ArrayList<String> list = new ArrayList<String>();
        list.add("https://randomuser.me/api/portraits/med/women/67.jpg");
        list.add("https://randomuser.me/api/portraits/med/women/66.jpg");
        list.add("https://randomuser.me/api/portraits/med/women/65.jpg");

        DynamicChatAdapter adapter = new DynamicChatAdapter(this,list);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rv);
        rv.setLayoutManager(pickerLayoutManager);
        rv.setAdapter(adapter);

        pickerLayoutManager.setOnScrollStopListener(new PickerLayoutManager.onScrollStopListener() {
            @Override
            public void selectedView(View view) {
//                Toast.makeText(this, ("Selected value : "+((TextView) view).getText().toString()), Toast.LENGTH_SHORT).show();
            }
        });


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
