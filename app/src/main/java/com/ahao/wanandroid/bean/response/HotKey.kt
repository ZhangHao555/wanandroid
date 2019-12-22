package com.ahao.wanandroid.bean.response

import java.io.Serializable

data class HotKey(var id: Int = 0,
                  var link: String = "",
                  var name: String = "",
                  var order: Int = 0,
                  var visible: Int = 0) : Serializable