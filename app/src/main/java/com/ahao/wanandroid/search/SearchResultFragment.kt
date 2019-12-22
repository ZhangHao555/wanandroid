package com.ahao.wanandroid.search

import androidx.recyclerview.widget.LinearLayoutManager
import com.ahao.wanandroid.R
import com.ahao.wanandroid.baseview.BaseListFragment
import com.ahao.wanandroid.bean.response.HomePageListResponse
import com.ahao.wanandroid.homepage.HomePageAdapter

class SearchResultFragment : BaseListFragment<HomePageListResponse.Item>() {
    override fun initPresenter() = SearchResultPresenter(this)

    override fun initAdapter() = HomePageAdapter(dataSource, R.layout.view_home_list_item)

    override fun initLayoutManager() = LinearLayoutManager(activity)

    override fun getLayoutRes() = R.layout.fragment_search_result

    fun search(keywords: String) {
        (presenter as SearchResultPresenter).search(keywords)
    }

}