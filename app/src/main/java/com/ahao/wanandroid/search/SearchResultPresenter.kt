package com.ahao.wanandroid.search

import com.ahao.wanandroid.baseview.BaseListFragment
import com.ahao.wanandroid.baseview.ListViewPresenter
import com.ahao.wanandroid.bean.response.HomePageListResponse
import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService

class SearchResultPresenter(val view: BaseListFragment<HomePageListResponse.Item>) : ListViewPresenter {

    var pageIndex = 0
    var keywords = ""
    override fun loadListData() {
        if (keywords.isNotEmpty()) {
            ServiceManager.getService(WanAndroidHttpService::class.java)
                    ?.search(pageIndex, keywords)
                    ?.onLoading { view.showLoading() }
                    ?.onError { message, code ->
                        view.finishLoadView()
                        view.showErrorView(message)
                    }?.onSuccess {
                        view.finishLoadView()
                        view.showData(it.data.datas, it.data.total)
                    }
        }
    }

    override fun loadMoreListData() {
        if (keywords.isNotEmpty()) {
            ServiceManager.getService(WanAndroidHttpService::class.java)
                    ?.search(++pageIndex, keywords)
                    ?.onError { message, code ->
                        view.showToast(message)
                    }?.onSuccess {
                        view.showMore(it.data.datas, it.data.total)
                    }
        }
    }

    fun search(keywords : String){
        this.keywords = keywords
        loadListData()
    }
}