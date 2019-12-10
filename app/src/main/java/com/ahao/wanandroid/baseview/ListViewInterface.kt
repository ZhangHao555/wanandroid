package com.ahao.wanandroid.baseview

interface ListViewInterface<T> {
    fun showData(listData: List<T>,total : Int)
    fun showMore(listData: List<T>,total : Int)
    fun showErrorView(message: String)
    fun showToast(message: String)
    fun finishLoadView()
}