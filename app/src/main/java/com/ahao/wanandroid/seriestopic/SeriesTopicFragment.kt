package com.ahao.wanandroid.seriestopic

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ahao.wanandroid.R
import com.ahao.wanandroid.baseview.BaseFragment
import com.ahao.wanandroid.bean.response.CategoryItem
import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService
import com.ahao.wanandroid.view.ProgressDialog
import kotlinx.android.synthetic.main.fragment_project_page.view_pager
import kotlinx.android.synthetic.main.fragment_series_topic.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

class SeriesTopicFragment : BaseFragment() {

    private val categoryDataList: MutableList<CategoryItem> = mutableListOf()
    private lateinit var viewPagerAdapter: SeriesItemAdapter
    private lateinit var indicatorAdapter: CommonNavigatorAdapter
    private var progressDialog: ProgressDialog? = null
    override fun getLayoutRes() = R.layout.fragment_series_topic

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initEvent()
        initDataSource()
    }

    private fun initData() {
        val navigator = CommonNavigator(activity)
        indicatorAdapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(p0: Context?, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(context)
                colorTransitionPagerTitleView.normalColor = Color.GRAY
                colorTransitionPagerTitleView.selectedColor = ContextCompat.getColor(context!!, R.color.deep_blue)
                colorTransitionPagerTitleView.setText(categoryDataList[index].name)
                colorTransitionPagerTitleView.setOnClickListener { view_pager.setCurrentItem(index) }

                return colorTransitionPagerTitleView
            }

            override fun getCount() = categoryDataList.size
            override fun getIndicator(p0: Context?): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                indicator.setColors(ContextCompat.getColor(context!!, R.color.shallow_blue))
                return indicator
            }

        }

        viewPagerAdapter = SeriesItemAdapter(categoryDataList, childFragmentManager)
        view_pager.adapter = viewPagerAdapter

        magic_indicator.navigator = navigator
        navigator.adapter = indicatorAdapter
        ViewPagerHelper.bind(magic_indicator, view_pager)
    }


    private fun initEvent() {

    }

    private fun initDataSource() {
        ServiceManager.getService(WanAndroidHttpService::class.java)
                ?.getSeriesTopicCategory()
                ?.onLoading {
                    showProgressDialog()
                }?.onError { _, _ ->
                    hideProgressDialog()
                    showErrorPage()
                }?.onSuccess {
                    hideProgressDialog()
                    notifyCategoryDataChanged(it.data)
                }
    }

    private fun hideProgressDialog() {
        progressDialog?.hide()
    }

    private fun showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(activity!!)
        }
        progressDialog?.show()
    }

    private fun notifyCategoryDataChanged(data: List<CategoryItem>) {
        if (data.isNotEmpty()) {
            categoryDataList.clear()
            categoryDataList.addAll(data)
            indicatorAdapter.notifyDataSetChanged()
            viewPagerAdapter.notifyDataSetChanged()
        }
    }

    private fun showErrorPage() {

    }

    class SeriesItemAdapter(val categoryDataList: MutableList<CategoryItem>, fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment {
            return SeriesTopicFragmentItem.newInstance(categoryDataList[position].children)
        }

        override fun getCount() = categoryDataList.size

    }
}