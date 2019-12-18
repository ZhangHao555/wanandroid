package com.ahao.wanandroid.service

import com.ahao.wanandroid.bean.request.LoginRequest
import com.ahao.wanandroid.bean.request.RegisterRequest
import com.ahao.wanandroid.bean.response.*
import com.ahao.wanandroid.dao.WanAndroidHttpDao
import com.ahao.wanandroid.util.isNetWorkConnected
import kotlinx.coroutines.*
import kotlin.Exception

class WanAndroidHttpService {

    fun login(request: LoginRequest) = API<UserInfo>().execute {
        getDao(WanAndroidHttpDao::class.java).login(request.username, request.password).execute().body()!!
    }

    fun register(request: RegisterRequest) = API<String?>().execute {
        getDao(WanAndroidHttpDao::class.java).register(request.username, request.password, request.repassword).execute().body()!!
    }

    fun getHomePageList(pageIndex: Int) = API<HomePageListResponse>().execute {
        getDao(WanAndroidHttpDao::class.java).getMainPageList(pageIndex).execute().body()!!
    }

    fun getProjectCategory() = API<List<CategoryItem>>().execute {
        getDao(WanAndroidHttpDao::class.java).getProjectCategory().execute().body()!!
    }

    fun getProjectList(pageIndex: Int, categoryId: Int) = API<HomePageListResponse>().execute {
        getDao(WanAndroidHttpDao::class.java).getProjectList(pageIndex, categoryId).execute().body()!!
    }

    fun getSeriesTopicCategory() = API<List<CategoryItem>>().execute {
        getDao(WanAndroidHttpDao::class.java).getSeriesTopicCategory().execute().body()!!
    }

    fun getSeriesTopicList(pageIndex: Int, categoryId: Int) = API<HomePageListResponse>().execute {
        getDao(WanAndroidHttpDao::class.java).getSeriesTopicList(pageIndex, categoryId).execute().body()!!
    }

    fun collect(id: Int) = API<String?>().execute {
        getDao(WanAndroidHttpDao::class.java).collect(id).execute().body()!!
    }

    fun cancelCollect(id: Int) = API<String?>().execute {
        getDao(WanAndroidHttpDao::class.java).cancelCollect(id).execute().body()!!
    }

    fun getCollectionList(pageIndex: Int) = API<CollectionListResponse>().execute {
        getDao(WanAndroidHttpDao::class.java).getCollectionList(pageIndex).execute().body()!!
    }

    private fun <T> getDao(clazz: Class<T>): T {
        return HttpManager.getDao(clazz)
    }

    class API<T> {
        var onLoading: (() -> Unit)? = null
        var onSuccess: ((result: JsonResult<T>) -> Unit)? = null
        var onError: ((message: String, code: ErrorCode) -> Unit)? = null

        private val handler = CoroutineExceptionHandler { _, exception ->
            onError?.invoke(exception.message ?: "未知错误", ErrorCode.EXCEPTION)
        }

        fun execute(executor: suspend () -> JsonResult<T>): API<T> {
            try {
                GlobalScope.launch(Dispatchers.Main + handler) {
                    onLoading?.invoke()
                    val result = withContext(Dispatchers.IO) {
                        executor.invoke()
                    }
                    if (result.isOK()) {
                        onSuccess?.invoke(result)
                    } else {
                        onError?.invoke(result.errorMsg, ErrorCode.API_ERROR)
                    }
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

        fun onSuccess(onSuccess: (result: JsonResult<T>) -> Unit): API<T> {
            this.onSuccess = onSuccess
            return this
        }

        fun onError(onError: ((message: String, code: ErrorCode) -> Unit)): API<T> {
            this.onError = onError
            return this
        }
    }

    enum class ErrorCode {
        EXCEPTION, NET_ERROR, API_ERROR
    }
}