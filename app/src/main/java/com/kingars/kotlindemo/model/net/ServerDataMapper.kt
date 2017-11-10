package com.kingars.kotlindemo.model.net

import com.kingars.kotlindemo.entity.ForecastList
import java.util.*
import java.util.concurrent.TimeUnit
//由于我们使用了两个相同名字的类，我们可以给其中一个指定一个别名
import com.kingars.kotlindemo.entity.Forecast as ModelForecast

/**
 * 将API返回的数据转换为我们需要的数据类
 * author: YanShi
 * email: shi.yan@ele.me
 * date: 2017/11/2
 */
class ServerDataMapper {

    fun convertToDomain(zipCode: Long, forecast: ForecastResult) =
            with(forecast) {
                ForecastList(zipCode, city.name, city.country, convertForecastListToDomain(list))
            }

    private fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> {
        return list.mapIndexed { i, forecast ->
            val dt = Calendar.getInstance().timeInMillis + TimeUnit.DAYS.toMillis(i.toLong())
            convertForecastItemToDomain(forecast.copy(dt = dt))
        }
    }

    private fun convertForecastItemToDomain(forecast: Forecast) = with(forecast) {
        ModelForecast(-1, dt, weather[0].description, temp.max.toInt(), temp.min.toInt(),
                generateIconUrl(weather[0].icon))
    }

    private fun generateIconUrl(iconCode: String) = "http://openweathermap.org/img/w/$iconCode.png"
}