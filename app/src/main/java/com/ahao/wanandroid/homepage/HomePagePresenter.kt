package com.ahao.wanandroid.homepage

import com.ahao.wanandroid.bean.response.JsonResult
import com.ahao.wanandroid.bean.response.MainPageListResponse
import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService
import io.reactivex.functions.Consumer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request

class HomePagePresenter(val view : HomeContract.View) : HomeContract.Presenter {

    init {
        view.presenter = this
    }

    override suspend fun loadBannerData(index : Int) = withContext(Dispatchers.IO){

       ServiceManager.getService(WanAndroidHttpService::class.java)?.getMainPageList(index)?.execute()
    }

}