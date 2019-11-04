package com.ahao.wanandroid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.ahao.wanandroid.user.LoginActivity
import com.ahao.wanandroid.util.CacheUtil
import com.ahao.wanandroid.util.Preference

class LaunchingActivity : Activity() {

    private val userToken: String by Preference(Constant.TOKEN,"")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        jump()
    }

    private fun jump() {
        if (TextUtils.isEmpty(CacheUtil.getCachedString(userToken))) {
            LoginActivity.actionStart(this@LaunchingActivity)
        } else {
            MainActivity.newIntent(this)
        }
        finish()
    }
}