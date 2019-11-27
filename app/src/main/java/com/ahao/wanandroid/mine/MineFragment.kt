package com.ahao.wanandroid.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahao.wanandroid.R
import com.ahao.wanandroid.baseview.BaseFragment

class MineFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        initData()
        return view
    }

    private fun initData() {

    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_mine_page
    }


}