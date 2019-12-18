package com.ahao.wanandroid.dao

import com.ahao.wanandroid.API
import com.ahao.wanandroid.BaseUrl
import com.ahao.wanandroid.bean.response.*
import com.google.gson.JsonElement
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

@BaseUrl(API.BASE_URL)
interface WanAndroidHttpDao {

    @POST(API.LOGIN)
    @FormUrlEncoded
    fun login(@Field("username") username: String,
              @Field("password") password: String): Call<JsonResult<UserInfo>>

    @POST(API.REGISTER)
    @FormUrlEncoded
    fun register(@Field("username") username: String,
                 @Field("password") password: String,
                 @Field("repassword") repassword: String): Call<JsonResult<String?>>

    @GET(API.MAIN_PAGE_LIST)
    fun getMainPageList(@Path("page") page: Int): Call<JsonResult<HomePageListResponse>>

    @GET(API.PROJECT_CATEGORY)
    fun getProjectCategory(): Call<JsonResult<List<CategoryItem>>>

    @GET(API.PROJECT_LIST)
    fun getProjectList(@Path("page") page: Int, @Query("cid") category: Int): Call<JsonResult<HomePageListResponse>>

    @GET(API.SERIES_TOPIC_CATEGORY)
    fun getSeriesTopicCategory(): Call<JsonResult<List<CategoryItem>>>

    @GET(API.SERIES_TOPIC_LIST)
    fun getSeriesTopicList(@Path("page") page: Int, @Query("cid") category: Int): Call<JsonResult<HomePageListResponse>>

    @POST(API.COLLECT)
    fun collect(@Path("id") page: Int): Call<JsonResult<String?>>

    @POST(API.CANCEL_COLLECT)
    fun cancelCollect(@Path("id") page: Int): Call<JsonResult<String?>>

    @GET(API.COLLECTION_LIST)
    fun getCollectionList(@Path("page") page: Int): Call<JsonResult<CollectionListResponse>>

    @GET(API.NAV_LIST)
    fun getNavList(): Call<JsonResult<NavResponse>>
}