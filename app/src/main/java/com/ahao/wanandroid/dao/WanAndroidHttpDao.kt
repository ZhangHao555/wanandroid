package com.ahao.wanandroid.dao

import com.ahao.wanandroid.API
import com.ahao.wanandroid.BaseUrl
import com.ahao.wanandroid.bean.request.LoginRequest
import com.ahao.wanandroid.bean.request.RegisterRequest
import com.ahao.wanandroid.bean.response.JsonResult
import com.ahao.wanandroid.bean.response.MainPageListResponse
import com.ahao.wanandroid.bean.response.UserInfo
import com.google.gson.JsonElement
import io.reactivex.Observable
import retrofit2.http.*

@BaseUrl(API.BASE_URL)
interface WanAndroidHttpDao {

    @POST(API.LOGIN)
    @FormUrlEncoded
    fun login(@Field("username") username: String?,
              @Field("password") password: String?): Observable<JsonResult<UserInfo>>

    @POST(API.REGISTER)
    @FormUrlEncoded
    fun register(@Field("username") username: String?,
                 @Field("password") password: String?,
                 @Field("repassword") repassword: String?): Observable<JsonResult<JsonElement>>

    @GET(API.MAIN_PAGE_LIST)
    fun getMainPageList(@Path("page") page:Int):Observable<JsonResult<MainPageListResponse>>
}