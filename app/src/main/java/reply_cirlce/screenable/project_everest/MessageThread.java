package reply_cirlce.screenable.project_everest;

import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.emoji.widget.EmojiEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

//import com.vanniktech.emoji.EmojiEditText;
//import com.vanniktech.emoji.EmojiPopup;

import com.airbnb.lottie.LottieAnimationView;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class MessageThread extends AppCompatActivity {
    MessagesAdapter messagesAdapter;
    ArrayList<HashMap<Object,Object>> messages_list = new ArrayList<HashMap<Object, Object>>();
    RecyclerView messages;
    RecyclerView mediaThumbs;
    ArrayList<Uri> mediaThumbsUris;
    View parent;
    ThumbnailAdapter adapter;
    ArrayList<Long> mediaThumbIDS;
    private Socket mSocket;


    private void connectSocket(IO.Options options){
        try{
            Log.w(Globals.TAG,"should have started socket");

            mSocket= IO.socket(Globals.ip+"/", options).connect();


        }catch (URISyntaxException e){
            e.printStackTrace();
        }
    }
    //    LinearLayout ExpandingMediaHolder;
//    LinearLayout bottomSheetLayout;

//    TODO:: move socket functions to services



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagethread);
        Bundle bundle = getIntent().getExtras();
        final String user_id = bundle.getString(Globals.USER_ID_KN);
        String pic_url = bundle.getString(Globals.PROFILE_PIC_URL_KN);

//        get own user_id
        final String own_user_id = getSharedPreferences(Globals.SHARED_PREF_LOGIN,MODE_PRIVATE).getString("number",null);
        String[] arr = {user_id,own_user_id};

        Arrays.sort(arr);
        final String chat_id = arr[0]+"_"+arr[1];
        IO.Options options = new IO.Options();
        options.query="chat_id="+chat_id;
        connectSocket(options);


//        start emoji keyboard code
//        EmojiEditText emojiEditText = findViewById(R.id.editText);
        parent = findViewById(R.id.parent);
//        parent.getLayoutParams().height=1344;
        final View ExpandingMediaHolder=findViewById(R.id.bottom_sheet);


        final LottieAnimationView media = findViewById(R.id.media);

        media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                animate open

                if(ExpandingMediaHolder.getLayoutParams().height!=0){
                    float progress = media.getProgress();
                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(-progress,0 ).setDuration((long) ( media.getDuration()* progress));
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {

                            media.setProgress(Math.abs((float)animation.getAnimatedValue()));
                        }
                    });
                    valueAnimator.start();
                }else {
                    media.playAnimation();
                }
                expandMediaHolder(ExpandingMediaHolder);
            }
        });

        final View my_message = findViewById(R.id.my_message);
        final ImageView insert = findViewById(R.id.insert);
//        final View attach = findViewById(R.id.attach);
        final EditText message = findViewById(R.id.editText);
        View send = findViewById(R.id.send);
        RecyclerView rv = (RecyclerView) findViewById(R.id.dynamicchatview);
        PickerLayoutManager pickerLayoutManager = new PickerLayoutManager(this, PickerLayoutManager.HORIZONTAL, false);
        pickerLayoutManager.setChangeAlpha(true);
        pickerLayoutManager.setScaleDownBy(0.99f);
        pickerLayoutManager.setScaleDownDistance(0.8f);
        final ArrayList<String> list = new ArrayList<String>();
        list.add("https://randomuser.me/api/portraits/med/women/67.jpg");
        list.add("https://randomuser.me/api/portraits/med/women/66.jpg");
        list.add("https://randomuser.me/api/portraits/med/women/65.jpg");
        final View nontext = (LinearLayout) findViewById(R.id.non_text);
        messages=findViewById(R.id.messages);




//        footer.setOnGenericMotionListener(new View.OnGenericMotionListener() {
//            @Override
//            public boolean onGenericMotion(View view, MotionEvent motionEvent) {
//                Log.w(Globals.TAG,motionEvent.toString());
//                return false;
//            }
//        });

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
        messagesAdapter = new MessagesAdapter(messages_list,getApplicationContext());
        messages.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        messages.setAdapter(messagesAdapter);


        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String msg = message.getText().toString();

                if(!msg.isEmpty()){
                    long unixTime = System.currentTimeMillis();



                SimpleDateFormat sdf = new SimpleDateFormat("MM dd,yyyy-HH:mm aa");

                Date date = new Date(unixTime);
                String date_time = sdf.format(date).split("-",-1)[1];
                Log.w(Globals.TAG,mSocket.toString());




//                    TODO::Add if clause to log out and delete data if number==null
                    if(own_user_id!=null){
                        try{
                            populateList(HelperFunctions.removeStartandEndSpaces(msg),date_time);
                            message.setText("");
                            String message_id = unixTime+chat_id;
                            JSONObject message_details=prepareMessage(message_id,msg,unixTime,"unread","text",own_user_id,null,00000000,null,null,chat_id,false,user_id);

                            mSocket.emit(chat_id,message_details);
                            mSocket.on(own_user_id, new Emitter.Listener() {
                                @Override
                                public void call(Object... args) {
//                                         save to database and notify dataset change

                                }
                            });
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                }
            }
        });
        View rootView = findViewById(R.id.root);
