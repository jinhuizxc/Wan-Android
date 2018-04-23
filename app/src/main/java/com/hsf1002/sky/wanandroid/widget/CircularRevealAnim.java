package com.hsf1002.sky.wanandroid.widget;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by hefeng on 18-4-11.
 */

public class CircularRevealAnim {
    private static final long DURATION = 200;
    private  AnimListener listener;

    public interface AnimListener
    {
        void onHideAnimationEnd();
        void onShowAnimationEnd();
    }

    @SuppressLint("NewApi")
    private void actionOtherVisible(boolean isShow, View striggerView, View animView)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {
            if (isShow)
            {
                animView.setVisibility(View.VISIBLE);
                if (listener != null)
                {
                    listener.onShowAnimationEnd();
                }
            }
            else
            {
                animView.setVisibility(View.GONE);
                if (listener != null)
                {
                    listener.onHideAnimationEnd();
                }
            }

            return;
        }

        int[] tvLocation = {0, 0};
        striggerView.getLocationInWindow(tvLocation);
        int tvX = (int)(tvLocation[0] + animView.getWidth() * 0.8);
        int tvY = tvLocation[1] + striggerView.getHeight() / 2;

        int[] avLocation = {0, 0};
        animView.getLocationInWindow(avLocation);
        int avX = avLocation[0] + animView.getWidth() / 2;
        int avY = avLocation[1] + animView.getHeight() / 2;

        int rippleW;

        if (tvX < avX)
        {
            rippleW = animView.getWidth() - tvX;
        }
        else
        {
            rippleW = tvX - avLocation[0];
        }

        int rippleH;

        if (tvY < avY)
        {
            rippleH = animView.getHeight() - tvY;
        }
        else
        {
            rippleH = tvY - avLocation[1];
        }

        float maxRadius = (float)Math.sqrt((double)(rippleW * rippleW + rippleH * rippleH));
        float startRadius;
        float endRadius;

        if (isShow)
        {
            startRadius = 0f;
            endRadius = maxRadius;
        }
        else
        {
            startRadius = maxRadius;
            endRadius = 0f;
        }

        Animator anim = ViewAnimationUtils.createCircularReveal(animView, tvX, tvY, startRadius, endRadius);
        animView.setVisibility(View.VISIBLE);
        anim.setDuration(DURATION);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isShow)
                {
                    animView.setVisibility(View.VISIBLE);
                    if (listener != null)
                    {
                        listener.onShowAnimationEnd();
                    }
                    else
                    {
                        animView.setVisibility(View.GONE);
                        listener.onHideAnimationEnd();
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.start();
    }

    public void show(View triggerView, View showView)
    {
        actionOtherVisible(true, triggerView, showView);
    }

    public void hide(View triggerView, View hideView)
    {
        actionOtherVisible(false, triggerView, hideView);
    }

    public void setAnimListener(AnimListener listener)
    {
        this.listener = listener;
    }
}
