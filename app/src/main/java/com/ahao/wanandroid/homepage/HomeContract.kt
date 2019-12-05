package com.ahao.wanandroid.homepage

import com.ahao.wanandroid.baseview.BasePresenter
import com.ahao.wanandroid.baseview.BaseView
import com.ahao.wanandroid.bean.response.MainPageListResponse

interface HomeContract {

    interface View : BaseView<Presenter> {
        fun showData(listResponse: MainPageListResponse?)
        fun showErrorView(message: String)
    }

    interface Presenter : BasePresenter {
        fun loadBannerData(index: Int)
    }
}