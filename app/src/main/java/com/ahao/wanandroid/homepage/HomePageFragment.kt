package com.ahao.wanandroid.homepage

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahao.bannerview.BannerIndicator
import com.ahao.bannerview.BannerSetting
import com.ahao.bannerview.CircleView
import com.ahao.bannerview.ScaleBannerLayoutManager
import com.ahao.wanandroid.InfoDetailActivity
import com.ahao.wanandroid.R
import com.ahao.wanandroid.baseview.BaseListFragment
import com.ahao.wanandroid.bean.response.HomePageListResponse
import com.ahao.wanandroid.util.ToastUtil
import com.ahao.wanandroid.util.dp2px
import com.ahao.wanandroid.util.getDisplayMetrics
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home_page.*

class HomePageFragment : BaseListFragment<HomePageListResponse.Item>(), HomeContract.View {

    override fun initPresenter()= HomePagePresenter(this)
    override fun initAdapter() = HomePageAdapter(dataSource, R.layout.view_home_list_item)
    override fun initLayoutManager() = LinearLayoutManager(activity)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initEvent()
    }

    private fun initData() {
        banner_view.layoutManager = ScaleBannerLayoutManager()
        banner_indicator.adapter = object : BannerIndicator.Adapter() {
            override fun getItemCount() = data.size

            override fun addUnselectedView(parent: BannerIndicator) {
                val circleView = CircleView(parent.context).also {
                    it.color = Color.parseColor("#E6E6E6")
                }
                parent.addView(circleView, dp2px(6f), dp2px(6f))
            }

            override fun addSelectedView(parent: BannerIndicator) {
                val circleView = CircleView(parent.context).also {
                    it.color = Color.parseColor("#FF00CEAA")
                }
                parent.addView(circleView, dp2px(6f), dp2px(6f))
            }
        }
        banner_view.indicator = banner_indicator
        banner_view.setUp(BannerSetting().apply {
            slideTimeGap = 3000
            autoSlideSpeed = 1000
            loop = true
        }, Adapter())

        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = this@HomePageFragment.adapter
            addItemDecoration(HomeItemDecoration())
        }
    }

    private fun initEvent(){
        adapter.setOnItemClickListener { _, _, position ->
            if (TextUtils.isEmpty(dataSource[position].link)) {
                ToastUtil.toast("数据错误...")
            } else {
                val intent = InfoDetailActivity.newIntent(activity, dataSource[position].link)
                startActivity(intent)
            }
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home_page
    }

    private val data = listOf("https://www.wanandroid.com/blogimgs/fa822a30-00fc-4e0d-a51a-d704af48205c.jpeg",
            "https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png",
            "https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png")

    inner class Adapter : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(group: ViewGroup, position: Int): ViewHolder {
            val item = LayoutInflater.from(context).inflate(R.layout.view_banner_item_view, group, false)
            return ViewHolder(item)
        }

        override fun getItemCount() = data.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.cardItem.layoutParams?.width = (context?.let { getDisplayMetrics(it).widthPixels }?.times(0.85f))?.toInt()
            context?.let { Glide.with(it).load(data[position]).into(holder.image) }
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.banner_image)
        val cardItem: CardView = itemView.findViewById(R.id.card_item)
    }


}