package reply_cirlce.screenable.project_everest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

public class ThumbnailAdapter extends RecyclerView.Adapter<ThumbnailAdapter.ViewHolder> {
    Context context;
    LayoutInflater mInflater;
    ArrayList<Long> ids;
    public ThumbnailAdapter(Context context, ArrayList<Long> ids){
        this.context=context;
        this.ids=ids;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(),ids.get(i), MediaStore.Images.Thumbnails.MINI_KIND, null);
        viewHolder.imageView.setImageBitmap(bitmap);
        //        Bitmap thumbnail = MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(), Long.valueOf(uris.get(i).getLastPathSegment()).longValue(),1,null);
//        String path =  "/storage/emulated/0/WhatsApp Business/Media/WhatsApp Business Images/IMG-20190517-WA0000.jpg";
//        File file = new File(new URI(path));
//        File file = new File(Uri.parse("ssss"));
//        Picasso picasso=Picasso.get();
//        picasso.setLoggingEnabled(true);
//        Picasso.get().load(bitmap).resize(200,400).into(viewHolder.imageView);
        Log.w(Globals.TAG,"started");
//        viewHolder.imageView.setImageBitmap(thumbnail);
    }
//    public Bitmap loadThumbnail(Uri)

    @Override
    public int getItemCount() {
        return ids.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.thumbnailview,viewGroup,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        public ViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.thumbnail);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
