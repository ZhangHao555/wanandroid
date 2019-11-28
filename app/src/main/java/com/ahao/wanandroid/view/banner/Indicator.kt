package com.ahao.wanandroid.view.banner

interface Indicator {
    fun onViewSelected(position: Int)
    fun onScrolled(dx: Int,ratio : Float)
}