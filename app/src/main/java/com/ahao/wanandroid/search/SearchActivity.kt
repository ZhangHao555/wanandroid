package com.ahao.wanandroid.search

import android.os.Bundle
import com.ahao.wanandroid.R
import com.ahao.wanandroid.baseview.BaseActivity
import com.ahao.wanandroid.bean.response.HotKey
import com.ahao.wanandroid.rxbus.RxBus
import com.ahao.wanandroid.util.Preference
import io.reactivex.disposables.Disposable

class SearchActivity : BaseActivity() {
    override fun getResLayoutId() = R.layout.activity_search_result
    private var keyFragment: HotKeyFragment? = null
    private var resultFragment: SearchResultFragment? = null
    var disposable: Disposable? = null

    private var historyKeys: MutableList<HotKey> by Preference("SEARCH_HISTORY", mutableListOf(), false)

    companion object {
        const val ACTION_SEARCH = "ACTION_SEARCH"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        disposable = RxBus.toObservable(ACTION_SEARCH)
                .subscribe({
                    hideKeyFragment()
                    showSearchResultFragment()
                    historyKeys.add(it.data as HotKey)
                    this.historyKeys = historyKeys

                    resultFragment?.search(it.data.name)
                }, {

                })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    private fun showSearchResultFragment() {
        if (resultFragment == null) {
            resultFragment = SearchResultFragment()
        }
        if (resultFragment?.isAdded == false) {
            supportFragmentManager.beginTransaction().add(R.id.frame_layout, resultFragment!!).commitAllowingStateLoss()
        } else {
            supportFragmentManager.beginTransaction().show(resultFragment!!).commitAllowingStateLoss()
        }
    }

    private fun hideKeyFragment() {
        if (keyFragment != null) {
            supportFragmentManager.beginTransaction().hide(keyFragment!!).commitAllowingStateLoss()
        }
    }

    private fun initData() {
        if (keyFragment == null) {
            keyFragment = HotKeyFragment()
        }
        if (keyFragment?.isAdded == false) {
            supportFragmentManager.beginTransaction().add(R.id.frame_layout, keyFragment!!).commitAllowingStateLoss()
        } else {
            supportFragmentManager.beginTransaction().show(keyFragment!!).commitAllowingStateLoss()
        }
    }

    override fun onBackPressed() {
        if (resultFragment?.isHidden == true) {
            finish()
        } else {
            hideSearchResultFragment()
        }
    }

    private fun hideSearchResultFragment() {
        if (resultFragment?.isVisible == true) {
            supportFragmentManager.beginTransaction().hide(resultFragment!!).commitNowAllowingStateLoss()
        }
    }

}