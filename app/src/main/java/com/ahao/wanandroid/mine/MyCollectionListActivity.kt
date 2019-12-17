package com.ahao.wanandroid.mine

import android.os.Bundle
import android.text.TextUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahao.wanandroid.InfoDetailActivity
import com.ahao.wanandroid.R
import com.ahao.wanandroid.baseview.BaseListActivity
import com.ahao.wanandroid.bean.response.CollectionListResponse
import com.ahao.wanandroid.homepage.HomeItemDecoration
import com.ahao.wanandroid.util.ToastUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_home_page.*
import java.text.SimpleDateFormat
import java.util.*

class MyCollectionListActivity : BaseListActivity<CollectionListResponse.Item>(), MyCollectionContract.View<CollectionListResponse.Item> {

    override fun getLayoutId() = R.layout.activity_my_collection_list

    override fun initPresenter() = MyCollectionListPresenter(this)
    override fun initAdapter() = MyCollectionListAdapter(dataSource, R.layout.view_my_collection_list_item)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recycler_view.addItemDecoration(HomeItemDecoration())
        initEvent()
    }

    private fun initEvent() {
        adapter.setOnItemClickListener { _, _, position ->
            if (TextUtils.isEmpty(dataSource[position].link)) {
                ToastUtil.toast("数据错误...")
            } else {
                val intent = InfoDetailActivity.newIntent(this@MyCollectionListActivity, dataSource[position].link)
                startActivity(intent)
            }
        }
    }

    override fun initLayoutManager() = LinearLayoutManager(this)
    override fun notifyItemDeleted(position: Int) {
        adapter.notifyItemChanged(position)
    }

    override fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }

    class MyCollectionListAdapter(data: List<CollectionListResponse.Item>, layout: Int)
        : BaseQuickAdapter<CollectionListResponse.Item, BaseViewHolder>(layout, data) {

        private val oneDay = 1000 * 60 * 60 * 24
        private val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        override fun convert(helper: BaseViewHolder, item: CollectionListResponse.Item) {
            helper.setText(R.id.title, item.title)
                    .setText(R.id.author, if (TextUtils.isEmpty(item.author)) "匿名" else item.author)
            helper.setGone(R.id.type, !TextUtils.isEmpty(item.chapterName))
                    .setText(R.id.type, item.chapterName)

            if (System.currentTimeMillis() - item.publishTime > oneDay * 3) {
                helper.setText(R.id.time, format.format(item.publishTime))
            } else {
                val timeGap = (System.currentTimeMillis() - item.publishTime) / oneDay
                helper.setText(R.id.time, "$timeGap 天前")
            }
        }

    }
}