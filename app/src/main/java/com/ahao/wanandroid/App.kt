package com.ahao.wanandroid

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy


class App : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }


    override fun onCreate() {
        super.onCreate()
        context = this
        CookieHandler.setDefault(CookieManager(null, CookiePolicy.ACCEPT_ALL))
    }
}
