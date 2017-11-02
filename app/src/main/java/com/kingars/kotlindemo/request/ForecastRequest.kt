package com.kingars.kotlindemo.request

import com.google.gson.Gson
import com.kingars.kotlindemo.entity.ForecastResult

/**
 *
 * author: YanShi
 * email: shi.yan@ele.me
 * date: 2017/11/2
 */
public class ForecastRequest(val zipCode: String) {

    //类似java中的静态属性和方法
    companion object {
        private val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
        private val URL = "http://api.openweathermap.org/data/2.5/" +
                "forecast/daily?mode=json&units=metric&cnt=7"
        private val COMPLETE_URL = "${URL}&APPID=${APP_ID}&q="
    }

    fun execute(): ForecastResult {
        //readText是Kotlin标准库中的扩展函数
        val forecastJsonStr = java.net.URL(COMPLETE_URL + zipCode).readText()
        return Gson().fromJson(forecastJsonStr, ForecastResult::class.java)
    }
}