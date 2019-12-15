package com.ahao.wanandroid

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ahao.wanandroid.homepage.HomePageFragment
import com.ahao.wanandroid.mine.MineFragment
import com.ahao.wanandroid.projeect.ProjectPageFragment
import com.ahao.wanandroid.seriestopic.SeriesTopicFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var fragments: List<Fragment> = listOf(HomePageFragment(), ProjectPageFragment(), SeriesTopicFragment(), MineFragment())
    private var currentFragment: Fragment? = null

    private val icons = arrayOf(arrayOf(R.mipmap.home_page_selected,
            R.mipmap.interview_guide_selected,
            R.mipmap.ask_every_day_selected,
            R.mipmap.my_page_selected),
            arrayOf(R.mipmap.home_page_unselected,
                    R.mipmap.interview_guide_unselected,
                    R.mipmap.ask_every_day_unselected,
                    R.mipmap.my_page_unselected))

    private lateinit var buttons: Array<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initEvent()
    }

    private fun initData() {
        buttons = arrayOf(home_page, interview_guide, ask_every_day, my_page)
        switchFragment(fragments[0])
    }

    private fun initEvent() {
        buttons.forEach { it ->
            it.setOnClickListener {
                switchFragment(fragments[buttons.indexOf(it)])
            }
        }
    }

    private fun switchFragment(fragment: Fragment) {
        if (currentFragment == fragment) {
            return
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

    inner class Adapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.count()
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

}
