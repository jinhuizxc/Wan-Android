package com.hsf1002.sky.wanandroid.ui.main.activity;

import android.content.Intent;

import com.airbnb.lottie.LottieAnimationView;
import com.hsf1002.sky.wanandroid.R;
import com.hsf1002.sky.wanandroid.app.GeeksApp;
import com.hsf1002.sky.wanandroid.base.activity.BaseActivity;
import com.hsf1002.sky.wanandroid.contract.main.SplashContract;
import com.hsf1002.sky.wanandroid.presenter.main.SplashPresenter;
import com.hsf1002.sky.wanandroid.utils.StatusBarUtil;

import butterknife.BindView;

/**
 * Created by hefeng on 18-4-9.
 */

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {

    @BindView(R.id.one_animation)
    LottieAnimationView oneAnimation;

    @BindView(R.id.two_animation)
    LottieAnimationView twoAnimation;

    @BindView(R.id.three_animation)
    LottieAnimationView threeAnimation;

    @BindView(R.id.four_animation)
    LottieAnimationView fourAnimation;

    @BindView(R.id.five_animation)
    LottieAnimationView fiveAnimation;

    @BindView(R.id.six_animation)
    LottieAnimationView sixAnimation;

    @BindView(R.id.seven_animation)
    LottieAnimationView sevenAnimation;

    @BindView(R.id.eight_animation)
    LottieAnimationView eightAnimation;

    @BindView(R.id.nine_animation)
    LottieAnimationView nineAnimation;

    @BindView(R.id.ten_animation)
    LottieAnimationView tenAnimation;

    @Override
    protected void onDestroy() {
        cancelAnimation();
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void jumpToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        if (GeeksApp.sIsFirstRun)
        {
            jumpToMain();
            return;
        }
        else
        {
            StatusBarUtil.immersive(this);

            GeeksApp.sIsFirstRun = true;

            oneAnimation.setAnimation("W.json");
            oneAnimation.playAnimation();

            twoAnimation.setAnimation("A.json");
            twoAnimation.playAnimation();

            threeAnimation.setAnimation("N.json");
            threeAnimation.playAnimation();

            fourAnimation.setAnimation("A.json");
            fourAnimation.playAnimation();

            fiveAnimation.setAnimation("N.json");
            fiveAnimation.playAnimation();

            sixAnimation.setAnimation("D.json");
            sixAnimation.playAnimation();

            sevenAnimation.setAnimation("R.json");
            sevenAnimation.playAnimation();

            eightAnimation.setAnimation("O.json");
            eightAnimation.playAnimation();

            nineAnimation.setAnimation("I.json");
            nineAnimation.playAnimation();

            tenAnimation.setAnimation("D.json");
            tenAnimation.playAnimation();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void showLoginView() {

    }

    private void cancelAnimation()
    {
        if (oneAnimation != null)
        {
            oneAnimation.cancelAnimation();
        }
        if (twoAnimation != null)
        {
            twoAnimation.cancelAnimation();
        }
        if (threeAnimation != null)
        {
            threeAnimation.cancelAnimation();
        }
        if (fourAnimation != null)
        {
            fourAnimation.cancelAnimation();
        }
        if (fiveAnimation != null)
        {
            fiveAnimation.cancelAnimation();
        }
        if (sixAnimation != null)
        {
            sixAnimation.cancelAnimation();
        }
        if (sevenAnimation != null)
        {
            sevenAnimation.cancelAnimation();
        }
        if (eightAnimation != null)
        {
            eightAnimation.cancelAnimation();
        }
        if (nineAnimation != null)
        {
            nineAnimation.cancelAnimation();
        }
        if (tenAnimation != null)
        {
            tenAnimation.cancelAnimation();
        }
    }
}
