package com.ahao.wanandroid.seriestopic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahao.wanandroid.R
import com.ahao.wanandroid.baseview.BaseFragment
import com.ahao.wanandroid.bean.response.CategoryItem
import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_project_page.*
import kotlinx.android.synthetic.main.fragment_series_topic_item.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SeriesTopicFragment : BaseFragment() {

    private val categoryDataList: MutableList<CategoryItem> = mutableListOf()
    private val categoryAdapter = CategoryAdapter(R.layout.view_series_topic_category_item, categoryDataList)
    private lateinit var viewPagerAdapter: SeriesItemAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initEvent()
        initDataSource()
    }

    private fun initData() {
        category_list.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = categoryAdapter
            addItemDecoration(ItemDecoration())
        }
        category_list.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        category_list.adapter = categoryAdapter

        viewPagerAdapter = SeriesItemAdapter(categoryDataList, childFragmentManager)
        view_pager.adapter = viewPagerAdapter
    }


    private fun initEvent() {
        categoryAdapter.setOnItemClickListener { _, _, position ->
            view_pager.setCurrentItem(position,true)
        }
    }

    private fun initDataSource() {
        GlobalScope.launch(Dispatchers.Main) {
            val result = ServiceManager.getService(WanAndroidHttpService::class.java)?.getSeriesTopicCategory()
            if (result == null) {
                showErrorPage()
            } else {
                if (result.isOK()) {
                    notifyCategoryDataChanged(result.data)
                }
            }
        }

    }

    private fun notifyCategoryDataChanged(data: List<CategoryItem>) {
        if (data.isNotEmpty()) {
            categoryDataList.clear()
            categoryDataList.addAll(data)
            categoryAdapter.notifyDataSetChanged()
            viewPagerAdapter.notifyDataSetChanged()
        }
    }

    private fun showErrorPage() {

    }


    class CategoryAdapter(resLayout: Int, data: MutableList<CategoryItem>) : BaseQuickAdapter<CategoryItem, BaseViewHolder>(resLayout, data) {

        override fun convert(helper: BaseViewHolder, item: CategoryItem?) {
            helper.setText(R.id.tag_text, item?.name)
        }

    }

    override fun getLayoutRes() = R.layout.fragment_series_topic

    class SeriesItemAdapter(val categoryDataList: MutableList<CategoryItem>, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment {
            return SeriesTopicFragmentItem.newInstance(categoryDataList[position].children)
        }

        override fun getCount() = categoryDataList.size

    }
}