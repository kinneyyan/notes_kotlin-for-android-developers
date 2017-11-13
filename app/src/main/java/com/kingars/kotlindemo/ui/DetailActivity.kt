package com.kingars.kotlindemo.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.kingars.kotlindemo.R
import com.kingars.kotlindemo.entity.Forecast
import com.kingars.kotlindemo.extentions.color
import com.kingars.kotlindemo.extentions.toDateString
import com.kingars.kotlindemo.model.commands.RequestDayForecastCommand
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.*
import org.jetbrains.anko.coroutines.experimental.bg
import java.text.DateFormat

/**
 * 天气详情
 * Created by kinney on 2017/11/13.
 */
class DetailActivity : AppCompatActivity(), ToolbarManager {

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    companion object {
        val ID = "DetailActivity:id"
        val CITY_NAME = "DetailActivity:cityName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initToolbar()
        toolbarTitle = intent.getStringExtra(CITY_NAME)
        enableHomeAsUp { onBackPressed() }

        doAsync {
            val result = RequestDayForecastCommand(intent.getLongExtra(ID, -1)).execute()
            uiThread { bindForecast(result) }
        }
    }

    private fun bindForecast(forecast: Forecast) = with(forecast) {
        Picasso.with(ctx).load(iconUrl).into(icon)
        toolbar.subtitle = date.toDateString(DateFormat.FULL)
        weatherDescription.text = description
        bindWeather(high to maxTemperature, low to minTemperature)
    }

    /**
     * 根据温度给对应TextView设置不同的颜色
     */
    private fun bindWeather(vararg views: Pair<Int, TextView>) = views.forEach {
        it.second.text = String.format("%sº", it.first)
        it.second.textColor = color(when (it.first) {
            in -50..0 -> android.R.color.holo_red_dark
            in 0..15 -> android.R.color.holo_orange_dark
            else -> android.R.color.holo_green_dark
        })
    }

}