package com.ahao.wanandroid

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import android.util.Log
import com.ahao.wanandroid.bean.request.LoginRequest
import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class LoginActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.e("ss","dddddd1111111")
        initEvent()
    }

    private fun initEvent() {
        login.run {
            setOnClickListener{
                ServiceManager.getService(WanAndroidHttpService::class.java)
                        ?.login(LoginRequest("ahaoisme","heyhaozi"))
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe(Consumer {
                            println(it)
                        }, Consumer {
                            print(it)
                        })
            }
        }
    }

    companion object{
        fun actionStart(context: Context){
            Intent(context,LoginActivity::class.java).run {
                context.startActivity(this)
            }
        }
    }

}