package com.ahao.wanandroid.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahao.wanandroid.R
import com.ahao.wanandroid.baseview.BaseFragment

class HomePageFragment : BaseFragment(),HomeContract.View {
    override var presenter: HomeContract.Presenter = HomePagePresenter(this)


    override fun showData() {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        initData()
        return view

    }

    private fun initData() {

    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home_page
    }

}