package com.ahao.wanandroid.view.banner

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.ahao.wanandroid.R
import com.ahao.wanandroid.util.getDisplayMetrics
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_banner_view.view.*
import kotlin.math.abs

open class BannerView : FrameLayout {

    var canScroll = true

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_banner_view, this, true)
        banner_recycler_view.layoutManager = ScaleBannerLayoutManager()
        banner_recycler_view.adapter = Adapter()
        BannerPageSnapHelper().run {
            infinite = true
            attachToRecyclerView(banner_recycler_view)
        }
    }


    inner class Adapter : RecyclerView.Adapter<ViewHolder>() {
        private val data = listOf("https://www.wanandroid.com/blogimgs/fa822a30-00fc-4e0d-a51a-d704af48205c.jpeg",
                "https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png",
                "https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png")

        override fun onCreateViewHolder(group: ViewGroup, position: Int): ViewHolder {
            val item = LayoutInflater.from(context).inflate(R.layout.view_banner_item_view, group, false)
            return ViewHolder(item)
        }

        override fun getItemCount() = data.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.image.layoutParams?.width = (getDisplayMetrics(context).widthPixels * 0.85f).toInt()
            Glide.with(context).load(data[position]).into(holder.image)
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.banner_image)
    }

    private var actionDownX: Float = 0.toFloat()
    private var actionDownY: Float = 0.toFloat()
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if(ev == null){
            return super.dispatchTouchEvent(ev)
        }
        when(ev.action){
            MotionEvent.ACTION_DOWN -> requestDisallowInterceptTouchEvent(true)
            MotionEvent.ACTION_MOVE -> {
                val moveX = ev.x
                val moveY = ev.y
                if (abs(actionDownX - moveX) > abs(actionDownY - moveY)) {
                    parent.requestDisallowInterceptTouchEvent(true)
                } else {
                    parent.requestDisallowInterceptTouchEvent(false)
                }

                if (!canScroll) {
                    return false
                }
            }
        }

        return super.dispatchTouchEvent(ev)
    }

}