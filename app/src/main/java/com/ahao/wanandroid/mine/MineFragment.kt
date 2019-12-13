package com.ahao.wanandroid.mine

import android.os.Bundle
import android.view.View
import com.ahao.wanandroid.R
import com.ahao.wanandroid.baseview.BaseFragment
import kotlinx.android.synthetic.main.fragment_mine_page.*

class MineFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {

    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_mine_page
    }



}