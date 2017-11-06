package com.kingars.kotlindemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kingars.kotlindemo.domain.Forecast
import com.kingars.kotlindemo.domain.ForecastList
import com.kingars.kotlindemo.request.RequestForecastCommand
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_forecast.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

/**
 * 使用Kotlin实现RecyclerView
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val forecastList = findViewById<RecyclerView>(R.id.forecast_list)
        forecastListRv.layoutManager = LinearLayoutManager(this)
        forecastListRv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

//        val requestBtn = findViewById<Button>(R.id.request_btn)
//        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        requestBtn.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            //在其他线程执行
            doAsync {
                val result = RequestForecastCommand("94043").execute()
                //如果调用者activity已被销毁，则uiThread内不会执行
                uiThread {
                    //forecastList.adapter = ForecastListAdapter(result, { forecast -> toast(forecast.date) })
                    //如果这个函数只接收一个参数，那我们可以使用it引用，而不用去指定左边的参数
                    forecastListRv.adapter = ForecastListAdapter(result, { toast(it.date) })
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    class ForecastListAdapter(private val weekForecast: ForecastList, private val itemClick: (Forecast) -> Unit) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_forecast, parent, false)
            return ViewHolder(view, itemClick)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindForecast(weekForecast[position])
        }

        override fun getItemCount(): Int = weekForecast.size()

        class ViewHolder(view: View, private val itemClick: (Forecast) -> Unit) : RecyclerView.ViewHolder(view) {
//            private val iconView: ImageView
//            private val dateView: TextView
//            private val descriptionView: TextView
//            private val maxTemperatureView: TextView
//            private val minTemperatureView: TextView

//            init {
//                iconView = view.find(R.id.icon)
//                dateView = view.find(R.id.date)
//                descriptionView = view.find(R.id.description)
//                maxTemperatureView = view.find(R.id.maxTemperature)
//                minTemperatureView = view.find(R.id.minTemperature)
//            }

            fun bindForecast(forecast: Forecast) {
                with(forecast) {
                    //                    Picasso.with(itemView.context).load(iconUrl).into(iconView)
//                    dateView.text = date
//                    descriptionView.text = description
//                    maxTemperatureView.text = "$high"
//                    minTemperatureView.text = "$low"
                    Picasso.with(itemView.context).load(iconUrl).into(itemView.icon)
                    itemView.dateTv.text = date
                    itemView.descriptionTv.text = description
                    itemView.maxTemperatureTv.text = "$high"
                    itemView.minTemperatureTv.text = "$low"
                    itemView.setOnClickListener { itemClick(forecast) }
                }
            }
        }

    }

}
