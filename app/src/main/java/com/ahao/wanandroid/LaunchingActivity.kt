package com.ahao.wanandroid

import android.os.Bundle
import android.text.TextUtils
import com.ahao.wanandroid.baseview.BaseActivity
import com.ahao.wanandroid.user.LoginActivity

class LaunchingActivity : BaseActivity() {
    override fun getResLayoutId() = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        jump()
    }

    private fun jump() {
        if (TextUtils.isEmpty(UserRepository.userToken)) {
            LoginActivity.actionStart(this@LaunchingActivity)
        } else {
            MainActivity.newIntent(this).run {
                startActivity(this)
            }
        }
        finish()
    }
}