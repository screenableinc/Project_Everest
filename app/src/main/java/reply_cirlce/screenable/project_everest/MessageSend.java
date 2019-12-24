package reply_cirlce.screenable.project_everest;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MessageSend {
    Context context;
    public MessageSend(Context context){
        this.context=context;
    }
    public void sendText(String message_id, String text, int status, Timestamp time_received, int type, String sender, String parent_message_id
            , String media_duration, String media_url, String media_mime_type, String chat_id, boolean has_attachments
            , int recipient_count, JSONArray recipients, Timestamp time_sent) throws Exception{
        JSONObject body  = new JSONObject();
        body.put("message_id","");
        body.put("text",text);
        body.put("status",status);
        body.put("time_received",time_received);
        body.put("type",type);body.put("sender",sender);body.put("parent_message_id",parent_message_id);
        body.put("media_duration",media_duration);body.put("media_url",media_url);body.put("media_mime_type",media_mime_type);
        body.put("chat_id",chat_id);body.put("has_attachments",has_attachments);
        body.put("recipient_count",recipient_count);body.put("recipients",recipients);body.put("time_sent",time_sent);

        String res = new AccessApi().sendPost(Globals.url_send_message,body.toString());
        Log.w("EverestLog", res);



    }
}
