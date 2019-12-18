package com.ahao.wanandroid.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.ahao.wanandroid.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import com.ahao.wanandroid.R
import com.ahao.wanandroid.UserRepository
import com.ahao.wanandroid.baseview.BaseActivity
import com.ahao.wanandroid.bean.request.LoginRequest
import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService
import com.ahao.wanandroid.util.ToastUtil

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initEvent()
    }

    private fun initEvent() {
        login.run {
            setOnClickListener {
                if (validate()) {
                    ServiceManager.getService(WanAndroidHttpService::class.java)
                            ?.login(LoginRequest().apply {
                                username = this@LoginActivity.username.text.toString()
                                password = this@LoginActivity.password.text.toString()
                            })?.onLoading {
                                showProgressDialog()
                            }?.onError { message, _ ->
                                hideProgressDialog()
                                ToastUtil.toast(message)
                            }?.onSuccess {
                                hideProgressDialog()
                                with(MainActivity.newIntent(this@LoginActivity)) {
                                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(this)
                                }
                            }
                }
            }
        }

        register.run {
            setOnClickListener {
                RegisterActivity.actionStart(this@LoginActivity)
            }
        }
    }

    private fun validate(): Boolean {
        if (TextUtils.isEmpty(username.text?.trim())) {
            ToastUtil.toast("用户名不能为空")
            return false
        }
        if (TextUtils.isEmpty(password.text?.trim())) {
            ToastUtil.toast("密码不能为空")
            return false
        }
        return true
    }

    companion object {
        fun actionStart(context: Context) {
            Intent(context, LoginActivity::class.java).run {
                context.startActivity(this)
            }
        }
    }

}