package com.ahao.wanandroid

import android.os.Bundle
import com.ahao.wanandroid.baseview.BaseFragment

class NavFragment : BaseFragment() {

    companion object{
        fun newInstance() = NavFragment().apply{
                arguments = Bundle().apply{

                }
        }
    }
    override fun getLayoutRes() = R.layout.fragment_nav
}