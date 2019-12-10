package com.ahao.wanandroid.projeect

import com.ahao.wanandroid.baseview.ListViewInterface
import com.ahao.wanandroid.baseview.ListViewPresenter
import com.ahao.wanandroid.bean.response.HomePageListResponse
import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProjectItemPresenter(val view : ListViewInterface<HomePageListResponse.Item>, private val category:Int) : ListViewPresenter{
    private var pageIndex = 0

    override fun loadListData() {
        GlobalScope.launch(Dispatchers.Main) {
            val result  = ServiceManager.getService(WanAndroidHttpService::class.java)?.getProjectList(pageIndex,category)
            if(result == null){
                view.showErrorView("error")
            }else{
                if(!result.isOK()){
                    view.showErrorView("not ok")
                }else{
                    view.showData(result.data.datas,result.data.total)
                }
            }
        }
    }

    override fun loadMoreListData() {
        GlobalScope.launch(Dispatchers.Main) {
            val result  = ServiceManager.getService(WanAndroidHttpService::class.java)?.getProjectList(pageIndex++,category)
            if(result == null){
                view.showErrorView("error")
            }else{
                if(!result.isOK()){
                    view.showToast("not ok")
                }else{
                    view.showMore(result.data.datas,result.data.total)
                }
            }
        }
    }



}