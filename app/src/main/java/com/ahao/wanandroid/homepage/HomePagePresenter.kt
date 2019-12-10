package com.ahao.wanandroid.homepage

import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomePagePresenter(val view: HomeContract.View) : HomeContract.Presenter {
    private var pageIndex = 0

    override fun loadListData() {
        GlobalScope.launch(Dispatchers.Main) {
            val result = ServiceManager.getService(WanAndroidHttpService::class.java)?.getHomePageList(pageIndex)
            result!!
            if (!result.isOK()) {
                view.showErrorView(result.errorMsg)
            } else {
                view.showData(result.data.datas,result.data.total)
            }
        }
    }

    override fun loadMoreListData() {
        GlobalScope.launch(Dispatchers.Main) {
            val result = ServiceManager.getService(WanAndroidHttpService::class.java)?.getHomePageList(++pageIndex)
            result!!
            if (!result.isOK()) {
                view.showToast(result.errorMsg)
            } else {
                view.showMore(result.data.datas,result.data.total)
            }
        }
    }

}