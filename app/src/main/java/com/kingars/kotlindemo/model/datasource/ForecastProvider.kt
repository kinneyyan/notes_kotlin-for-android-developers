package com.kingars.kotlindemo.model.datasource

import com.kingars.kotlindemo.entity.Forecast
import com.kingars.kotlindemo.model.db.ForecastDb
import com.kingars.kotlindemo.model.net.ForecastServer
import com.kingars.kotlindemo.entity.ForecastList

class ForecastProvider(private val sources: List<ForecastDataSource> = SOURCES) {

    companion object {
        val DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
        val SOURCES = listOf(ForecastDb(), ForecastServer())
    }

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList = requestToSources {
        val res = it.requestForecastByZipCode(zipCode, todayTimeSpan())
        if (res != null && res.size >= days) res else null
    }

    fun requestForecast(id: Long): Forecast = requestToSources { it.requestDayForecast(id) }

    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS

    private fun <T : Any> requestToSources(f: (ForecastDataSource) -> T?): T = sources.firstResult { f(it) }

    //扩展函数
    //和firstOrNull类似，确保返回非null变量，区别在于前者返回null，后者抛出异常
    private inline fun <T, R : Any> Iterable<T>.firstResult(predicate: (T) -> R?): R {
        for (element in this) {
            val result = predicate(element)
            if (result != null) return result
        }
        throw NoSuchElementException("No element matching predicate was found.")
    }
}