package com.sm.htnchallengelist5.RecyclerFastScroll.calculation.progress;
/*
    Part of a recent library used for FastScrolling in a RecyclerView (since RecycleView does not
    have the same fastscroll functionality as a ListView)

     https://github.com/danoz73/RecyclerViewFastScroller
 */

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;


/**
 * Assists in calculating the amount of scroll progress for a {@link android.support.v7.widget.RecyclerView} based on a {@link android.view.MotionEvent}
 */
public interface TouchableScrollProgressCalculator extends ScrollProgressCalculator {

    /**
     * Calculates the scroll progress of a RecyclerView based on a motion event from a scroller
     * @param event for which to calculate scroll progress
     * @return fraction from [0 to 1] representing the scroll progress
     */
    public float calculateScrollProgress(MotionEvent event);

}
