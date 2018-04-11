package com.hsf1002.sky.wanandroid.di.module;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.hsf1002.sky.wanandroid.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hefeng on 18-4-11.
 */

@Module
public class FragmentModule {
    private Fragment fragment;
    private DialogFragment dialogFragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
        this.dialogFragment = null;
    }

    public FragmentModule(DialogFragment dialogFragment) {
        this.fragment = null;
        this.dialogFragment = dialogFragment;
    }

    @Provides
    @FragmentScope
    Activity provideActivity()
    {
        if (fragment == null)
        {
            return dialogFragment.getActivity();
        }
        else
        {
            return fragment.getActivity();
        }
    }
}
