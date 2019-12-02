package com.ahao.wanandroid

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ahao.wanandroid.homepage.HomePageFragment
import com.ahao.wanandroid.interview.InterviewPageFragment
import com.ahao.wanandroid.mine.MineFragment
import com.ahao.wanandroid.quetionandanswer.QuestionAndAnswerFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var fragments: List<Fragment> = listOf(HomePageFragment(), InterviewPageFragment(), QuestionAndAnswerFragment(), MineFragment())
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initEvent()

    }

    private fun initData() {
        switchFragment(fragments[0])
    }

    private fun initEvent() {
        text1.run {
            setOnClickListener {
                switchFragment(fragments[0])
            }
        }

        text2.run {
            setOnClickListener {
                switchFragment(fragments[1])
            }
        }

        text3.run {
            setOnClickListener {
                switchFragment(fragments[2])
            }
        }

        text4.run {
            setOnClickListener {
                switchFragment(fragments[3])
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
