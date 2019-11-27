package com.ahao.wanandroid.bean.response

class UserInfo(var admin: Boolean,
               var chapterTops: MutableList<Int>,
               var chllectIds: MutableList<Int>,
               var email: String,
               var icon: String,
               var id: String,
               var nickname: String,
               var password: String,
               var publicName: String,
               var token: String,
               var type: Int,
               var username: String)