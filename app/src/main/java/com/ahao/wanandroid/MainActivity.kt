package com.ahao.wanandroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ahao.wanandroid.baseview.BaseActivity
import com.ahao.wanandroid.homepage.HomePageFragment
import com.ahao.wanandroid.mine.MineFragment
import com.ahao.wanandroid.nav.NavFragment
import com.ahao.wanandroid.projeect.ProjectPageFragment
import com.ahao.wanandroid.search.SearchActivity
import com.ahao.wanandroid.seriestopic.SeriesTopicFragment
import com.ahao.wanandroid.util.ToastUtil
import com.ahao.wanandroid.util.dp2px
import com.ahao.wanandroid.util.getColorWithApp
import com.ahao.wanandroid.util.loge
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_search_layout.*
import kotlinx.android.synthetic.main.view_title_layout.view.*


class MainActivity : BaseActivity() {

    companion object {
        private const val QUIT_INTERVAL = 1000 * 1000 * 1000

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun getResLayoutId() = R.layout.activity_main
    private var lastClickBackTime = 0L
    private var fragments: List<Fragment> = listOf(MineFragment(), ProjectPageFragment(), SeriesTopicFragment(), NavFragment(), HomePageFragment())
    private var currentFragment: Fragment? = null

    private val icons = arrayOf(arrayOf(R.mipmap.home_page_selected,
            R.mipmap.interview_guide_selected,
            R.mipmap.ask_every_day_selected,
            R.mipmap.ask_every_day_selected,
            R.mipmap.my_page_selected),
            arrayOf(R.mipmap.home_page_unselected,
                    R.mipmap.interview_guide_unselected,
                    R.mipmap.ask_every_day_unselected,
                    R.mipmap.ask_every_day_unselected,
                    R.mipmap.my_page_unselected))

    private lateinit var buttons: Array<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statusView.background = getDrawable(R.drawable.mine_page_header_bg)
        initData()
        initEvent()
    }

    private fun initData() {
        buttons = arrayOf(home_page, interview_guide, ask_every_day, nav_button, my_page)
        switchFragment(fragments[0])

        initTitleBar()
    }

    private fun initTitleBar() {
        setSearchTitleBar()
    }

    private fun setSearchTitleBar() {
        val searchView = LayoutInflater.from(this).inflate(R.layout.view_serach_title_bar, null, false).apply {
            findViewById<EditText>(R.id.search_edit_text).apply {
                isFocusable = false
                isFocusableInTouchMode = false

                setOnClickListener {
                    startActivity(Intent(this@MainActivity, SearchActivity::class.java))
                }
            }
            setOnClickListener {
                startActivity(Intent(this@MainActivity, SearchActivity::class.java))
            }
        }
        title_bar.setCenterView(searchView)

        val rightText = TextView(this).apply {
            textSize = 15f
            setTextColor(getColorWithApp(R.color.white))
            text = "搜索"

            setOnClickListener {
                startActivity(Intent(this@MainActivity, SearchActivity::class.java))
            }
        }
        title_bar.setRightView(rightText)
        (rightText.layoutParams as ViewGroup.MarginLayoutParams).rightMargin = dp2px(10f)

        title_bar.left_container.setOnClickListener {
            back()
        }
    }


    private fun back() {
        if (System.nanoTime() - lastClickBackTime < QUIT_INTERVAL) {
            finish()
        } else {
            ToastUtil.toast("再按一次退出")
            lastClickBackTime = System.nanoTime()
        }
    }

    override fun onBackPressed() {
        back()
    }

    private fun setSettingTitleBar() {
        title_bar.setCenterView("个人中心")
        title_bar.setRightView(ImageView(this).apply {
            setImageResource(R.mipmap.icon_setting)
        })
    }

    private fun initEvent() {
        buttons.forEach { it ->
            it.setOnClickListener {
                switchFragment(fragments[buttons.indexOf(it)])
            }
        }

        view_bg.setOnClickListener {
            startActivity(Intent(this@MainActivity, SearchActivity::class.java))
        }
    }

    private fun switchFragment(fragment: Fragment) {
        if (currentFragment == fragment) {
            return
        }

        if (fragment is MineFragment) {
            setSettingTitleBar()
        } else {
            setSearchTitleBar()
        }

        supportFragmentManager.fragments.forEach {
            if (it.isAdded && !it.isHidden) {
                supportFragmentManager.beginTransaction().hide(it).commit()
            }
        }

        if (!fragment.isAdded) {
            supportFragmentManager.beginTransaction().add(R.id.fragment_layout, fragment).commit()
        } else {
            supportFragmentManager.beginTransaction().show(fragment).commit()
        }

        currentFragment = fragment
        if (currentFragment != null) {
            val indexOf = fragments.indexOf(currentFragment!!)
            (buttons.indices).forEach {
                val drawable = if (it == indexOf) getDrawable(icons[0][it]) else getDrawable(icons[1][it])
                buttons[it].setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
            }
        }
    }



}
