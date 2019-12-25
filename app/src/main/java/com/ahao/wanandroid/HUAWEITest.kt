package com.ahao.wanandroid

import android.os.Bundle
import com.ahao.wanandroid.baseview.BaseActivity
import kotlinx.android.synthetic.main.activity_huawei_test.*
import android.text.TextUtils
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.agconnect.config.AGConnectServicesConfig
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.ahao.wanandroid.util.loge


class HUAWEITest : BaseActivity() {
    override fun getResLayoutId() = R.layout.activity_huawei_test

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        get_token.setOnClickListener {
            getToken()
        }
    }

    private fun getToken() {
        loge("get token: begin")

        // get token
        object : Thread() {
            override fun run() {
                try {
                    val appId = AGConnectServicesConfig.fromContext(this@HUAWEITest).getString("client/app_id")
                    val pushtoken = HmsInstanceId.getInstance(this@HUAWEITest).getToken(appId, "HCM")
                    if (!TextUtils.isEmpty(pushtoken)) {
                        loge("get token: begin")
                        loge("get token:$pushtoken")
                    }
                } catch (e: Exception) {
                    loge("getToken failed, $e")
                }
            }
        }.start()
    }
}