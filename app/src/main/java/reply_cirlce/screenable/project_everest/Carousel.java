package reply_cirlce.screenable.project_everest;

import android.content.Context;

import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import static androidx.emoji.text.EmojiCompat.init;

public class Carousel extends FrameLayout implements GestureDetector.OnGestureListener {
    int child_count;
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private static int TOTAL_WIDTH=0;
    private static int CURR_SLIDE=0;


    public Carousel(Context context, @Nullable AttributeSet attrs){
        super(context);
        init(null);
    }


    //    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        setMeasuredDimension(500,300);
//    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        child_count++;
//        TOTAL_WIDTH=child_count*this.getLayoutParams().width;
        super.addView(child, index, params);

    }


    @Override
    public void onLongPress(MotionEvent motionEvent) {
//        mute and share option
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;
        Log.w("CIRCLE_CC","flung");
        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                    result = true;
                }
            }
            else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    onSwipeBottom();
                } else {
                    onSwipeTop();
                }
                result = true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
//        return false;

    }
    public void onSwipeRight() {
     Log.w("CIRCLE_CC","swiped right");
    }


    public void onSwipeLeft() {
        Log.w("CIRCLE_CC","swiped left");
    }

    public void onSwipeTop() {
        Log.w("CIRCLE_CC","swiped up");
    }

    public void onSwipeBottom() {
        Log.w("CIRCLE_CC","swiped bottom");
    }



    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.w("CIRCLE_CC",motionEvent.toString());
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        return super.onGenericMotionEvent(event);
    }
}
