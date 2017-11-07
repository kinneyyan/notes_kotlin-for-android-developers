package com.kingars.kotlindemo

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 自定义委托
 *
 * author: YanShi
 * email: shi.yan@ele.me
 * date: 2017/11/6
 */
object DelegatesExt {

    /**
     * 这个委托可以作用在任何非null的类型。它接收任何类型的引用，然后像getter和setter那样使用T。现在我们需要去实现这些函数。
     * Getter函数 如果已经被初始化，则会返回一个值，否则会抛异常。
     * Setter函数 如果仍然是null，则赋值，否则会抛异常。
     */
    fun <T : Any> notNullSingleValue(): ReadWriteProperty<Any?, T> = NotNullSingleValueVar()

    private class NotNullSingleValueVar<T> : ReadWriteProperty<Any?, T> {

        private var value: T? = null

        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return value ?: throw IllegalStateException("value not initialized")
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            this.value = if (this.value == null) value
            else throw IllegalStateException("value already initialized")
        }

    }

}
