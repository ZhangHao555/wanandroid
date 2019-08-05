package com.ahao.wanandroid.bean.response

class JsonResult<T>(var data: T, var errorCode: Int, var errorMsg: String) {

    override fun toString(): String {
        return "JsonResult(data=$data, errorCode=$errorCode, errorMsg='$errorMsg')"
    }

    fun isOK() = errorCode == 0
}