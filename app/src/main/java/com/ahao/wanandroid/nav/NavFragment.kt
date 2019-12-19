package com.ahao.wanandroid.nav

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahao.wanandroid.R
import com.ahao.wanandroid.baseview.BaseListFragment
import com.ahao.wanandroid.bean.response.ArticleCategory
import com.ahao.wanandroid.util.dp2px
import com.ahao.wanandroid.view.TagLayout
import com.ahao.wanandroid.view.TagView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class NavFragment : BaseListFragment<ArticleCategory>() {
    override fun initPresenter() = NavPresenter(this)

    override fun initAdapter() = NavAdapter(R.layout.view_nav_item, dataSource)
    override fun initLayoutManager() = LinearLayoutManager(context)

    override fun getLayoutRes() = R.layout.fragment_nav

    class NavAdapter(resId: Int, data: MutableList<ArticleCategory>) : BaseQuickAdapter<ArticleCategory, BaseViewHolder>(resId, data) {

        override fun convert(helper: BaseViewHolder, item: ArticleCategory) {
            helper.setText(R.id.category_name, item.name)
            val inflater = LayoutInflater.from(mContext)
            item.articles.forEach {
                val red = Math.random().toFloat()
                val green = Math.random().toFloat()
                val blue = Math.random().toFloat()

                val color = argb(1f, red, green, blue)
                val tagLayout: TagLayout = helper.getView(R.id.tag_layout)
                val tagView = inflater.inflate(R.layout.view_nav_tag, tagLayout, false) as TagView
                tagView.borderColor = color
                tagView.setTextColor(getForegroundColor(red, green, blue))
                tagView.drawStyle = Paint.Style.FILL
                tagView.radius = dp2px(5f).toFloat()
                tagView.text = it.title
                tagLayout.addView(tagView)
            }
        }

        private fun getForegroundColor(r: Float, g: Float, b: Float) = if ((0.299f * r + 0.587f * g + 0.114f * b) > 0.5) {
            Color.BLACK
        } else Color.WHITE

        private fun argb(alpha: Float, red: Float, green: Float, blue: Float): Int {
            return (alpha * 255.0f + 0.5f).toInt() shl 24 or
                    ((red * 255.0f + 0.5f).toInt() shl 16) or
                    ((green * 255.0f + 0.5f).toInt() shl 8) or
                    (blue * 255.0f + 0.5f).toInt()
        }

    }

}