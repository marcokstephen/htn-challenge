package com.sm.htnchallengelist5.RecyclerFastScroll.calculation;
/*
    Part of a recent library used for FastScrolling in a RecyclerView (since RecycleView does not
    have the same fastscroll functionality as a ListView)

     https://github.com/danoz73/RecyclerViewFastScroller
 */

/**
 * {@link Please describe ScrollBoundsProvider!}
 */
public class VerticalScrollBoundsProvider {

    private final float mMinimumScrollY;
    private final float mMaximumScrollY;

    public VerticalScrollBoundsProvider(float minimumScrollY, float maximumScrollY) {
        mMinimumScrollY = minimumScrollY;
        mMaximumScrollY = maximumScrollY;
    }

    public float getMinimumScrollY() {
        return mMinimumScrollY;
    }

    public float getMaximumScrollY() {
        return mMaximumScrollY;
    }
}
