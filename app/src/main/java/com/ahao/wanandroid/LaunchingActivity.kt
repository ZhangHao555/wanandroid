package com.ahao.wanandroid

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.ahao.wanandroid.baseview.BaseActivity
import com.ahao.wanandroid.user.LoginActivity

class LaunchingActivity : AppCompatActivity() {
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