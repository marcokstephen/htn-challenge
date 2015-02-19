package com.sm.htnchallengelist5.RecyclerFastScroll.calculation.progress;
/*
    Part of a recent library used for FastScrolling in a RecyclerView (since RecycleView does not
    have the same fastscroll functionality as a ListView)

     https://github.com/danoz73/RecyclerViewFastScroller
 */

import android.support.v7.widget.RecyclerView;

/**
 * Assists in calculating the amount of scroll progress for a {@link android.support.v7.widget.RecyclerView}
 */
public interface ScrollProgressCalculator {

    /**
     * Calculates the scroll progress of a provided RecyclerView
     * @param recyclerView for which to calculate scroll progress
     * @return fraction from [0 to 1] representing the scroll progress
     */
    public float calculateScrollProgress(RecyclerView recyclerView);

}
