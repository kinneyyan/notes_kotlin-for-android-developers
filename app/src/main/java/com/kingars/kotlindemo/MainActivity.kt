package com.kingars.kotlindemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.kingars.kotlindemo.domain.ForecastList
import com.kingars.kotlindemo.request.RequestForecastCommand
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * 使用Kotlin实现RecyclerView
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val forecastList = findViewById<RecyclerView>(R.id.forecast_list)
        forecastList.layoutManager = LinearLayoutManager(this)
        forecastList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
//        forecastList.adapter = ForecastListAdapter(items)

        val requestBtn = findViewById<Button>(R.id.request_btn)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        requestBtn.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            //在其他线程执行
            doAsync {
                val result = RequestForecastCommand("94043").execute()
                //如果调用者activity已被销毁，则uiThread内不会执行
                uiThread {
                    forecastList.adapter = ForecastListAdapter(result)
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    class ForecastListAdapter(private val weekForecast: ForecastList) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val textView = TextView(parent?.context)
            textView.setPadding(20, 20, 20, 20)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
            return ViewHolder(textView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            with(weekForecast.dailyForecast[position]) {
                holder.textView.text = "$date - $description - $high/$low"
            }
        }
//        holder.textView.text = items[position]

        override fun getItemCount(): Int = weekForecast.dailyForecast.size

        class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
    }

//    private val items = listOf(
//            "Mon 6 / 23 - Sunny - 31 / 17",
//            "Tue 6/24 - Foggy - 21/8",
//            "Wed 6/25 - Cloudy - 22/17",
//            "Thurs 6/26 - Rainy - 18/11",
//            "Fri 6/27 - Foggy - 21/10",
//            "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
//            "Sun 6/29 - Sunny - 20/7"
//    )
}
