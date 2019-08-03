package com.ahao.wanandroid

import com.ahao.wanandroid.bean.request.LoginRequest
import com.ahao.wanandroid.bean.response.JsonResult
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

@BaseUrl(API.BASE_URL)
interface WanAndroidHttpDao {

    @POST(API.LOGIN)
    fun login(@Body request: LoginRequest) : Observable<JsonResult<String>>
}