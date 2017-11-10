package com.kingars.kotlindemo.model.db

import com.kingars.kotlindemo.entity.Forecast
import com.kingars.kotlindemo.entity.ForecastList

/**
 * 将获取到的db数据映射到domain类
 * author: YanShi
 * email: shi.yan@ele.me
 * date: 2017/11/9
 */
class DbDataMapper {

    fun convertFromDomain(forecast: ForecastList) = with(forecast) {
        val daily = dailyForecast.map { convertDayFromDomain(id, it) }
        CityForecast(id, city, country, daily)
    }

    private fun convertDayFromDomain(cityId: Long, forecast: Forecast) = with(forecast) {
        DayForecast(date, description, high, low, iconUrl, cityId)
    }

    fun convertToDomain(forecast: CityForecast) = with(forecast) {
        val daily = dailyForecast.map { convertDayToDomain(it) }
        ForecastList(_id, city, country, daily)
    }

    fun convertDayToDomain(dayForecast: DayForecast) = with(dayForecast) {
        Forecast(_id, date, description, high, low, iconUrl)
    }
}