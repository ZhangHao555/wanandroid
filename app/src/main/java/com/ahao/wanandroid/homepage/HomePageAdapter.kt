package com.ahao.wanandroid.homepage

import android.graphics.Color
import android.graphics.Paint
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.LinearLayout
import com.ahao.wanandroid.R
import com.ahao.wanandroid.bean.response.HomePageListResponse
import com.ahao.wanandroid.util.dp2px
import com.ahao.wanandroid.view.TagView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import java.text.SimpleDateFormat
import java.util.*

class HomePageAdapter(data: List<HomePageListResponse.Item>, layout: Int)
    : BaseQuickAdapter<HomePageListResponse.Item, BaseViewHolder>(layout, data) {

    private val oneDay = 1000 * 60 * 60 * 24
    private val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    override fun convert(helper: BaseViewHolder, item: HomePageListResponse.Item) {
        helper.setText(R.id.title, item.title)
        helper.setGone(R.id.author, !TextUtils.isEmpty(item.author))
                .setText(R.id.author,  item.author)
        helper.setGone(R.id.type, !TextUtils.isEmpty(item.chapterName))
                .setText(R.id.type, item.chapterName)

        if (System.currentTimeMillis() - item.publishTime > oneDay * 3) {
            helper.setText(R.id.time, format.format(item.publishTime))
        } else {
            val timeGap = (System.currentTimeMillis() - item.publishTime ) / oneDay
            helper.setText(R.id.time, "$timeGap 天前")
        }

        if (item.tags.isEmpty()) {
            helper.setGone(R.id.tag_container, false)
        } else {
            helper.setGone(R.id.tag_container, true)
            val tagContainer: LinearLayout = helper.getView(R.id.tag_container)
            tagContainer.removeAllViews()
            item.tags.forEach {
                val tagView = TagView(tagContainer.context)
                tagView.apply {
                    borderWidth = dp2px(0.5f).toFloat()
                    radius = dp2px(2f).toFloat()
                    drawStyle = Paint.Style.STROKE
                    borderColor = Color.parseColor("#3D7013")
                    setTextColor(Color.parseColor("#3D7113"))
                    text = it.name
                    val dp3: Int = dp2px(3f)
                    setPadding(dp3,dp3,dp3,dp3)
                    textSize = 10f
                    tagContainer.addView(this,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                }
            }
        }
        helper.setImageResource(R.id.like,if(item.collect) R.mipmap.like else R.mipmap.not_like )

    }

}