package com.hsf1002.sky.wanandroid.ui.project.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hsf1002.sky.wanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hefeng on 18-4-17.
 */

public class ProjectListViewHolder extends BaseViewHolder{
    @BindView(R.id.item_project_list_iv)
    ImageView imageView;

    @BindView(R.id.item_project_list_title_tv)
    TextView title;

    @BindView(R.id.item_project_list_content_tv)
    TextView content;

    @BindView(R.id.item_project_list_time_tv)
    TextView time;

    @BindView(R.id.item_project_list_author_tv)
    TextView author;

    @BindView(R.id.item_project_list_install_tv)
    TextView install;

    public ProjectListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