//        final EmojiPopup emojiPopup = EmojiPopup.Builder.fromRootView(rootView).build(emojiEditText);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_attach));

            animateShowAttachments(nontext, view);



            }
        });







    }

    private JSONObject prepareMessage(String message_id, String text, long time_sent, String status, String type, String sender, @Nullable String parentMessage_id,
    @Nullable long media_duration, @Nullable String media_url, @Nullable String media_mime_type, @NonNull String chat_id, boolean has_attachments, @NonNull String to) throws Exception{
        JSONObject object=  new JSONObject();
        object.put("message_id",message_id);
        object.put("text",text);
        object.put("recipient",to);
        object.put("status",status);
        object.put("type",type);
        object.put("sender",sender);
        object.put("parent_message_id",parentMessage_id);object.put("media_duration",media_duration);
        object.put("media_url",media_url);object.put("media_mime_type",media_mime_type);object.put("chat_id",chat_id);object.put("has_attachments",has_attachments);
        object.put("time_sent",time_sent);

//        put into database
        return object;

    }


    private void expandMediaHolder(final View ExpandingMediaHolder){
        int toHeight;
        Log.w(Globals.TAG,parent.getLayoutParams().height+"haha");

        if(ExpandingMediaHolder.getLayoutParams().height==0){

            toHeight=400;

        }else {
            toHeight=0;

        }
        final ValueAnimator widthAnimator = ValueAnimator.ofInt(ExpandingMediaHolder.getLayoutParams().height, toHeight);
        widthAnimator.setDuration(200);
        widthAnimator.setInterpolator(new DecelerateInterpolator());
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ExpandingMediaHolder.getLayoutParams().height = (int) animation.getAnimatedValue();
                ExpandingMediaHolder.requestLayout();
                if((int)animation.getAnimatedValue()==ExpandingMediaHolder.getLayoutParams().height){
                    loadImages();
                }
            }

        });

        widthAnimator.start();
    }
    public void loadImages(){
//        check permission first

        new LoadMedia().execute();




    }
    public void populateList(String one,String two){
        HashMap<Object,Object> hashMap = new HashMap<>();
        hashMap.put("message",one);
        hashMap.put("time",two);

        messages_list.add(hashMap);
        messagesAdapter.notifyDataSetChanged();
        messages.scrollToPosition(messages_list.size()-1);
        Log.w(Globals.TAG,messages_list.toString());
    }
    public void adjustViews(float offset){
        Display display = (MessageThread.this).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y-30;
        int newHeight = (int) (height-offset);


//        Log.w(Globals.TAG,newHeight+"ok");
//        parent.getLayoutParams().height=newHeight;
//        parent.requestLayout();
    }
    private void animateShowAttachments(final View view, View chevron){
        int toWidth;
        int animFile;
        if(view.getLayoutParams().width==0){
            toWidth=120;
            animFile=R.anim.rotate_attach;
        }else {
            toWidth=0;
            animFile=R.anim.rotate_attach_to_init;
        }
        final ValueAnimator widthAnimator = ValueAnimator.ofInt(view.getWidth(), toWidth);
        widthAnimator.setDuration(200);
        widthAnimator.setInterpolator(new DecelerateInterpolator());
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.getLayoutParams().width = (int) animation.getAnimatedValue();
                view.requestLayout();
            }
        });
        chevron.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),animFile));
        widthAnimator.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    loadImages();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission. i.e close image drawer
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
    public class LoadMedia extends AsyncTask<String, Integer,String>{

        @Override
        protected String doInBackground(String... strings) {
            mediaThumbsUris=new RequestMedia(getApplicationContext()).Begin();
            mediaThumbIDS = new RequestMedia(getApplicationContext()).Ids();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
        if(mediaThumbsUris!=null){
            Log.w(Globals.TAG,mediaThumbsUris.toString());
            mediaThumbs=findViewById(R.id.media_thumbs);
            adapter=new ThumbnailAdapter(getApplicationContext(),mediaThumbIDS);
            mediaThumbs.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
            mediaThumbs.setAdapter(adapter);
        }else {
            Log.w(Globals.TAG,"failed");
        }
        }
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
