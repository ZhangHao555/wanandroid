package com.ahao.wanandroid

import com.ahao.wanandroid.common.HttpConstant
import com.ahao.wanandroid.util.Preference

class UserRepository {

    companion object{
        val userToken: String by Preference(HttpConstant.TOKEN_PASS,"")
    }
}