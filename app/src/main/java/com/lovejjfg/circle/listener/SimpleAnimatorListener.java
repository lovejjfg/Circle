package com.lovejjfg.circle.listener;

import android.animation.Animator;

/**
 * Created by Joe on 2016-06-15
 * Email: lovejjfg@gimail.com
 */
public abstract class SimpleAnimatorListener implements Animator.AnimatorListener {
    @Override
    public  void onAnimationStart(Animator animation){}

    @Override
    public  void onAnimationEnd(Animator animation){}


    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
