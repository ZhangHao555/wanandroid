package com.ahao.wanandroid.search

import com.ahao.wanandroid.bean.response.HotKey
import com.ahao.wanandroid.rxbus.Message
import com.ahao.wanandroid.rxbus.RxBus
import com.ahao.wanandroid.util.CacheUtil
import com.google.gson.reflect.TypeToken

class HistoryKeyRepository {

    companion object {
        private const val CACHE_KEY = "SEARCH_HISTORY"

        val historyKeys: ArrayList<HotKey> by lazy {
            val type = object : TypeToken<List<HotKey>>() {}.type
            val result: ArrayList<HotKey> = CacheUtil.getCachedObject(CACHE_KEY, type)
                    ?: ArrayList()
            result
        }

        @Synchronized fun addHistoryKey(key: HotKey) {
            val find = historyKeys.find { it.name == key.name }
            if (find != null) {
                historyKeys.remove(find)
            }
            historyKeys.add(0, key)
            if(historyKeys.size > 30){
                historyKeys.removeAt(30)
            }
            CacheUtil.cacheObject(CACHE_KEY, historyKeys)
            RxBus.post(Message(HotKeyFragment.REFRESH_HISTORY_CODE, key))
        }

        @Synchronized fun deleteAllHistoryKey() {
            historyKeys.clear()
            CacheUtil.cacheObject(CACHE_KEY, historyKeys)
            RxBus.post(Message(HotKeyFragment.REFRESH_HISTORY_CODE, null))
        }
    }
}