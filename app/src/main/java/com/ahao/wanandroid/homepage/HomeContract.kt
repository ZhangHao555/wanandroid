package com.ahao.wanandroid.homepage

import com.ahao.wanandroid.baseview.BasePresenter
import com.ahao.wanandroid.baseview.BaseView
import com.ahao.wanandroid.bean.response.MainPageListResponse

interface HomeContract {

    interface View : BaseView<Presenter> {
        fun showData(listResponse: MainPageListResponse)
        fun showMore(listResponse: MainPageListResponse)
        fun showErrorView(message: String)
        fun showToast(message: String)
    }

    interface Presenter : BasePresenter {
        fun loadHomeListData()
        fun loadMoreHomeListData()
    }
}