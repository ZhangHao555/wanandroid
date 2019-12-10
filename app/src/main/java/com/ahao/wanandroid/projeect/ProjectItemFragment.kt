package com.ahao.wanandroid.projeect

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ahao.wanandroid.baseview.BaseListFragment
import com.ahao.wanandroid.baseview.ListViewPresenter
import com.ahao.wanandroid.bean.response.HomePageListResponse

class ProjectItemFragment : BaseListFragment<HomePageListResponse.Item>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        
    }
    override fun initPresenter(): ListViewPresenter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initAdapter(): RecyclerView.Adapter<*> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initLayoutManager(): RecyclerView.LayoutManager {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutRes(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}