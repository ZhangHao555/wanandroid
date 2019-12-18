package com.ahao.wanandroid.projeect

import com.ahao.wanandroid.baseview.ListViewInterface
import com.ahao.wanandroid.baseview.ListViewPresenter
import com.ahao.wanandroid.bean.response.HomePageListResponse
import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService

class ProjectItemPresenter(val view: ListViewInterface<HomePageListResponse.Item>, private val category: Int) : ListViewPresenter {
    private var pageIndex = 0

    override fun loadListData() {
        ServiceManager.getService(WanAndroidHttpService::class.java)
                ?.getProjectList(pageIndex, category)
                ?.onLoading {
                    view.showLoading()
                }?.onError { message, _ ->
                    view.finishLoadView()
                    view.showErrorView(message)
                }?.onSuccess {
                    view.showData(it.data.datas, it.data.total)
                }
    }

    override fun loadMoreListData() {
        ServiceManager.getService(WanAndroidHttpService::class.java)
                ?.getProjectList(++pageIndex, category)
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