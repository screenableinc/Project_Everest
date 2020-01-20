package reply_cirlce.screenable.project_everest;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class myPagerAdapter extends PagerAdapter {
    Context context;
    int count;
    public myPagerAdapter(Context context,int count){
        this.context=context;
        this.count=count;
        Log.w(Globals.TAG,"instatiated");
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return true;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater1 = LayoutInflater.from(context);
        FrameLayout test = (FrameLayout) inflater1.inflate(R.layout.bg_test,null);
        container.addView(test);
        Log.w(Globals.TAG,"done");
        return test;
    }
}
