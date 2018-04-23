package com.hsf1002.sky.wanandroid.ui.main.fragment;

import android.app.DialogFragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.app.Constants;
import com.hsf1002.sky.wanandroid.base.fragment.BaseDialogFragment;
import com.hsf1002.sky.wanandroid.contract.main.SearchContract;
import com.hsf1002.sky.wanandroid.core.DataManager;
import com.hsf1002.sky.wanandroid.core.bean.BaseResponse;
import com.hsf1002.sky.wanandroid.core.bean.main.search.TopSearchData;
import com.hsf1002.sky.wanandroid.core.bean.main.search.UsefulSiteData;
import com.hsf1002.sky.wanandroid.core.dao.HistoryData;
import com.hsf1002.sky.wanandroid.presenter.main.SearchPresenter;
import com.hsf1002.sky.wanandroid.ui.main.adapter.HistorySearchAdapter;
import com.hsf1002.sky.wanandroid.utils.CommonUtils;
import com.hsf1002.sky.wanandroid.utils.KeyBoardUtils;
import com.hsf1002.sky.wanandroid.utils.StartActivityUtils;
import com.hsf1002.sky.wanandroid.utils.StatusBarUtil;
import com.hsf1002.sky.wanandroid.widget.CircularRevealAnim;
import com.jakewharton.rxbinding2.view.RxView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hefeng on 18-4-11.
 */

