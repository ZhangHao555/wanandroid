package com.ahao.wanandroid.projeect

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ahao.wanandroid.R
import com.ahao.wanandroid.baseview.BaseListFragment
import com.ahao.wanandroid.bean.response.HomePageListResponse

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

    override fun initAdapter(): RecyclerView.Adapter<*> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initLayoutManager(): RecyclerView.LayoutManager {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutRes() = R.layout.fragment_project_item
}