package reply_cirlce.screenable.project_everest;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.jar.Attributes;

public class MessageBubble extends LinearLayout {

    private GestureDetectorCompat mDetector;

    Context context;
    TextView tview;
    View rootView;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();


        this.rootView = this.getRootView();

    }

    public MessageBubble(final Context context, AttributeSet attrs) {

        super(context, attrs);
        this.setLongClickable(true);

        this.context = context;


        // Set the gesture detector as the double tap
        // listener.
//        mDetector.setOnDoubleTapListener(this);
        GestureDetector.OnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
//                final FrameLayout overlay = rootView.findViewById(R.id.overlay);
                final FrameLayout scrollview = rootView.findViewById(R.id.scroll_section);

                scrollview.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                            FrameLayout  overlay=rootView.findViewById(R.id.overlay);

//                            overlay.setVisibility(GONE);
                            scrollview.setOnTouchListener(null);
                            return false;
                        } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                            Log.w("POSITIONS", motionEvent.getX() + "");
                            tview.animate().x(motionEvent.getX()).y(motionEvent.getY()).setDuration(300).start();
                            return true;
                        } else {
//                            overlay.setVisibility(GONE);
                            scrollview.setOnTouchListener(null);
                        }


                        return false;
                    }
                });
//                scrollview.remove

//                overlay.setVisibility(VISIBLE);
//                overlay.removeAllViews();
//
//
//                overlay.addView(tview);
//
//                Log.w("TOUCHLISTEN", overlay.toString());
            }
        };

        final GestureDetector gestureDetector = new GestureDetector(context, listener);

//        this.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event)
//            {
//
//
////                MAKE EXACT COPY OF VIEW TO ADD TO OVERLAY FRAMELAYOUT
//
//                LayoutInflater inflater = LayoutInflater.from(context);
//                TextView copy = (TextView) inflater.inflate(R.layout.copy_template,null);
//                TextView actual_view = (TextView) v.findViewById(R.id.the_messsage);
//                copy.setText(actual_view.getText());
//                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(actual_view.getWidth(), actual_view.getHeight());
//
//                copy.setLayoutParams(layoutParams);
//                tview=copy;
//
//
//
//                return gestureDetector.onTouchEvent(event);
//            }
//        });
//
//    }

    }





//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
////        L
//        return super.onTouchEvent(event);
//    }
}
