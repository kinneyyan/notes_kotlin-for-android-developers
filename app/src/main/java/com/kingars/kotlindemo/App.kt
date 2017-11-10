package com.kingars.kotlindemo

import android.app.Application
import com.kingars.kotlindemo.extentions.DelegatesExt

/**
 * author: YanShi
 * email: shi.yan@ele.me
 * date: 2017/11/6
 */
class App : Application() {

    companion object {
        //使用notNull委托。它会含有一个可null的变量并会在我们设置这个属性的时候分配一个真实的值。如果这个值在被获取之前没有被分配，它就会抛出一个异常
        //var instance: App by Delegates.notNull()
        //使用自定义委托。只会赋值一次
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
