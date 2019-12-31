package reply_cirlce.screenable.project_everest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ChapterVIewAdapter extends BaseAdapter {
    private List<String> mData;
    private LayoutInflater mInflater;
    public ChapterVIewAdapter(List<String> data, Context context) {
        this.mData = data;
        mInflater=LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.chapterstack, parent, false);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.page);
        String url = mData.get(position);
        Picasso.get().load(url).into(imageView);

        return convertView;
    }
}
