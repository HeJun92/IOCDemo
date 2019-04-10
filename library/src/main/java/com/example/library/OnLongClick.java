package com.example.library;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hj on 2019/4/10.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenersetter = "setOnLongClickListener",lisenerType = View.OnLongClickListener.class,callbacklisener = "onLongClick")
public @interface OnLongClick {
    int [] value();
}
