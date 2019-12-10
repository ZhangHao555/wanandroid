package com.ahao.wanandroid.projeect

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.ahao.wanandroid.R
import com.ahao.wanandroid.baseview.BaseFragment
import kotlinx.android.synthetic.main.fragment_project_page.*
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView


class ProjectPageFragment : BaseFragment() {
    val tabDataSource = mutableListOf<String>()
    init {
        tabDataSource.add("111")
        tabDataSource.add("222")
        tabDataSource.add("333")
        tabDataSource.add("444")
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_project_page
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTab()
    }

    private fun initTab() {
        val navigator = CommonNavigator(activity)
        navigator.adapter = object :CommonNavigatorAdapter(){
            override fun getTitleView(p0: Context?, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(context)
                colorTransitionPagerTitleView.normalColor = Color.GRAY
                colorTransitionPagerTitleView.selectedColor = Color.BLACK
                colorTransitionPagerTitleView.setText(tabDataSource[index])
                colorTransitionPagerTitleView.setOnClickListener { view_pager.setCurrentItem(index)}
                return colorTransitionPagerTitleView
            }

            override fun getCount() = tabDataSource.size


            override fun getIndicator(p0: Context?): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                return indicator
            }

        }
        indicator.navigator = navigator

    }
}