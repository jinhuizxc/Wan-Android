package com.hsf1002.sky.wanandroid.widget.behavior;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;

/**
 * Created by hefeng on 18-4-8.
 */

public class AnimatorUtil {
    private static LinearOutSlowInInterpolator FAST_OUT_SLOW_IN_INTERPOLATOR = new LinearOutSlowInInterpolator();

    public static void translateShow(View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener)
    {
        view.setVisibility(View.VISIBLE);

        ViewCompat.animate(view)
                .translationY(0)
                .setDuration(400)
                .setListener(viewPropertyAnimatorListener)
                .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                .start();
    }

    public static void translateHide(View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener)
    {
        view.setVisibility(View.VISIBLE);

        ViewCompat.animate(view)
                .translationY(350)
                .setDuration(400)
                .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                .setListener(viewPropertyAnimatorListener)
                .start();
    }

}
