package com.ahao.wanandroid.homepage

import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService
import com.ahao.wanandroid.util.ToastUtil
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
                view.showData(result.data.datas, result.data.total)
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
                view.showMore(result.data.datas, result.data.total)
            }
        }
    }

    fun collect(id: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            val collect = ServiceManager.getService(WanAndroidHttpService::class.java)?.collect(id)
            if (collect == null || !collect.isOK()) {
                ToastUtil.toast("收藏失败！")
            } else {
                ToastUtil.toast("收藏成功！")
            }
        }
    }

    fun cancelCollect(id: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            val collect = ServiceManager.getService(WanAndroidHttpService::class.java)?.cancelCollect(id)
            if (collect == null || !collect.isOK()) {
                ToastUtil.toast("取消收藏失败！")
            } else {
                ToastUtil.toast("取消收藏成功！")
            }
        }
    }

}