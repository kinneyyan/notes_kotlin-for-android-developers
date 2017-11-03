package com.kingars.kotlindemo

import com.kingars.kotlindemo.domain.ForecastList
import com.kingars.kotlindemo.entity.Forecast
import com.kingars.kotlindemo.entity.ForecastResult
import java.text.DateFormat
import java.util.*
//由于我们使用了两个相同名字的类，我们可以给其中一个指定一个别名
import com.kingars.kotlindemo.domain.Forecast as ModelForecast

/**
 *
 * author: YanShi
 * email: shi.yan@ele.me
 * date: 2017/11/2
 */
public class ForecastDataMapper {

    fun convertFromDataModel(forecast: ForecastResult): ForecastList {
        return ForecastList(forecast.city.name, forecast.city.country,
                convertForecastListToDomain(forecast.list))
    }

    private fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> {
        //循环这个集合并且返回一个转换后的新的List
        //Kotlin在List中提供了很多不错的函数操作符
        return list.map { convertForecastItemToDomain(it) }
    }

    private fun convertForecastItemToDomain(forecast: Forecast): ModelForecast {
        return ModelForecast(convertDate(forecast.dt),
                forecast.weather[0].description, forecast.temp.max.toInt(),
                forecast.temp.min.toInt(), generateIconUrl(forecast.weather[0].icon))
    }

    private fun generateIconUrl(iconCode: String): String = "http://openweathermap.org/img/w/$iconCode.png"

    private fun convertDate(date: Long): String {
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return df.format(date * 1000)
    }
}