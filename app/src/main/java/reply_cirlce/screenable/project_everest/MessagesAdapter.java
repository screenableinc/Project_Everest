package reply_cirlce.screenable.project_everest;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {
    private List<HashMap<Object,Object>> mData;
    private LayoutInflater mInflater;

    public MessagesAdapter(List<HashMap<Object,Object>> data, Context context) {
        this.mData = data;

        mInflater = LayoutInflater.from(context);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=mInflater.inflate(R.layout.othermessage,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String message = mData.get(i).get("message").toString();
        String time = mData.get(i).get("time").toString();
//        viewHolder.the_message.setAlpha(0.1f);
        Log.w(Globals.TAG,i+"here");

        viewHolder.the_message.setText(message);
//        animate last added message
        if(i==mData.size()-1) {
            animate(viewHolder.the_message);
        }
        viewHolder.time.setText(time);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView the_message;
        TextView time;
        public ViewHolder(View itemView){
            super(itemView);
            the_message=itemView.findViewById(R.id.the_messsage);
            time=itemView.findViewById(R.id.time);

        }

        @Override
        public void onClick(View view) {

        }
    }
    public void animate(View view){
        AlphaAnimation animation1 = new AlphaAnimation(0f,1f);
        animation1.setDuration(250);
        animation1.setStartOffset(250);
        animation1.setFillAfter(true);
        view.startAnimation(animation1);
    }
}