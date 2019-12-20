package com.ahao.wanandroid.nav

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.text.TextUtils
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahao.wanandroid.baseview.BaseListFragment
import com.ahao.wanandroid.bean.response.ArticleCategory
import com.ahao.wanandroid.util.ToastUtil
import com.ahao.wanandroid.util.dp2px
import com.ahao.wanandroid.view.TagLayout
import com.ahao.wanandroid.view.TagView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import android.content.Intent
import android.net.Uri
import com.ahao.wanandroid.R


class NavFragment : BaseListFragment<ArticleCategory>() {
    override fun initPresenter() = NavPresenter(this)

    override fun initAdapter() = NavAdapter(R.layout.view_nav_item, dataSource).apply { context = this@NavFragment.context }
    override fun initLayoutManager() = LinearLayoutManager(context)

    override fun getLayoutRes() = R.layout.fragment_nav

    class NavAdapter(resId: Int, data: MutableList<ArticleCategory>) : BaseQuickAdapter<ArticleCategory, BaseViewHolder>(resId, data) {
        var context: Context? = null
        private val tagPool: MutableList<TagView> = mutableListOf()

        override fun convert(helper: BaseViewHolder, item: ArticleCategory) {
            helper.setText(R.id.category_name, item.name)
            val inflater = LayoutInflater.from(mContext)
            val tagLayout: TagLayout = helper.getView(R.id.tag_layout)
            (0 until tagLayout.childCount).forEach {
                val childAt = tagLayout.getChildAt(it)
                if (childAt as? TagView != null) {
                    tagPool.add(childAt)
                }
            }
            tagLayout.removeAllViews()
            item.articles.forEach {
                val red = Math.random().toFloat()
                val green = Math.random().toFloat()
                val blue = Math.random().toFloat()

                val color = argb(1f, red, green, blue)
                val tagView = if (tagPool.size > 0) {
                    tagPool[0]
                    tagPool.removeAt(0)
                } else {
                    inflater.inflate(R.layout.view_nav_tag, tagLayout, false) as TagView
                }
                tagView.borderColor = color
                tagView.setTextColor(getForegroundColor(red, green, blue))
                tagView.drawStyle = Paint.Style.FILL
                tagView.radius = dp2px(5f).toFloat()
                tagView.text = it.title

                tagLayout.addView(tagView)
            }
            tagLayout.onItemClickedListener = { position, view ->
                if (TextUtils.isEmpty(item.articles[position].link)) {
                    ToastUtil.toast("数据错误...")
                } else {
                    val uri = Uri.parse(item.articles[position].link)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    context?.startActivity(intent)
                }
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