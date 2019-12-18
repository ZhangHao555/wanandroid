package com.ahao.wanandroid.mine

import com.ahao.wanandroid.baseview.ListViewPresenter
import com.ahao.wanandroid.bean.response.CollectionListResponse
import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyCollectionListPresenter(val view: MyCollectionContract.View<CollectionListResponse.Item>) : ListViewPresenter {
    private var page = 0

    override fun loadListData() {
        GlobalScope.launch(Dispatchers.Main) {
            ServiceManager.getService(WanAndroidHttpService::class.java)?.getCollectionList(page)
                    ?.onLoading {
                        view.showLoading()
                    }?.onSuccess {
                        view.showData(it.data.datas, it.data.total)
                    }?.onError { _, _ ->
                        view.finishLoadView()
                        view.showErrorView("获取列表失败")
                    }

        }
    }

    override fun loadMoreListData() {
        GlobalScope.launch(Dispatchers.Main) {
            ServiceManager.getService(WanAndroidHttpService::class.java)?.getCollectionList(++page)
                    ?.onLoading {
                        view.showLoading()
                    }?.onSuccess {
                        view.showMore(it.data.datas, it.data.total)
                    }?.onError { _, _ ->
                        view.finishLoadView()
                        view.showToast("获取列表失败")
                    }
        }
    }
}