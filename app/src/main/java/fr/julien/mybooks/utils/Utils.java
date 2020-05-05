package fr.julien.mybooks.utils;

import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class Utils {
    public static void enableScroll(View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setMovementMethod(new ScrollingMovementMethod());
        }

        view.setOnTouchListener((v, event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return false;
        });
    }
}
