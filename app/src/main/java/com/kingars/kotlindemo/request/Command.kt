package com.kingars.kotlindemo.request

import com.kingars.kotlindemo.ForecastDataMapper
import com.kingars.kotlindemo.domain.ForecastList

/**
 *
 * author: YanShi
 * email: shi.yan@ele.me
 * date: 2017/11/2
 */
interface Command<T> {
    fun execute(): T
}

class RequestForecastCommand(val zipCode: String) : Command<ForecastList> {
    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(forecastRequest.execute())
    }
}