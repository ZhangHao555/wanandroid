package com.ahao.wanandroid.baseview

import android.os.Bundle
import android.os.PersistableBundle
import androidx.recyclerview.widget.RecyclerView
import com.ahao.wanandroid.R
import com.ahao.wanandroid.util.ToastUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_home_page.*

abstract class BaseListActivity<T> : BaseActivity(), ListViewInterface<T> {

    protected val dataSource = mutableListOf<T>()
    protected lateinit var recyclerView: RecyclerView
    protected lateinit var refreshLayout: SmartRefreshLayout
    protected lateinit var presenter: ListViewPresenter
    protected lateinit var adapter: BaseQuickAdapter<*, BaseViewHolder>
    protected lateinit var layoutManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        recyclerView = findViewById(R.id.recycler_view)
        refreshLayout = findViewById(R.id.swipe_refresh_layout)

        initData()
        initEvent()
        initDataSource()
    }

    private fun initData() {
        layoutManager = initLayoutManager()
        adapter = initAdapter()
        presenter = initPresenter()

        recyclerView.apply {
            layoutManager = this@BaseListActivity.layoutManager
            adapter = this@BaseListActivity.adapter
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

    open fun initDataSource() {
        presenter.loadListData()
    }

    override fun showData(listData: List<T>, total: Int) {
        if (listData.isNotEmpty()) {
            dataSource.clear()
            dataSource.addAll(listData)
            adapter.notifyDataSetChanged()
        }
        refreshLayout.setEnableAutoLoadMore(total > dataSource.size)
        swipe_refresh_layout.setEnableLoadMore(total > dataSource.size)
        finishLoadView()
    }

    override fun showMore(listData: List<T>, total: Int) {
        if (listData.isNotEmpty()) {
            dataSource.addAll(listData)
            adapter.notifyDataSetChanged()
        }
        swipe_refresh_layout.setEnableAutoLoadMore(total > dataSource.size)
        swipe_refresh_layout.setEnableLoadMore(total > dataSource.size)
        finishLoadView()
    }

    override fun showToast(message: String) {
        ToastUtil.toast(message)
    }

    override fun showErrorView(message: String) {
        ToastUtil.toast(message)
    }

    override fun finishLoadView() {
        swipe_refresh_layout.finishLoadMore()
        swipe_refresh_layout.finishRefresh()
    }

    abstract fun getLayoutId(): Int
    protected abstract fun initPresenter(): ListViewPresenter
    protected abstract fun initAdapter(): BaseQuickAdapter<*, BaseViewHolder>
    protected abstract fun initLayoutManager(): RecyclerView.LayoutManager
}