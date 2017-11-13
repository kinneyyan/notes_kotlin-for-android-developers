package com.kingars.kotlindemo.extentions

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View

/**
 * View相关的扩展函数
 * Created by kinney on 2017/11/13.
 */

fun Context.color(res: Int): Int = ContextCompat.getColor(this, res)

fun View.slideExit() {
    if (translationY == 0f) animate().translationY(-height.toFloat())
}

fun View.slideEnter() {
    if (translationY < 0f) animate().translationY(0f)
}