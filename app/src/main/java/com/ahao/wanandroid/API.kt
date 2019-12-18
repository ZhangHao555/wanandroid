package com.ahao.wanandroid

object API {
    const val BASE_URL = "https://www.wanandroid.com/"
    const val LOGIN = "user/login"
    const val REGISTER = "user/register"
    const val LOG_OUT = "user/logout/json"
    const val OFFICIAL_ACCOUNT_TAB = "wxarticle/chapters/json"
    const val BANNER = "banner/json"
    const val MAIN_PAGE_LIST = "article/list/{page}/json"
    const val PROJECT_CATEGORY = "project/tree/json"
    const val PROJECT_LIST = "project/list/{page}/json"
    const val SERIES_TOPIC_CATEGORY = "tree/json"
    const val SERIES_TOPIC_LIST = "article/list/{page}/json"
    const val COLLECT = "lg/collect/{id}/json"
    const val CANCEL_COLLECT = "lg/uncollect_originId/{id}/json"
    const val COLLECTION_LIST = "lg/collect/list/{page}/json"
    const val NAV_LIST = "navi/json"
}