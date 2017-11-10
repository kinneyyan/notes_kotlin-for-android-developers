package com.kingars.kotlindemo.model.db

import android.database.sqlite.SQLiteDatabase
import com.kingars.kotlindemo.model.datasource.ForecastDataSource
import com.kingars.kotlindemo.entity.ForecastList
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.SelectQueryBuilder
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 * 天气数据获取。数据源：db
 */
class ForecastDb(
        private val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance,
        private val dataMapper: DbDataMapper = DbDataMapper()) : ForecastDataSource {

    /**
     * 根据zipCode查询
     */
    override fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {

        val dailyRequest = "${DayForecastTable.CITY_ID} = ? AND ${DayForecastTable.DATE} >= ?"
        val dailyForecast = select(DayForecastTable.NAME)
                .whereSimple(dailyRequest, zipCode.toString(), date.toString())
                .parseList { DayForecast(HashMap(it)) }

        val city = select(CityForecastTable.NAME)
                .whereSimple("${CityForecastTable.ID} = ?", zipCode.toString())
                .parseOpt { CityForecast(HashMap(it), dailyForecast) }

        city?.let { dataMapper.convertToDomain(it) }
    }

    /**
     * 查询一天
     */
    override fun requestDayForecast(id: Long) = forecastDbHelper.use {
        val forecast = select(DayForecastTable.NAME)
                .whereSimple("${DayForecastTable.ID} = ?", id.toString())
                .parseOpt { DayForecast(HashMap(it)) }

        forecast?.let { dataMapper.convertDayToDomain(it) }
    }

    fun saveForecast(forecast: ForecastList) = forecastDbHelper.use {

        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)

        with(dataMapper.convertFromDomain(forecast)) {
            insert(CityForecastTable.NAME, *map.toVarargArray())
            dailyForecast.forEach { insert(DayForecastTable.NAME, *it.map.toVarargArray()) }
        }
    }
}

//扩展函数
//把map转换成一个vararg数组。
fun <K, V : Any> MutableMap<K, V?>.toVarargArray(): Array<out Pair<K, V>> = map({ Pair(it.key, it.value!!) }).toTypedArray()

//扩展函数
//删除表
fun SQLiteDatabase.clear(tableName: String) {
    execSQL("delete from $tableName")
}

//扩展函数
//返回一个可null的对象
fun <T : Any> SelectQueryBuilder.parseOpt(parser: (Map<String, Any?>) -> T): T? =
        parseOpt(object : MapRowParser<T> {
            override fun parseRow(columns: Map<String, Any?>): T = parser(columns)
        })

//扩展函数
//接收一个lambda函数返回一个MapRowParser的函数。解析器会调用这个lambda来创建这个对象
fun <T : Any> SelectQueryBuilder.parseList(parser: (Map<String, Any?>) -> T): List<T> =
        parseList(object : MapRowParser<T> {
            override fun parseRow(columns: Map<String, Any?>): T = parser(columns)
        })
