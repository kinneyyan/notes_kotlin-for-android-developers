package com.kingars.kotlindemo.extentions

import java.text.DateFormat
import java.util.*

/**
 * Created by kinney on 2017/11/10.
 */
fun Long.toDateString(dateFormat: Int = DateFormat.MEDIUM): String {
    val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
    return df.format(this)
}