package com.ahao.wanandroid.homepage

class HomePagePresenter(val view : HomeContract.View) : HomeContract.Presenter {

    init {
        view.presenter = this
    }

    override fun loadData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}