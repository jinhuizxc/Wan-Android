# Wan-Android
Rxjava+RxAndroid+Retrofit+OkHttp+Gson+Glide+Dagger2+GreenDao+Lottie-android+SmartRefreshLayout


### com.airbnb.lottie
Lottie是一个支持Android、iOS、React Native，并由 Adobe After Effects制作aep格式的动画，然后经由bodymovin插件转化渲染为json格式可被移动端本地识别解析的Airbnb开源库。实时呈现After Effects动画效果，让应用程序可以像使用静态图片一样轻松地使用动画。Lottie支持API 14及以上
```
implementation "com.airbnb.android:lottie:2.3.0"
```
```
<com.airbnb.lottie.LottieAnimationView
    android:id="@+id/one_animation"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginStart="@dimen/dp_36"
    android:layout_marginTop="@dimen/dp_88"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent" />
```
```
@BindView(R.id.one_animation)
LottieAnimationView oneAnimation;
```

```
oneAnimation.setAnimation("W.json");
oneAnimation.playAnimation();
....
if (oneAnimation != null)
{
    oneAnimation.cancelAnimation();
}
```

### com.scwang.smartrefresh:SmartRefreshLayout
```
implementation 'com.scwang.smartrefresh:SmartRefreshLayout:1.0.5.1'
implementation 'com.scwang.smartrefresh:SmartRefreshHeader:1.0.5.1'
```

```
<com.scwang.smartrefresh.layout.SmartRefreshLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/normal_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:visibility="invisible">
<!-- com.scwang.smartrefresh.layout.header.ClassicsHeader -->
    <com.scwang.smartrefresh.header.PhoenixHeader
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <android.support.v7.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@color/colorBackground" />
<!-- com.scwang.smartrefresh.layout.footer.ClassicsFooter -->
    <com.scwang.smartrefresh.header.TaurusHeader
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
</com.scwang.smartrefresh.layout.SmartRefreshLayout>
```
```
@BindView(R.id.normal_view)
SmartRefreshLayout refreshLayout;
```
```
private void setRefresh()
{
    refreshLayout.setOnRefreshListener( refreshLayout1 ->
    {
        presenter.autoRefresh();
        refreshLayout1.finishRefresh(1000);
    });

    refreshLayout.setOnLoadMoreListener(refreshLayout1 ->
    {
        presenter.loadMore();
        refreshLayout1.finishLoadMore(1000);
    });
}
```
