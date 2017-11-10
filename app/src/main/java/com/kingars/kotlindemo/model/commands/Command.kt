package com.kingars.kotlindemo.model.commands

/**
 * 获取数据的命令
 */
interface Command<out T> {
    fun execute(): T
}