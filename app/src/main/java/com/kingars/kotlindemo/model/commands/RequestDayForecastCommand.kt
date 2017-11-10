package com.kingars.kotlindemo.model.commands

import com.kingars.kotlindemo.entity.Forecast
import com.kingars.kotlindemo.model.datasource.ForecastProvider

class RequestDayForecastCommand(
        val id: Long,
        private val forecastProvider: ForecastProvider = ForecastProvider()) :
        Command<Forecast> {

    override fun execute() = forecastProvider.requestForecast(id)
}