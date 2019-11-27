package com.ahao.wanandroid.homepage

import com.ahao.wanandroid.baseview.BasePresenter
import com.ahao.wanandroid.baseview.BaseView

interface HomeContract {

    interface View:BaseView<Presenter>{
        fun showData()
    }

    interface Presenter : BasePresenter{
        fun loadData()
    }
}