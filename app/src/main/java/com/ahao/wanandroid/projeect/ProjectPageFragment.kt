package com.ahao.wanandroid.projeect

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ahao.wanandroid.R
import com.ahao.wanandroid.baseview.BaseFragment
import com.ahao.wanandroid.bean.response.CategoryItem
import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService
import com.ahao.wanandroid.util.ToastUtil
import com.ahao.wanandroid.view.ProgressDialog
import kotlinx.android.synthetic.main.fragment_project_page.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView


class ProjectPageFragment : BaseFragment() {
    val tabDataSource = mutableListOf<CategoryItem>()
    var indicatorAdapter: CommonNavigatorAdapter? = null
    var viewPagerAdapter: ProjectPageAdapter? = null
    var progressDialog: ProgressDialog? = null

    override fun getLayoutRes(): Int {
        return R.layout.fragment_project_page
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e(TAG, "ProjectPageFragment onViewCreated")
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
                colorTransitionPagerTitleView.selectedColor = Color.BLACK
                colorTransitionPagerTitleView.setText(tabDataSource[index].name)
                colorTransitionPagerTitleView.setOnClickListener { view_pager.setCurrentItem(index) }
                return colorTransitionPagerTitleView
            }

            override fun getCount() = tabDataSource.size


            override fun getIndicator(p0: Context?): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                return indicator
            }

        }
        navigator.adapter = indicatorAdapter
        indicator.navigator = navigator
        ViewPagerHelper.bind(indicator, view_pager)

        viewPagerAdapter = ProjectPageAdapter(childFragmentManager)
        view_pager.adapter = viewPagerAdapter
    }


    private fun initDataSource() {
        ServiceManager.getService(WanAndroidHttpService::class.java)?.getProjectCategory()
                ?.onLoading {
                    showProgressDialog()
                }?.onError { message, code ->
                    hideProgressDialog()
                    ToastUtil.toast(message)
                }?.onSuccess {
                    tabDataSource.clear()
                    tabDataSource.addAll(it.data)
                    indicatorAdapter?.notifyDataSetChanged()
                    viewPagerAdapter?.notifyDataSetChanged()
                    hideProgressDialog()
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

    private fun initEvent() {

    }


    inner class ProjectPageAdapter(fragmentManager: FragmentManager)
        : FragmentPagerAdapter(fragmentManager) {
        override fun getItem(position: Int) = ProjectItemFragment.newInstance(tabDataSource[position].id)

        override fun getCount() = tabDataSource.size
    }

    val TAG = "ProjectPageFragment"
    override fun onDestroyView() {
        super.onDestroyView()
        Log.e(TAG, "ProjectPageFragment onDestroyView")
    }

}