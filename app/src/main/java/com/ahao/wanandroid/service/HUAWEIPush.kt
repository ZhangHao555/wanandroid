package com.ahao.wanandroid.service

import com.ahao.wanandroid.util.loge
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.RemoteMessage


class HUAWEIPush : HmsMessageService() {

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        loge("receive token:$s")
    }

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)

        loge("onMessageReceived..........")
    }
}