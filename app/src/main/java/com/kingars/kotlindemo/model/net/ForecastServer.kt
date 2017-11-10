package com.kingars.kotlindemo.model.net

import com.kingars.kotlindemo.model.datasource.ForecastDataSource
import com.kingars.kotlindemo.model.db.ForecastDb
import com.kingars.kotlindemo.entity.ForecastList

/**
 * 天气数据获取。数据源：网络
 */
class ForecastServer(private val dataMapper: ServerDataMapper = ServerDataMapper(),
                     private val forecastDb: ForecastDb = ForecastDb()) : ForecastDataSource {

    /**
     * 请求API获取数据，并存入db
     */
    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        val result = ForecastByZipCodeRequest(zipCode).execute()
        val converted = dataMapper.convertToDomain(zipCode, result)
        forecastDb.saveForecast(converted)
        return forecastDb.requestForecastByZipCode(zipCode, date)
    }

    override fun requestDayForecast(id: Long) = throw UnsupportedOperationException()
}