package com.ahao.wanandroid.nav

import com.ahao.wanandroid.baseview.ListViewInterface
import com.ahao.wanandroid.baseview.ListViewPresenter
import com.ahao.wanandroid.bean.response.ArticleCategory
import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService

class NavPresenter(val view: ListViewInterface<ArticleCategory>) : ListViewPresenter {

    override fun loadListData() {
        ServiceManager.getService(WanAndroidHttpService::class.java)
                ?.getNavList()
                ?.onLoading { view.showLoading() }
                ?.onError { message, _ ->
                    view.finishLoadView()
                    view.showErrorView(message)
                }
                ?.onSuccess {
                    view.finishLoadView()
                    view.showData(it.data, it.data.size)
                }
    }

    override fun loadMoreListData() {}


}