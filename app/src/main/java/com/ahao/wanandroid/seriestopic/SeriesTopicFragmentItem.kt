package com.ahao.wanandroid.seriestopic

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahao.wanandroid.R
import com.ahao.wanandroid.baseview.BaseListFragment
import com.ahao.wanandroid.bean.response.CategoryItem
import com.ahao.wanandroid.bean.response.HomePageListResponse
import com.ahao.wanandroid.homepage.HomeItemDecoration
import com.ahao.wanandroid.homepage.HomePageAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_series_topic_item.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.Serializable

class SeriesTopicFragmentItem : BaseListFragment<HomePageListResponse.Item>() {
    override fun initPresenter() = SeriesTopicItemPresenter(this)
    override fun initAdapter() = HomePageAdapter(dataSource, R.layout.view_home_list_item)
    override fun initLayoutManager() = LinearLayoutManager(context)
    override fun getLayoutRes() = R.layout.fragment_series_topic_item

    private val categoryDataList: MutableList<CategoryItem> = mutableListOf()
    private val categoryAdapter = CategoryAdapter(R.layout.view_series_topic_category_item, categoryDataList)

    companion object {
        const val DATA_LIST = "dataList"

        fun newInstance(children: MutableList<CategoryItem>) = SeriesTopicFragmentItem().apply {
            arguments = Bundle().apply {
                putSerializable(DATA_LIST, children as Serializable)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initEvent()
    }

    private fun initData() {
        recyclerView.addItemDecoration(HomeItemDecoration())
        categoryDataList.clear()
        categoryDataList.addAll(arguments!![DATA_LIST] as MutableList<CategoryItem>)
        if (categoryDataList.isEmpty()) {
            showErrorView("没有数据")
            return
        }
        category_list.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        category_list.adapter = categoryAdapter
        category_list.addItemDecoration(ItemDecoration())
        (presenter as SeriesTopicItemPresenter).category = categoryDataList[0].id
    }

    private fun initEvent() {
        categoryAdapter.setOnItemClickListener { adapter, view, position ->
            (presenter as SeriesTopicItemPresenter).category = categoryDataList[position].id
            presenter.loadListData()
        }
    }

    override fun initDataSource() {
        GlobalScope.launch(Dispatchers.Main) {
            presenter.loadListData()
        }

    }

    class CategoryAdapter(resLayout: Int, data: MutableList<CategoryItem>) : BaseQuickAdapter<CategoryItem, BaseViewHolder>(resLayout, data) {

        override fun convert(helper: BaseViewHolder, item: CategoryItem?) {
            helper.setText(R.id.tag_text, item?.name)
        }

    }


}