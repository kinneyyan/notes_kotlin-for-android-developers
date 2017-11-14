package com.kingars.kotlindemo.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kingars.kotlindemo.R
import com.kingars.kotlindemo.entity.Forecast
import com.kingars.kotlindemo.entity.ForecastList
import com.kingars.kotlindemo.extentions.DelegatesExt
import com.kingars.kotlindemo.extentions.toDateString
import com.kingars.kotlindemo.model.commands.RequestForecastCommand
import com.kingars.kotlindemo.model.datasource.ForecastProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_forecast.view.*
import org.jetbrains.anko.*

/**
 * 这里使用kotlin-android-extensions的特性，直接通过编译器生成的类获取xml中定义的view
 */
class MainActivity : AppCompatActivity(), ToolbarManager {

    private val zipCode: Long by DelegatesExt.preference(this, SettingsActivity.ZIP_CODE, SettingsActivity.DEFAULT_ZIP)
    //使用lazy委托，只有在第一次使用它时才会inflate
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //初始化Toolbar
        initToolbar()
        //给Toolbar添加滑动监听
        attachToScroll(forecastListRv)

        forecastListRv.layoutManager = LinearLayoutManager(this)
        forecastListRv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onResume() {
        super.onResume()
        loadForecast()
    }

    private fun loadForecast() {
        progressBar.visibility = View.VISIBLE
        //在其他线程执行
        doAsync {
            val result: ForecastList = RequestForecastCommand(zipCode).execute()
            //如果调用者activity已被销毁，则uiThread内不会执行
            uiThread {
                //设置标题
                toolbarTitle = "${result.city}(${result.country})"

                //如果这个函数只接收一个参数，那我们可以使用it引用，而不用去指定左边的参数
                //forecastList.adapter = ForecastListAdapter(result, { forecast -> toast(forecast.date) })
                forecastListRv.adapter = ForecastListAdapter(result, {
                    //调用anko的扩展函数
                    startActivity<DetailActivity>(
                            DetailActivity.ID to it.id,
                            DetailActivity.CITY_NAME to result.city)
                })
                progressBar.visibility = View.GONE
            }
        }
    }

    private class ForecastListAdapter(
            private val weekForecast: ForecastList,
            private val itemClick: (Forecast) -> Unit) :
            RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_forecast, parent, false)
            return ViewHolder(view, itemClick)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindForecast(weekForecast[position])
        }

        override fun getItemCount(): Int = weekForecast.size

        class ViewHolder(view: View, private val itemClick: (Forecast) -> Unit) : RecyclerView.ViewHolder(view) {

            fun bindForecast(forecast: Forecast) {
                with(forecast) {
                    Picasso.with(itemView.context).load(iconUrl).into(itemView.icon)
//                    itemView.dateTv.text = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(date)
                    itemView.dateTv.text = date.toDateString()
                    itemView.descriptionTv.text = description
                    itemView.maxTemperatureTv.text = "$high"
                    itemView.minTemperatureTv.text = "$low"
                    itemView.setOnClickListener { itemClick(forecast) }
                }
            }
        }

    }

}
