package com.ahao.wanandroid.user

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.ahao.wanandroid.MainActivity
import kotlinx.android.synthetic.main.activity_register.*
import com.ahao.wanandroid.R
import com.ahao.wanandroid.bean.request.LoginRequest
import com.ahao.wanandroid.bean.request.RegisterRequest
import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService
import com.ahao.wanandroid.util.ToastUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.password
import kotlinx.android.synthetic.main.activity_login.username

class RegisterActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initEvent()
    }

    private fun initEvent() {
        register.run {
            setOnClickListener {
                if (validate()) {
                    ServiceManager.getService(WanAndroidHttpService::class.java)
                            ?.register(RegisterRequest().apply {
                                username = this@RegisterActivity.username.text.toString()
                                password = this@RegisterActivity.password.text.toString()
                                repassword = this@RegisterActivity.make_sure_password.text.toString()
                            })
                            ?.subscribeOn(Schedulers.io())
                            ?.observeOn(AndroidSchedulers.mainThread())
                            ?.subscribe({
                                if (it.isOK()) {
                                    with(MainActivity.newIntent(this@RegisterActivity)) {
                                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        this@RegisterActivity.startActivity(this)
                                    }
                                } else {
                                    ToastUtil.toast(it.errorMsg)
                                }
                            }, {
                                ToastUtil.toast(it?.message ?: "未知错误")
                            })
                }

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
        if (TextUtils.isEmpty(make_sure_password.text.trim())) {
            ToastUtil.toast("确认密码不能为空")
            return false
        }
        if (password.text.toString() != make_sure_password.text.toString()) {
            ToastUtil.toast("密码不一致")
            return false
        }
        return true
    }

    companion object {
        fun actionStart(context: Context) {
            Intent(context, RegisterActivity::class.java).run {
                context.startActivity(this)
            }
        }
    }

}