package com.kingars.kotlindemo.domain

/**
 * App上UI使用的实体类
 * author: YanShi
 * email: shi.yan@ele.me
 * date: 2017/11/2
 */
data class ForecastList(val city: String, val country: String,
                        val dailyForecast: List<Forecast>)

data class Forecast(val date: String, val description: String, val high: Int,
                    val low: Int)