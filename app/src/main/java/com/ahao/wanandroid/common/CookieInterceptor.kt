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
            jsessionId?.let { addHeader(HttpConstant.JSESSIONID, it) }

            if (loginUserName.isNotBlank()) {
                addHeader(HttpConstant.LOGIN_USER_NAME, loginUserName)
            }
            if (token.isNotBlank()) {
                addHeader(HttpConstant.TOKEN_PASS, token)
            }
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
}