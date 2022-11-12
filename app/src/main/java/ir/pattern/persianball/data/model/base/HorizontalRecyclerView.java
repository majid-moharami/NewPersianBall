package ir.pattern.persianball.data.model.base;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


public class HorizontalRecyclerView extends RecyclerView {
    private double downX;
    private double downY;
    private boolean isScrolling = false;

    public HorizontalRecyclerView(@NonNull Context context) {
        super(context);
        setFocusable(false);
        setHasFixedSize(true);
    }

    public HorizontalRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFocusable(false);
        setHasFixedSize(true);
    }


    public class DisableInterceptTouchEvent {
        private boolean disabled;

        public DisableInterceptTouchEvent(boolean disabled) {
            this.disabled = disabled;
        }

        public boolean isDisabled() {
            return disabled;
        }
    }

    public void setScrolling(boolean scrolling) {
        isScrolling = scrolling;
    }
}
