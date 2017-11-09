package com.kingars.kotlindemo.db

import com.kingars.kotlindemo.domain.Forecast
import com.kingars.kotlindemo.domain.ForecastList

/**
 * 将获取到的db数据映射到domain类
 * author: YanShi
 * email: shi.yan@ele.me
 * date: 2017/11/9
 */
class DbDataMapper {

    /**
     * domain类 -> db数据
     */
    fun convertFromDomain(forecast: ForecastList): CityForecast =
            with(forecast) {
                val daily = dailyForecast.map { convertDayFromDomain(city.toLong(), it) }
                CityForecast(city.toLong(), city, country, daily)
            }

    private fun convertDayFromDomain(cityId: Long, forecast: Forecast): DayForecast =
            with(forecast) {
                DayForecast(date.toLong(), description, high, low, iconUrl, cityId)
            }

    /**
     * db数据 -> domain类
     */
    fun convertToDomain(forecast: CityForecast): ForecastList =
            with(forecast) {
                val daily = dailyForecast.map { convertDayToDomain(it) }
                ForecastList(city, country, daily)
            }

    private fun convertDayToDomain(dayForecast: DayForecast): Forecast =
            with(dayForecast) {
                Forecast(date.toString(), description, high, low, iconUrl)
            }
}