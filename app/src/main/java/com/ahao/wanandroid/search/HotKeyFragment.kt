package com.ahao.wanandroid.search

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.ahao.wanandroid.R
import com.ahao.wanandroid.baseview.BaseFragment
import com.ahao.wanandroid.bean.response.HotKey
import com.ahao.wanandroid.rxbus.Message
import com.ahao.wanandroid.rxbus.RxBus
import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService
import com.ahao.wanandroid.util.dp2px
import com.ahao.wanandroid.util.getColorWithApp
import com.ahao.wanandroid.view.TagView
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_hotkey.*

class HotKeyFragment : BaseFragment() {
    override fun getLayoutRes() = R.layout.fragment_hotkey

    private val hotKeyData: MutableList<HotKey> = mutableListOf()

    companion object{
        const val REFRESH_HISTORY_CODE = "REFRESH_HISTORY_CODE"
    }
    private var disposable : Disposable? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDataSource()

        disposable = RxBus.toObservable(REFRESH_HISTORY_CODE)
                .subscribe({
                    initHistory()
                }, {})
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable?.dispose()
    }

    private fun initDataSource() {
        initHotKeys()
        initHistory()
    }

    private fun initHotKeys() {
        ServiceManager.getService(WanAndroidHttpService::class.java)
                ?.getHotKeys()
                ?.onSuccess {
                    if (!isViewDestroyed) {
                        notifyHistoryHasGet(it.data)
                    }
                }
                ?.onError { _, _ ->
                    hot_search_group.visibility = GONE
                }

    }

    private fun notifyHistoryHasGet(data: MutableList<HotKey>) {
        hotKeyData.addAll(data)
        hot_key_tag_layout.removeAllViews()
        hot_search_group.visibility = if (hotKeyData.isNotEmpty()) VISIBLE else GONE

        hotKeyData.forEach {
            val tagView = TagView(context!!).apply {
                textSize = 11f
                setPadding(dp2px(10f), dp2px(5f), dp2px(10f), dp2px(5f))
                setTextColor(getColorWithApp(R.color.gray))
                borderColor = getColorWithApp(R.color.gray_shallow)
                radius = dp2px(10f).toFloat()
                drawStyle = Paint.Style.FILL
                text = it.name
            }
            hot_key_tag_layout.addView(tagView)
            (tagView.layoutParams as? ViewGroup.MarginLayoutParams)?.leftMargin = dp2px(10f)
            (tagView.layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin = dp2px(10f)
        }
        hot_key_tag_layout.onItemClickedListener = { position, _ ->
            RxBus.post(Message(SearchActivity.ACTION_SEARCH, hotKeyData[position]))
        }

    }

    fun initHistory() {
        val historyKeys = HistoryKeyRepository.historyKeys
        history_tag_layout.visibility = if (historyKeys.isNotEmpty()) VISIBLE else GONE
        history_tag_layout.removeAllViews()
        historyKeys.forEach {
            val tagView = TagView(context!!).apply {
                textSize = 11f
                setPadding(dp2px(10f), dp2px(5f), dp2px(10f), dp2px(5f))
                setTextColor(getColorWithApp(R.color.gray))
                borderColor = getColorWithApp(R.color.gray_shallow)
                radius = dp2px(10f).toFloat()
                drawStyle = Paint.Style.FILL
                text = it.name
            }
            history_tag_layout.addView(tagView)
            (tagView.layoutParams as? ViewGroup.MarginLayoutParams)?.leftMargin = dp2px(10f)
            (tagView.layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin = dp2px(10f)
        }

        history_tag_layout.onItemClickedListener = { position, _ ->
            RxBus.post(Message(SearchActivity.ACTION_SEARCH, historyKeys[position]))
        }
    }

}