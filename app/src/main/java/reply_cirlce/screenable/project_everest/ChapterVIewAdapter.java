package reply_cirlce.screenable.project_everest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

public class ChapterVIewAdapter extends RecyclerView.Adapter<ChapterVIewAdapter.ViewHolder> {
    private List<JSONObject> mData;
    private LayoutInflater mInflater;
    public ChapterVIewAdapter(List<JSONObject> data, Context context) {
        this.mData = data;

        mInflater=LayoutInflater.from(context);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.chapterstack,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        try {
            Log.w(Globals.TAG,"yeah "+mData.get(i).toString());


            String url = mData.get(i).getString("media_url_high");
            Picasso.get().load(url).into(viewHolder.imageView);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//
//        if(convertView==null){
//            convertView = mInflater.inflate(R.layout.chapterstack,parent, false);
//        }
//        ImageView imageView = (ImageView) convertView.findViewById(R.id.page);
//        try {
//            Log.w(Globals.TAG,"yeah "+mData.get(position).toString());
//
//
//            String url = mData.get(position).getString("media_url_high");
//            Picasso.get().load(url).into(imageView);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return convertView;
//    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        public ViewHolder(View itemView){
            super(itemView);
            imageView=itemView.findViewById(R.id.page);
        }

        @Override
        public void onClick(View view) {

        }
    }

}