public class SearchDialogFragment extends BaseDialogFragment<SearchPresenter> implements
        SearchContract.View,
        CircularRevealAnim.AnimListener,
        ViewTreeObserver.OnPreDrawListener
{
    @BindView(R.id.search_back_ib)
    ImageButton backBtn;

    @BindView(R.id.search_tint_tv)
    TextView tintTv;

    @BindView(R.id.search_edit)
    EditText searchEdit;

    @BindView(R.id.search_tv)
    TextView searchTv;

    @BindView(R.id.search_history_clear_all_tv)
    TextView clearAllHistoryTv;

    @BindView(R.id.search_scroll_view)
    NestedScrollView searchScrollView;

    @BindView(R.id.search_history_null_tint_tv)
    TextView historyNullTv;

    @BindView(R.id.search_history_rv)
    RecyclerView recyclerView;

    @BindView(R.id.top_search_flow_layout)
    TagFlowLayout topSearchTagFlow;

    @BindView(R.id.useful_sites_flow_layout)
    TagFlowLayout userfulTagFlow;

    @BindView(R.id.search_floating_action_btn)
    FloatingActionButton floatingActionButton;

    private List<TopSearchData> topSearchDataList;
    private List<UsefulSiteData> usefulSiteDataList;

    @Inject
    DataManager dataManager;

    private CircularRevealAnim circularRevealAnim;
    private HistorySearchAdapter historySearchAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle);
    }

    @Override
    public void onStart() {
        super.onStart();

        initDialog();
    }


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initEventAndData() {
        StatusBarUtil.immersive(getActivity().getWindow(), ContextCompat.getColor(getActivity(), R.color.transparent), 0.5f);
        initCircleAnimation();

        topSearchDataList = new ArrayList<>();
        usefulSiteDataList = new ArrayList<>();

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(searchEdit.getText().toString()))
                {
                    tintTv.setText(getString(R.string.search_tint));
                }
                else
                {
                    tintTv.setText("");
                }
            }
        });

        // 防手抖,仅执行1秒内的第一次事件
        RxView.clicks(searchTv)
                .throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
                .filter( o -> !TextUtils.isEmpty(searchEdit.getText().toString().trim()))
                .subscribe( o ->
                        {
                            presenter.addHistoryData(searchEdit.getText().toString().trim());
                            setHistoryTvStatus(false);
                        }
                );

        showHistoryData(dataManager.loadAllHistoryData());
        presenter.getTopSearchData();
        presenter.getUsefulSites();
    }

    @Override
    public void showTopSearchData(BaseResponse<List<TopSearchData>> topSearchDataResponse) {

        if (topSearchDataResponse == null)
        {
            showTopSearchDataFail();
            return;
        }

        topSearchDataList = topSearchDataResponse.getData();
        topSearchTagFlow.setAdapter(new TagAdapter<TopSearchData>(topSearchDataList) {
            @Override
            public View getView(FlowLayout parent, int position, TopSearchData topSearchData) {
                assert getActivity() != null;

                TextView tv = (TextView)LayoutInflater.from(getActivity()).inflate(R.layout.flow_layout_tv, parent, false);
                if (topSearchData != null)
                {
                    tv.setText(topSearchData.getName());
                }

                setItemBackground(tv);

                topSearchTagFlow.setOnTagClickListener((view, position1, parent1) ->
                {
                    presenter.addHistoryData(topSearchDataList.get(position1).getName().trim());
                    setHistoryTvStatus(false);
                    searchEdit.setText(topSearchDataList.get(position1).getName().trim());
                    searchEdit.setSelection(searchEdit.getText().length());
                    return true;
                });

                return tv;
            }
        });
    }

    private void setItemBackground(TextView tv)
    {
        tv.setBackgroundColor(CommonUtils.randomColor());
        tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
    }

    private void initCircleAnimation()
    {
        circularRevealAnim = new CircularRevealAnim();
        circularRevealAnim.setAnimListener(this);
        searchEdit.getViewTreeObserver().addOnPreDrawListener(this);
    }

    @Override
    public void onHideAnimationEnd() {
        searchEdit.setText("");
        dismiss();
    }

    @Override
    public void onShowAnimationEnd() {
        KeyBoardUtils.openKeyBoard(getActivity(), searchEdit);
    }

    private void initDialog()
    {
        Window window = getDialog().getWindow();
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        int width = (int)(metrics.widthPixels * 0.98);
        assert window != null;

        window.setLayout(width, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.TOP);
        window.setWindowAnimations(R.style.DialogEmptyAnimation);
    }

    @Override
    public void showUsefulSites(BaseResponse<List<UsefulSiteData>> usefulSitesResponse) {
        if (usefulSitesResponse == null)
        {
            showUsefulSitesDataFail();
            return;
        }

        usefulSiteDataList = usefulSitesResponse.getData();
        userfulTagFlow.setAdapter(new TagAdapter<UsefulSiteData>(usefulSiteDataList) {
            @Override
            public View getView(FlowLayout parent, int position, UsefulSiteData usefulSiteData) {
                assert  getActivity() != null;

                TextView tv = (TextView)LayoutInflater.from(getActivity()).inflate(R.layout.flow_layout_tv, parent, false);

                if (usefulSiteData != null)
                {
                    tv.setText(usefulSiteData.getName().toString());
                }

                setItemBackground(tv);

                userfulTagFlow.setOnTagClickListener((view, position1, parent1) ->
                {
                    StartActivityUtils.startArticleDetailActivity(getActivity(),
                            usefulSiteDataList.get(position1).getId(),
                            usefulSiteDataList.get(position1).getName().trim(),
                            usefulSiteDataList.get(position1).getLink().trim(),
                            false, false, true);
                    return true;
                });

                return tv;
            }
        });
    }

    @Override
    public void showHistoryData(List<HistoryData> historyDataList) {
        if (historyDataList == null || historyDataList.size() <= 0)
        {
            setHistoryTvStatus(true);
            return;
        }

        setHistoryTvStatus(false);

        Collections.reverse(historyDataList);

        historySearchAdapter = new HistorySearchAdapter(R.layout.item_search_history, historyDataList);
        historySearchAdapter.setOnItemChildClickListener(((adapter, view, position) ->
        {
            HistoryData historyData = (HistoryData)adapter.getData().get(position);
            presenter.addHistoryData(historyData.getData());
            searchEdit.setText(historyData.getData());
            searchEdit.setSelection(searchEdit.getText().length());
            setHistoryTvStatus(false);
        }));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(historySearchAdapter);
    }

    @Override
    public void showTopSearchDataFail() {
        CommonUtils.showSnackMessage(getActivity(), getString(R.string.failed_to_obtain_top_data));
    }

    @Override
    public void showUsefulSitesDataFail() {
        CommonUtils.showSnackMessage(getActivity(), getString(R.string.failed_to_obtain_useful_sites_data));
    }

    @Override
    public void judgeToTheSearchListActivity() {
        backEvent();
        StartActivityUtils.startSearchListActivity(getActivity(), searchEdit.getText().toString().trim());
    }

    @Override
    public boolean onPreDraw() {
        searchEdit.getViewTreeObserver().removeOnPreDrawListener(this);
        circularRevealAnim.show(searchEdit, rootView);
        return true;
    }

    @OnClick({R.id.search_back_ib, R.id.search_floating_action_btn, R.id.search_history_clear_all_tv})
    void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.search_back_ib:
                backEvent();
                break;
            case R.id.search_floating_action_btn:
                searchScrollView.smoothScrollTo(0, 0);
                break;
            case R.id.search_history_clear_all_tv:
                presenter.clearHistoryData();
                historySearchAdapter.replaceData(new ArrayList<>());
                setHistoryTvStatus(true);
                break;
            default:
                break;
        }
    }

    private void backEvent()
    {
        KeyBoardUtils.closeKeyBoard(getActivity(), searchEdit);
        circularRevealAnim.hide(searchEdit, rootView);
    }

    private void setHistoryTvStatus(boolean isClearAll)
    {
        clearAllHistoryTv.setEnabled(!isClearAll);

        if (isClearAll)
        {
            historyNullTv.setVisibility(View.VISIBLE);
            Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.ic_clear_all_gone);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            clearAllHistoryTv.setCompoundDrawables(drawable, null, null, null);
            clearAllHistoryTv.setTextColor(ContextCompat.getColor(getActivity(), R.color.title_black));
        }
        else
        {
            historyNullTv.setVisibility(View.GONE);
            Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.ic_clear_all);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            clearAllHistoryTv.setCompoundDrawables(drawable, null, null, null);
            clearAllHistoryTv.setTextColor(ContextCompat.getColor(getActivity(), R.color.title_black));
        }

        clearAllHistoryTv.setCompoundDrawablePadding(CommonUtils.dp2px(6));
    }
}
