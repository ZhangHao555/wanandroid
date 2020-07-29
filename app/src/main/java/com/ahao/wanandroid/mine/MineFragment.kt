package com.ahao.wanandroid.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahao.wanandroid.R
import com.ahao.wanandroid.baseview.BaseFragment
import com.ahao.wanandroid.mymusic.MyMusicActivity
import kotlinx.android.synthetic.main.fragment_mine_page.*

class MineFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initEvent()
    }

    private fun initEvent() {
        my_music_container.setOnClickListener {
            startActivity(MyMusicActivity.newIntent(activity))
        }
    }

    private fun initData() {

    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_mine_page
    }


}