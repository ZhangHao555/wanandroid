package com.ahao.wanandroid.search

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahao.wanandroid.R
import com.ahao.wanandroid.baseview.BaseListFragment
import com.ahao.wanandroid.bean.response.HomePageListResponse
import com.ahao.wanandroid.homepage.HomePageAdapter

class SearchResultFragment : BaseListFragment<HomePageListResponse.Item>() {


    private lateinit var searchKey: String

    companion object {

        const val SEARCH_KEY = "SEARCH_KEY"
        fun newInstance(key: String) = SearchResultFragment().apply {
            arguments = Bundle().apply {
                putString(SEARCH_KEY, key)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchKey = arguments?.getString(SEARCH_KEY) ?: ""

        super.onViewCreated(view, savedInstanceState)
    }

    override fun initPresenter() = SearchResultPresenter(this).apply {
        keywords = searchKey
    }

    override fun initAdapter() = HomePageAdapter(dataSource, R.layout.view_home_list_item)

    override fun initLayoutManager() = LinearLayoutManager(activity)

    override fun getLayoutRes() = R.layout.fragment_search_result

    fun search(keywords: String) {
        (presenter as SearchResultPresenter).search(keywords)
    }

}