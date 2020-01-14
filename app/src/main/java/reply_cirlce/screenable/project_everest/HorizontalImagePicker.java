package reply_cirlce.screenable.project_everest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;

public class HorizontalImagePicker{
    View controller;
    View controlled;
    public HorizontalImagePicker(View controller, View controlled){
        this.controller=controller;
        this.controlled=controlled;
        setListeners();
    }
    public void setListeners(){
        controller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w(Globals.TAG,"cliccked");
            }
        });
        controller.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                Log.w(Globals.TAG,dragEvent.toString());
                return false;
            }
        });
    }







}
