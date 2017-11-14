package com.kingars.kotlindemo.ui

import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.kingars.kotlindemo.App
import com.kingars.kotlindemo.R
import com.kingars.kotlindemo.extentions.slideEnter
import com.kingars.kotlindemo.extentions.slideExit
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * 封装Toolbar操作
 * Created by kinney on 2017/11/13.
 */
interface ToolbarManager {

    val toolbar: Toolbar

    /**
     * 标题的获取、设置
     */
    var toolbarTitle: String
        get() = toolbar.title.toString()
        set(value) {
            toolbar.title = value
        }

    /**
     * 初始化Toolbar
     */
    fun initToolbar() {
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_settings -> toolbar.context.startActivity<SettingsActivity>()
                else -> App.instance.toast("Unknown option")
            }
            true
        }
    }

    /**
     * 设置Toolbar的导航icon以及点击事件
     */
    fun enableHomeAsUp(up: () -> Unit) {
        toolbar.navigationIcon = createUpDrawable()
        toolbar.setNavigationOnClickListener { up() }
    }

    //使用DrawerArrowDrawable来创建一个最后状态（当箭头已经显示时）的drawable
    private fun createUpDrawable() = DrawerArrowDrawable(toolbar.context).apply { progress = 1f }

    /**
     * 根据scroll的方向来执行动画。当往下滚动时toolbar会消失 ，往上滚动toolbar会再次显示
     */
    fun attachToScroll(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0) toolbar.slideExit() else toolbar.slideEnter()
            }
        })
    }
}