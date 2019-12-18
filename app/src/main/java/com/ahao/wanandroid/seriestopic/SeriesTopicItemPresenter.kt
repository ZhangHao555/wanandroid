package com.ahao.wanandroid.seriestopic

import com.ahao.wanandroid.baseview.ListViewInterface
import com.ahao.wanandroid.baseview.ListViewPresenter
import com.ahao.wanandroid.bean.response.HomePageListResponse
import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService

class SeriesTopicItemPresenter(val view: ListViewInterface<HomePageListResponse.Item>, var category: Int = 0) : ListViewPresenter {
    private var pageIndex = 0

    override fun loadListData() {
        ServiceManager.getService(WanAndroidHttpService::class.java)
                ?.getSeriesTopicList(pageIndex, category)
                ?.onLoading {
                    view.showLoading()
                }?.onError { message, code ->
                    view.finishLoadView()
                    view.showErrorView(message)
                }?.onSuccess {
                    view.showData(it.data.datas, it.data.total)
                }

    }

    override fun loadMoreListData() {
        ServiceManager.getService(WanAndroidHttpService::class.java)
                ?.getSeriesTopicList(++pageIndex, category)
                ?.onLoading {
                    view.showLoading()
                }?.onError { message, _ ->
                    view.finishLoadView()
                    view.showToast(message)
                }?.onSuccess {
                    view.showMore(it.data.datas, it.data.total)
                }

    }


}