package com.ahao.wanandroid.homepage

import com.ahao.wanandroid.bean.response.MainPageListResponse
import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePagePresenter(val view: HomeContract.View) : HomeContract.Presenter {

    init {
        view.presenter = this
    }

    override fun loadBannerData(index: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            val result = ServiceManager.getService(WanAndroidHttpService::class.java)?.getMainPageList(0)
            result!!
            if (!result.isOK()) {
                view.showErrorView(result.errorMsg)
            } else {
                view.showData(result.data)
            }

        }
    }
}