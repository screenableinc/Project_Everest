package reply_cirlce.screenable.project_everest;

import android.content.Context;
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

public class ChapterVIewAdapter extends BaseAdapter {
    private List<JSONObject> mData;
    private LayoutInflater mInflater;
    public ChapterVIewAdapter(List<JSONObject> data, Context context) {
        this.mData = data;

        mInflater=LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position).toString();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView = mInflater.inflate(R.layout.chapterstack,parent, false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.page);
        try {
            Log.w(Globals.TAG,"yeah "+mData.get(position).toString());


            String url = mData.get(position).getString("media_url_high");
            Picasso.get().load(url).into(imageView);
        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }


}
