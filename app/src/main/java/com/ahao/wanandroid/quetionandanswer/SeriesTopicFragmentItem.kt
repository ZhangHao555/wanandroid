package com.ahao.wanandroid.quetionandanswer

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahao.wanandroid.R
import com.ahao.wanandroid.baseview.BaseFragment
import com.ahao.wanandroid.bean.response.CategoryItem
import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_series_topic_item.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.Serializable

class SeriesTopicFragmentItem : BaseFragment() {

    override fun getLayoutRes() = R.layout.fragment_series_topic_item
    private val categoryDataList: MutableList<CategoryItem> = mutableListOf()
    private val categoryAdapter = CategoryAdapter(R.layout.view_series_topic_category_item,categoryDataList)

     companion object{
         const val DATA_LIST = "dataList"

         fun newInstance(children: MutableList<CategoryItem>)
                 = SeriesTopicFragmentItem().apply{
                    arguments = Bundle().apply{
                        putSerializable(DATA_LIST,children as Serializable)
                 }
         }
     }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initDataSource()
    }

    private fun initData() {
        categoryDataList.clear()
        categoryDataList.addAll(arguments!![DATA_LIST] as MutableList<CategoryItem>)
        category_list.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        category_list.adapter = categoryAdapter
        category_list.addItemDecoration(ItemDecoration())
    }

    private fun initDataSource() {
        GlobalScope.launch(Dispatchers.Main ) {
            val result = ServiceManager.getService(WanAndroidHttpService::class.java)?.getSeriesTopicCategory()
            if(result == null){
                showErrorPage()
            }else{
                if(result.isOK()){
                   notifyCategoryDataChanged(result.data)
                }
            }
        }

    }

    private fun notifyCategoryDataChanged(data: List<CategoryItem>) {
        if(data.isNotEmpty()){
            categoryDataList.clear()
            categoryDataList.addAll(data)
            categoryAdapter.notifyDataSetChanged()
        }
    }

    private fun showErrorPage() {

    }


    class CategoryAdapter(resLayout: Int, data: MutableList<CategoryItem>) : BaseQuickAdapter<CategoryItem, BaseViewHolder>(resLayout, data) {

        override fun convert(helper: BaseViewHolder, item: CategoryItem?) {
            helper.setText(R.id.tag_text, item?.name)
        }

    }


}