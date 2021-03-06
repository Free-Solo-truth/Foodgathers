package com.example.foodrecipes.Utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;

public class closeAnimator {
    private int mWidth;//伸展高度

    private View hideView,down;//需要展开隐藏的布局，开关控件

    private RotateAnimation animation;//旋转动画

    /**
     * 构造器(可根据自己需要修改传参)
     * @param context 上下文
     * @param hideView 需要隐藏或显示的布局view
     * @param down 按钮开关的view
     */
    public static closeAnimator newInstance(Context context, View hideView, View down, int width){
        return new closeAnimator(context,hideView,down,width);
    }

    private closeAnimator(Context context, View hideView, View down, int width){
        this.hideView = hideView;
        this.down = down;
        float mDensity = context.getResources().getDisplayMetrics().density;
        mWidth = (int) (mDensity * width + 0.5);//伸展宽度
    }

    /**
     * 开关
     */
    public void toggle() {
//        startAnimation();
        if (View.VISIBLE == hideView.getVisibility()) {
            closeAnimate(hideView);//布局隐藏
        }
    }

//    /**
//     * 开关旋转动画
//     */
//    private void startAnimation() {
//        if (View.VISIBLE == hideView.getVisibility()) {
//            animation = new RotateAnimation(180, 135, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        } else {
//            animation = new RotateAnimation(135, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        }
//        animation.setDuration(30);//设置动画持续时间
//        animation.setInterpolator(new LinearInterpolator());
//        animation.setRepeatMode(Animation.REVERSE);//设置反方向执行
//        animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
//        down.startAnimation(animation);
//    }
//
//    private void openAnim(View v) {
//        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
//        layoutParams.width = 1;
//        v.setLayoutParams(layoutParams);
//        ValueAnimator animator = createDropAnimator(v, 0, mWidth);
//        animator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                v.setVisibility(View.VISIBLE);
//            }
//        });
//        animator.start();
//    }

    private void closeAnimate(final View view) {
        ValueAnimator animator = createDropAnimator(view, mWidth, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        animator.start();
    }

    private ValueAnimator createDropAnimator(final View v, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.width = value;
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

}
