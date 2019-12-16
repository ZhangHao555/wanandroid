package com.ahao.wanandroid.common

import com.ahao.wanandroid.util.Preference
import okhttp3.Interceptor
import okhttp3.Response

class CookieInterceptor : Interceptor {

    private val TAG: String = "CookieInterceptor"
    private var jsessionId: String? = null
    private var loginUserName: String by Preference(HttpConstant.LOGIN_USER_NAME, "", true)
    private var token: String by Preference(HttpConstant.TOKEN_PASS, "", true)

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder().apply {
            addHeader(HttpConstant.COOKIE, combineCookie())
        }.build()


        val response = chain.proceed(request)
        response.headers(HttpConstant.SET_COOKIE).forEach { e ->
            val regex = Regex(pattern = "(.*?=.*?;)")
            val result = regex.find(e)
            if (result != null) {
                val string = result.value.dropLast(1)
                val split = string.split("=")
                val key = split[0]
                val value = split[1]

                when (key) {
                    HttpConstant.TOKEN_PASS -> token = value
                    HttpConstant.JSESSIONID -> jsessionId = value
                    HttpConstant.LOGIN_USER_NAME -> loginUserName = value
                }
            }
        }
        return response
    }

    private fun combineCookie(): String {
        var text = ""
        if (jsessionId != null) {
            text += HttpConstant.JSESSIONID + "=$jsessionId;"
        }

        if (token.isNotEmpty()) {
            text += HttpConstant.TOKEN_PASS + "=$token;"
        }

        if (loginUserName.isNotEmpty()) {
            text += HttpConstant.LOGIN_USER_NAME + "=$loginUserName;"
        }

        return text
    }
}