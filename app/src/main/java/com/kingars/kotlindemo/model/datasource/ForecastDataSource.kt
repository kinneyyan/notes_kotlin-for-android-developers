package com.kingars.kotlindemo.model.datasource

import com.kingars.kotlindemo.entity.Forecast
import com.kingars.kotlindemo.entity.ForecastList

interface ForecastDataSource {

    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?

    fun requestDayForecast(id: Long): Forecast?
}