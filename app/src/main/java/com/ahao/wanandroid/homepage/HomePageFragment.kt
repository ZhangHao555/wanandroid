package com.ahao.wanandroid.homepage

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ahao.wanandroid.R
import com.ahao.wanandroid.baseview.BaseFragment
import com.ahao.wanandroid.util.dp2px
import com.ahao.wanandroid.util.getDisplayMetrics
import com.ahao.wanandroid.view.CircleView
import com.ahao.wanandroid.view.banner.BannerIndicator
import com.ahao.wanandroid.view.banner.BannerSetting
import com.ahao.wanandroid.view.banner.ScaleBannerLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home_page.*

class HomePageFragment : BaseFragment(), HomeContract.View {
    override var presenter: HomeContract.Presenter = HomePagePresenter(this)

    override fun showData() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        banner_view.layoutManager = ScaleBannerLayoutManager()
        banner_indicator.adapter = object : BannerIndicator.Adapter(){

            override fun getItemCount(): Int = data.size

            override fun getUnselectedView(context: Context) = CircleView(context,null).apply {
                radius = dp2px(context,2.5f)
                color = Color.parseColor("#E6E6E6")
            }

            override fun getSelectedView(context: Context) = CircleView(context,null).apply {
                radius = dp2px(context,2.5f)
                color = Color.parseColor("#FFFFFF")
            }
        }
        banner_view.indicator = banner_indicator
        banner_view.setUp(BannerSetting().apply {
            slideTimeGap = 3000
            autoSlideSpeed = 1000
            loop = true
        }, Adapter())
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home_page
    }

    private val data = listOf("https://s3.qsxt.info/news/201911/shejinetnews/20180814/1534251303903814.jpg",
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