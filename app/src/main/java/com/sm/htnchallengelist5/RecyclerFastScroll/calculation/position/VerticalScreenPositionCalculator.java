package com.sm.htnchallengelist5.RecyclerFastScroll.calculation.position;
/*
    Part of a recent library used for FastScrolling in a RecyclerView (since RecycleView does not
    have the same fastscroll functionality as a ListView)

     https://github.com/danoz73/RecyclerViewFastScroller
 */

import com.sm.htnchallengelist5.RecyclerFastScroll.calculation.VerticalScrollBoundsProvider;

/**
 * Calculates the correct vertical Y position for a view based on scroll progress and given bounds
 */
public class VerticalScreenPositionCalculator {

    private final VerticalScrollBoundsProvider mVerticalScrollBoundsProvider;

    public VerticalScreenPositionCalculator(VerticalScrollBoundsProvider scrollBoundsProvider) {
        mVerticalScrollBoundsProvider = scrollBoundsProvider;
    }

    public float getYPositionFromScrollProgress(float scrollProgress) {
        return Math.max(
                mVerticalScrollBoundsProvider.getMinimumScrollY(),
                Math.min(
                        scrollProgress * mVerticalScrollBoundsProvider.getMaximumScrollY(),
                        mVerticalScrollBoundsProvider.getMaximumScrollY()
                )
        );
    }

}
