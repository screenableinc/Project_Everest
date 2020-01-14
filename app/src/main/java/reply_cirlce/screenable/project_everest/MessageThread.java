package reply_cirlce.screenable.project_everest;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.text.emoji.widget.EmojiEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

//import com.vanniktech.emoji.EmojiEditText;
//import com.vanniktech.emoji.EmojiPopup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Float.NaN;

public class MessageThread extends AppCompatActivity {
    MessagesAdapter messagesAdapter;
    ArrayList<HashMap<Object,Object>> messages_list = new ArrayList<HashMap<Object, Object>>();
    RecyclerView messages;
    RecyclerView mediaThumbs;
    ArrayList<Uri> mediaThumbsUris;
    View parent;
    ThumbnailAdapter adapter;
    ArrayList<Long> mediaThumbIDS;
//    LinearLayout bottomSheetLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagethread);

//        start emoji keyboard code
//        EmojiEditText emojiEditText = findViewById(R.id.editText);
        parent = findViewById(R.id.parent);
//        parent.getLayoutParams().height=1344;


        final FrameLayout peek = findViewById(R.id.peek);
//        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
//        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View view, int i) {
//                if(i==BottomSheetBehavior.STATE_EXPANDED){
//                    peek.setVisibility(View.VISIBLE);
//                    if (ContextCompat.checkSelfPermission(MessageThread.this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                        ActivityCompat.requestPermissions(MessageThread.this,
//                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                                100);
//                    }else {
////            permission already granted load images
//                        loadImages();
//
//                    }
//                }else{
//                    peek.setVisibility(View.INVISIBLE);
//                }
//            }
//
//            @Override
//            public void onSlide(@NonNull View view, float v) {
//                adjustViews(v);
//            }
//        });

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

                populateList(HelperFunctions.removeStartandEndSpaces(message.getText().toString()),"21:14");
                message.setText("");
//                if(!message.getText().toString().equals("")){
//                    long unixTime = System.currentTimeMillis() / 1000L;
//                    SharedPreferences preferences =getSharedPreferences(Globals.SHARED_PREF_LOGIN,MODE_PRIVATE);
//                    String number = preferences.getString("number","260954806566");
////                    TODO::Add if clause to log out and delete data if number==null
//                    if(number==null){
//
//                    }
//                    String[][] req_body = {{"message_id",""},{"text",message.getText().toString()},
//                            {"time_received",""},
//                            {"status",""},{"type",""},
//                            {"sender",number},{"parent_message_id",""},
//                            {"media_duration",""},{"media_duration",""},
//                            {"media_url",""},{"media_mime_type",""},
//                            {"chat_id",""},{"has_attachments",""},{"recipient_count",""},
//                            {"recipients",""},
//                            {"time_sent",unixTime+""}};
//
//                    new SendMessage().execute(req_body);
//
//                }
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




//        my_message.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Log.w("TOUCHLISTEN", motionEvent.toString());
//                return false;
//            }
//        });

//        message thread animations


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
