package com.blakelafleur.pyli.Fragments;

import android.os.SystemClock;
import android.view.View;

import com.larswerkman.holocolorpicker.ColorPicker;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * A Debounced OnColorChangedListener
 * Rejects calls that are too close together in time.
 * This class is safe to use as an OnColorChangedListener for multiple views, and will debounce each one separately.
 */
public abstract class DebouncedOnColorChangedListener implements ColorPicker.OnColorChangedListener {

    private final long minimumInterval;
    private Map<View, Long> lastClickMap;
    private View mView;

    /**
     * The one and only constructor
     *
     * @param minimumIntervalMsec The minimum allowed time between clicks - any click sooner than this after a previous click will be rejected
     */
    public DebouncedOnColorChangedListener(View view, long minimumIntervalMsec) {
        this.mView = view;
        this.minimumInterval = minimumIntervalMsec;
        this.lastClickMap = new WeakHashMap<View, Long>();
    }

    /**
     * Implement this in your subclass instead of onColorChanged
     *
     * @param color The color int of the current color
     */
    public abstract void onDebouncedClick(int color);

    @Override
    public void onColorChanged(int color) {
        Long previousClickTimestamp = lastClickMap.get(this.mView);
        long currentTimestamp = SystemClock.uptimeMillis();

        lastClickMap.put(this.mView, currentTimestamp);
        if (previousClickTimestamp == null || (currentTimestamp - previousClickTimestamp > minimumInterval)) {
            onDebouncedClick(color);
        }
    }
}