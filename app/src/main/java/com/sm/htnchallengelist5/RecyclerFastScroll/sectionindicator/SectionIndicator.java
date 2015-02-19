package com.sm.htnchallengelist5.RecyclerFastScroll.sectionindicator;

/*
    Part of a recent library used for FastScrolling in a RecyclerView (since RecycleView does not
    have the same fastscroll functionality as a ListView)

     https://github.com/danoz73/RecyclerViewFastScroller
 */

/**
 * An indicator for which section a {@link android.support.v7.widget.RecyclerView} is currently in. This is for RecyclerViews whose adapters
 * implement the {@link android.widget.SectionIndexer} interface.
 */
public interface SectionIndicator<T> {

    /**
     * Sets the progress of the indicator
     * @param progress fraction from [0 to 1] representing progress scrolled through a RecyclerView
     */
    public void setProgress(float progress);

    /**
     * Allows the setting of section types in the indicator. The indicator should appropriately handle the section type
     * @param section the current section to which the list is scrolled
     */
    public void setSection(T section);

    /**
     * Method for animating the alpha of the indicator
     * @param targetAlpha alpha to animate towards
     */
    public void animateAlpha(float targetAlpha);
}
