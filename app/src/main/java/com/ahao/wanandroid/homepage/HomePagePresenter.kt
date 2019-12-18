package com.ahao.wanandroid.homepage

import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService
import com.ahao.wanandroid.util.ToastUtil

class HomePagePresenter(val view: HomeContract.View) : HomeContract.Presenter {
    private var pageIndex = 0

    override fun loadListData() {
        ServiceManager.getService(WanAndroidHttpService::class.java)
                ?.getHomePageList(pageIndex)
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
                ?.getHomePageList(++pageIndex)
                ?.onLoading {
                }?.onError { message, _ ->
                    view.finishLoadView()
                    view.showToast(message)
                }?.onSuccess {
                    view.showMore(it.data.datas, it.data.total)
                }

    }

    fun collect(id: Int) {
        ServiceManager.getService(WanAndroidHttpService::class.java)
                ?.collect(id)
                ?.onError { _, _ ->
                    ToastUtil.toast("收藏失败！")
                }
                ?.onSuccess {
                    ToastUtil.toast("收藏成功！")
                }
    }

    fun cancelCollect(id: Int) {
        ServiceManager.getService(WanAndroidHttpService::class.java)
                ?.cancelCollect(id)
                ?.onError { _, _ ->
                    ToastUtil.toast("取消收藏失败！")
                }?.onSuccess {
                    ToastUtil.toast("取消收藏成功！")
                }
    }
}

