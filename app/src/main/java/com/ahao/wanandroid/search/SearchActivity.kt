package com.ahao.wanandroid.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.ahao.wanandroid.R
import com.ahao.wanandroid.baseview.BaseActivity
import com.ahao.wanandroid.bean.response.HotKey
import com.ahao.wanandroid.rxbus.RxBus
import com.ahao.wanandroid.util.dp2px
import com.ahao.wanandroid.util.getColorWithApp
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class SearchActivity : BaseActivity() {
    override fun getResLayoutId() = R.layout.activity_search_result
    private var keyFragment: HotKeyFragment? = null
    private var resultFragment: SearchResultFragment? = null
    var disposable: Disposable? = null

    private var editText: EditText? = null

    companion object {
        const val ACTION_SEARCH = "ACTION_SEARCH"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statusView.background = getDrawable(R.drawable.mine_page_header_bg)
        initData()
        disposable = RxBus.toObservable(ACTION_SEARCH)
                .subscribe({
                    searchAction(it.data as HotKey)
                }, {

                })
    }

    private fun searchAction(data: HotKey?) {
        if (data?.name?.isNotEmpty() == true) {
            HistoryKeyRepository.addHistoryKey(data)
            hideKeyFragment()
            showSearchResultFragment(data)
        }
    }

    private fun initData() {
        showKeyFragment()
        setSearchTitleBar()
    }

    private fun setSearchTitleBar() {
        val searchView = LayoutInflater.from(this).inflate(R.layout.view_serach_title_bar, null, false).apply {
            editText = findViewById(R.id.search_edit_text)
            editText?.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH && editText?.text?.isNotEmpty() == true) {
                    searchAction(HotKey(name = editText?.text?.toString() ?: ""))
                    true
                } else {
                    false
                }
            }
        }

        title_bar.setCenterView(searchView)

        val rightText = TextView(this).apply {
            textSize = 15f
            setTextColor(getColorWithApp(R.color.white))
            text = "搜索"
        }
        title_bar.setRightView(rightText)
        (rightText.layoutParams as ViewGroup.MarginLayoutParams).rightMargin = dp2px(10f)
    }

    private fun showSearchResultFragment(data: HotKey) {
        if (resultFragment == null) {
            resultFragment = SearchResultFragment.newInstance(data.name)
        }
        if (resultFragment?.isAdded == false) {
            supportFragmentManager.beginTransaction().add(R.id.frame_layout, resultFragment!!).commitAllowingStateLoss()
        } else {
            supportFragmentManager.beginTransaction().show(resultFragment!!).commitAllowingStateLoss()
            resultFragment?.search(data.name)
        }
    }

    private fun hideKeyFragment() {
        if (keyFragment != null) {
            supportFragmentManager.beginTransaction().hide(keyFragment!!).commitAllowingStateLoss()
        }
    }


    private fun showKeyFragment() {
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
        if (keyFragment?.isHidden == false) {
            finish()
        } else {
            hideSearchResultFragment()
            showKeyFragment()
        }
    }

    private fun hideSearchResultFragment() {
        if (resultFragment?.isVisible == true) {
            supportFragmentManager.beginTransaction().hide(resultFragment!!).commitNowAllowingStateLoss()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_SEARCH) {
            editText?.onEditorAction(EditorInfo.IME_ACTION_SEARCH)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }


}