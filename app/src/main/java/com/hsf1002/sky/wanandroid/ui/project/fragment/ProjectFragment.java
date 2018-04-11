package com.hsf1002.sky.wanandroid.ui.project.fragment;

import android.os.Bundle;

import com.hsf1002.sky.wanandroid.app.Constants;
import com.hsf1002.sky.wanandroid.base.fragment.AbstractRootFragment;
import com.hsf1002.sky.wanandroid.contract.project.ProjectContract;
import com.hsf1002.sky.wanandroid.presenter.project.ProjectPresenter;

/**
 * Created by hefeng on 18-4-10.
 */

public class ProjectFragment extends AbstractRootFragment<ProjectPresenter> implements ProjectContract.View{

    public static ProjectFragment getInstance(String param1, String param2) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }
}
