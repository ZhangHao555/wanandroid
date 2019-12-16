package com.ahao.wanandroid.baseview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

open abstract class BaseFragment : Fragment() {
    var isViewDestroyed = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isViewDestroyed = false
        return inflater.inflate(getLayoutRes(), container, false)
    }

    abstract fun getLayoutRes(): Int

    override fun onDestroyView() {
        super.onDestroyView()
        isViewDestroyed = true
    }
}