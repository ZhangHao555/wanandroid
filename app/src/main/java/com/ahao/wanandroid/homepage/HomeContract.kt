package com.ahao.wanandroid.homepage

import com.ahao.wanandroid.baseview.BasePresenter
import com.ahao.wanandroid.baseview.BaseView
import com.ahao.wanandroid.baseview.ListViewInterface
import com.ahao.wanandroid.baseview.ListViewPresenter
import com.ahao.wanandroid.bean.response.HomePageListResponse

interface HomeContract {

    interface View : BaseView , ListViewInterface<HomePageListResponse.Item>{
        fun notifyDataSetChanged()
    }

    interface Presenter : BasePresenter ,ListViewPresenter
}