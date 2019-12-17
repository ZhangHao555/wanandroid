package com.ahao.wanandroid.service

import com.ahao.wanandroid.bean.request.LoginRequest
import com.ahao.wanandroid.bean.request.RegisterRequest
import com.ahao.wanandroid.bean.response.*
import com.ahao.wanandroid.dao.WanAndroidHttpDao
import com.ahao.wanandroid.util.isNetWorkConnected
import com.google.gson.JsonElement
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class WanAndroidHttpService {

    fun login(request: LoginRequest): Observable<JsonResult<UserInfo>> {
        return getDao(WanAndroidHttpDao::class.java).login(request.username, request.password)
    }

    fun register(request: RegisterRequest): Observable<JsonResult<JsonElement>> {
        return getDao(WanAndroidHttpDao::class.java).register(request.username, request.password, request.repassword)
    }

    suspend fun getHomePageList(pageIndex: Int): JsonResult<HomePageListResponse>? = withContext(Dispatchers.IO) {
        try {
            getDao(WanAndroidHttpDao::class.java).getMainPageList(pageIndex).execute().body()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getProjectCategory(): JsonResult<List<CategoryItem>>? = withContext(Dispatchers.IO) {
        getDao(WanAndroidHttpDao::class.java).getProjectCategory().execute().body()
    }

    suspend fun getProjectList(pageIndex: Int, categoryId: Int): JsonResult<HomePageListResponse>? = withContext(Dispatchers.IO) {
        try {
            getDao(WanAndroidHttpDao::class.java).getProjectList(pageIndex, categoryId).execute().body()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getSeriesTopicCategory(): JsonResult<List<CategoryItem>>? = withContext(Dispatchers.IO) {
        try {
            getDao(WanAndroidHttpDao::class.java).getSeriesTopicCategory().execute().body()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getSeriesTopicList(pageIndex: Int, categoryId: Int): JsonResult<HomePageListResponse>? = withContext(Dispatchers.IO) {
        try {
            getDao(WanAndroidHttpDao::class.java).getSeriesTopicList(pageIndex, categoryId).execute().body()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun collect(id: Int): JsonResult<String>? = withContext(Dispatchers.IO) {
        try {
            getDao(WanAndroidHttpDao::class.java).collect(id).execute().body()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun cancelCollect(id: Int): JsonResult<String>? = withContext(Dispatchers.IO) {
        try {
            getDao(WanAndroidHttpDao::class.java).cancelCollect(id).execute().body()
        } catch (e: Exception) {
            null
        }
    }

    fun getCollectionList(pageIndex: Int): API<CollectionListResponse> {
        return API<CollectionListResponse>().execute {
            getDao(WanAndroidHttpDao::class.java).getCollectionList(pageIndex).execute().body()
        }
    }

    private fun <T> getDao(clazz: Class<T>): T {
        return HttpManager.getDao(clazz)
    }

    class API<T> {
        var onLoading: (() -> Unit)? = null
        var onSuccess: ((result: JsonResult<T?>?) -> Unit)? = null
        var onError: ((message: String, code: ErrorCode) -> Unit)? = null

        fun execute(executor: suspend () -> JsonResult<T?>?): API<T> {
            try {
                GlobalScope.launch(Dispatchers.Main) {
                    onLoading?.invoke()
                    val result = executor.invoke()
                    onSuccess?.invoke(result)
                }
            } catch (e: Exception) {
                if (isNetWorkConnected()) {
                    onError?.invoke("网络错误", ErrorCode.NET_ERROR)
                } else {
                    onError?.invoke(e.message ?: "", ErrorCode.EXCEPTION)
                }
            }
            return this
        }

        fun onLoading(onLoading: (() -> Unit)): API<T> {
            this.onLoading = onLoading
            return this
        }

        fun onSuccess(onSuccess: (result: JsonResult<T?>?) -> Unit): API<T> {
            this.onSuccess = onSuccess
            return this
        }

        fun onError(onError: ((message: String, code: ErrorCode) -> Unit)): API<T> {
            this.onError = onError
            return this
        }

    }


    enum class ErrorCode {
        EXCEPTION, NET_ERROR
    }
}