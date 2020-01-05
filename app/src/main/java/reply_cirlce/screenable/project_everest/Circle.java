package reply_cirlce.screenable.project_everest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import android.widget.ArrayAdapter;
import android.widget.FrameLayout;


import com.bartoszlipinski.flippablestackview.FlippableStackView;

import java.util.ArrayList;
import java.util.List;

import link.fls.swipestack.SwipeStack;


public class Circle extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();


        View rootView = inflater.inflate(R.layout.circle, container, false);
        SwipeStack swipeStack = (SwipeStack) rootView.findViewById(R.id.swipeStack);
        List<String> mData=new ArrayList<>();
        mData.add("http://www.screenableinc.com/everest/100.jpg");
        mData.add("http://www.screenableinc.com/everest/123.jpg");
        mData.add("http://www.screenableinc.com/everest/427.jpg");
        mData.add("http://www.screenableinc.com/everest/399.jpg");

        swipeStack.setAdapter(new ChapterVIewAdapter(mData,getActivity()));



        return rootView;

    }
}
