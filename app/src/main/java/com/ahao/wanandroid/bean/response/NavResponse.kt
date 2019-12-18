package com.ahao.wanandroid.bean.response

data class NavResponse(var cid: Int, var name: String, var articles: MutableList<Article>) {

    data class Article(var apkLink: String,
                       var audit: Int,
                       var author: String,
                       var chapterId: Int,
                       var chapterName: String,
                       var collect: Boolean,
                       var courseId: Int,
                       var envelopePic: String,
                       var fressh: Boolean,
                       var id: Int,
                       var link: String,
                       var niceDate: String,
                       var niceShareDate: String,
                       var origin: String,
                       var publishTime: Long,
                       var tile: String,
                       var type: Int,
                       var zan: Int)

}