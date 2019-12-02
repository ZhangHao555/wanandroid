package com.ahao.wanandroid.homepage

class HomePagePresenter(val view : HomeContract.View) : HomeContract.Presenter {

    init {
        view.presenter = this
    }

    override suspend fun loadBannerData() {

    }

}