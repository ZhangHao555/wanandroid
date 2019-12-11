package com.ahao.wanandroid.projeect

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahao.wanandroid.R
import com.ahao.wanandroid.baseview.BaseListFragment
import com.ahao.wanandroid.bean.response.HomePageListResponse
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class ProjectItemFragment : BaseListFragment<HomePageListResponse.Item>() {

    companion object {
        private const val CATEGORY = "category"

        fun newInstance(category: Int): ProjectItemFragment {
            val projectItemFragment = ProjectItemFragment()
            val args = Bundle()
            args.putInt(CATEGORY, category)
            projectItemFragment.arguments = args
            return projectItemFragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initPresenter() = ProjectItemPresenter(this, arguments!!.getInt(CATEGORY))

    override fun initAdapter() = Adapter(R.layout.view_project_item,dataSource)

    override fun initLayoutManager() = LinearLayoutManager(activity)

    override fun getLayoutRes() = R.layout.fragment_project_item

    inner class Adapter(layoutId: Int, data: MutableList<HomePageListResponse.Item>)
        : BaseQuickAdapter<HomePageListResponse.Item, BaseViewHolder>(layoutId, data) {
        override fun convert(helper: BaseViewHolder, item: HomePageListResponse.Item) {
            Glide.with(this@ProjectItemFragment).load(item.envelopePic).into(helper.getView(R.id.image))
            helper.setText(R.id.title,item.title)
            helper.setText(R.id.content,item.desc)
            helper.setText(R.id.time,item.niceDate)
            helper.setText(R.id.author,item.author)
        }

    }
}