package com.ahao.wanandroid.baseview

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ahao.wanandroid.R
import com.ahao.wanandroid.util.ToastUtil
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_home_page.*

abstract class BaseListFragment<T> : BaseFragment(), ListViewInterface<T> {

    protected val dataSource = mutableListOf<T>()
    protected lateinit var recyclerView: RecyclerView
    protected lateinit var refreshLayout: SmartRefreshLayout
    protected lateinit var presenter: ListViewPresenter
    protected lateinit var adapter: RecyclerView.Adapter<*>
    protected lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view)
        refreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        initData()
        initEvent()
        initDataSource()
    }

    protected abstract fun initPresenter(): ListViewPresenter
    protected abstract fun initAdapter(): RecyclerView.Adapter<*>
    protected abstract fun initLayoutManager(): RecyclerView.LayoutManager

    private fun initDataSource() {
        presenter.loadListData()
    }

    private fun initData() {
        layoutManager = initLayoutManager()
        adapter = initAdapter()
        presenter = initPresenter()

        recyclerView.apply {
            layoutManager = this@BaseListFragment.layoutManager
            adapter = this@BaseListFragment.adapter
        }

    }

    private fun initEvent() {
        refreshLayout.setOnRefreshListener {
            presenter.loadListData()
        }

        refreshLayout.setOnLoadMoreListener {
            presenter.loadMoreListData()
        }
    }

    override fun showData(listData: List<T>,total : Int) {
        if (listData.isNotEmpty()) {
            dataSource.clear()
            dataSource.addAll(listData)
            adapter.notifyDataSetChanged()
        }
        swipe_refresh_layout.setEnableAutoLoadMore(total <= dataSource.size)
        finishLoadView()
    }

    override fun showMore(listData: List<T>,total : Int) {
        if (listData.isNotEmpty()) {
            dataSource.addAll(listData)
            adapter.notifyDataSetChanged()
        }
        swipe_refresh_layout.setEnableAutoLoadMore(total <= dataSource.size)
        finishLoadView()
    }

    override fun showToast(message: String) {
        ToastUtil.toast(message)
    }

    override fun showErrorView(message: String) {
        ToastUtil.toast(message)
    }

    override fun finishLoadView(){
        swipe_refresh_layout.finishLoadMore()
        swipe_refresh_layout.finishRefresh()
    }

}