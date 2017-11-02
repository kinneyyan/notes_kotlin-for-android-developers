package com.kingars.kotlindemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_kotlin_feature.*

/**
 * Kotlin特性概要
 */
class KotlinFeatureActivity : AppCompatActivity() {

    //一、这个数据类，它会自动生成所有属性和它们的访问器，以及一些有用的方法，比如，toString()
    data class Artist(var id: Long, var name: String, var url: String, var mbid: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_feature)

        //二、空安全
        //这里不能通过编译. Artist 不能是null
        //var notNullArtist: Artist = null;
        //此变量可以为null
        var artist: Artist? = null
        //只有在确保artist不为null时才能调用，否则会抛出异常
        //artist!!.toString()
        //elvis操作符
        main_tv.text = artist?.name ?: "empty"

        //四、函数式支持
        main_btn.setOnClickListener {
            artist = Artist(123, "kinney", "http://kingars.com", "001")
            //elvis操作符
            main_tv.text = artist.toString()
            toast("hello!")
        }
    }

    //三、扩展方法
    fun KotlinFeatureActivity.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }

    //自定义getter／setter
    class Person {
        var name: String = ""
            get() = field.toLowerCase()
            set(value) {
                field = "Name:$value"
            }
    }

    //扩展属性
    var TextView.text: CharSequence
        get() = getText()
        set(v) = setText(v)


    data class Per(val name: String, val id: Int)

    //复制一个数据类
    val p1 = Per("kinney", 123)
    val p2 = p1.copy(name = "yan", id = 456)

}
