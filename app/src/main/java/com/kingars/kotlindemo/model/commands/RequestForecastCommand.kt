package com.kingars.kotlindemo.model.commands

import com.kingars.kotlindemo.entity.ForecastList
import com.kingars.kotlindemo.model.datasource.ForecastProvider

/**
 * 根据zipCode获取天气的命令
 * Created by kinney on 2017/11/10.
 */
class RequestForecastCommand(
        private val zipCode: Long,
        private val forecastProvider: ForecastProvider = ForecastProvider()) :
        Command<ForecastList> {

    companion object {
        val DAYS = 7
    }

    override fun execute() = forecastProvider.requestByZipCode(zipCode, DAYS)
}