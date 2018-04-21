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
