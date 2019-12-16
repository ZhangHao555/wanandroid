package com.ahao.wanandroid.mine

import com.ahao.wanandroid.baseview.ListViewPresenter
import com.ahao.wanandroid.bean.response.CollectionListResponse
import com.ahao.wanandroid.bean.response.HomePageListResponse
import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyCollectionListPresenter(val view: MyCollectionContract.View<CollectionListResponse.Item>) : ListViewPresenter {
    private var page = 0

    override fun loadListData() {
        GlobalScope.launch(Dispatchers.Main) {
            val result = ServiceManager.getService(WanAndroidHttpService::class.java)?.getCollectionList(page)
            if (result == null || !result.isOK()) {
                view.showErrorView("获取列表失败")
            } else {
                view.showData(result.data.datas, result.data.total)
            }
        }
    }

    override fun loadMoreListData() {
        GlobalScope.launch(Dispatchers.Main) {
            val result = ServiceManager.getService(WanAndroidHttpService::class.java)?.getCollectionList(++page)
            if (result == null || !result.isOK()) {
                view.showToast("获取列表失败")
            } else {
                view.showMore(result.data.datas, result.data.total)
            }
        }
    }
}