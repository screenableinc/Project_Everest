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

public class Circle extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        myPagerAdapter mStackAdapter = new myPagerAdapter(getContext(),2);
        Log.w("TAG","message created");

        View rootView = inflater.inflate(R.layout.circle, container, false);
        FlippableStackView stack = (FlippableStackView) rootView.findViewById(R.id.stack);
        stack.initStack(1);
        stack.setAdapter(mStackAdapter);


        return rootView;

    }
}
