package com.ahao.wanandroid.homepage

import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomePagePresenter(val view: HomeContract.View) : HomeContract.Presenter {
    private var pageIndex = 0

    init {
        view.presenter = this
    }

    override fun loadHomeListData() {
        GlobalScope.launch(Dispatchers.Main) {
            val result = ServiceManager.getService(WanAndroidHttpService::class.java)?.getMainPageList(pageIndex)
            result!!
            if (!result.isOK()) {
                view.showErrorView(result.errorMsg)
            } else {
                view.showData(result.data)
            }
        }
    }

    override fun loadMoreHomeListData() {
        GlobalScope.launch(Dispatchers.Main) {
            val result = ServiceManager.getService(WanAndroidHttpService::class.java)?.getMainPageList(++pageIndex)
            result!!
            if (!result.isOK()) {
                view.showToast(result.errorMsg)
            } else {
                view.showMore(result.data)
            }
        }
    }

}