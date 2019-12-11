package com.ahao.wanandroid.bean.response

data class HomePageListResponse(var curPage: Int?,
                                var datas: MutableList<Item>,
                                var offset: Int,
                                var over: Boolean,
                                var pageCount: Int,
                                var size: Int,
                                var total: Int) {

    data class Item(var apkLink: String,
                    var audit: Int,
                    var author: String,
                    var chapterId: Int,
                    var chapterName: String,
                    var collect: Boolean,
                    var courseId: Int,
                    var desc: String,
                    var envelopePic: String,
                    var fresh: Boolean,
                    var id: Int,
                    var link: String,
                    var niceDate: String,
                    var shareUser : String,
                    var origin: String,
                    var prefix: String,
                    var projectLink: String,
                    var publishTime: Long,
                    var superChapterId: Long,
                    var superChapterName: String,
                    var tags: MutableList<Tag>,
                    var title: String,
                    var type: Int,
                    var userId: Int,
                    var visible: Int,
                    var zan: Int)

    data class Tag(var name: String, var url: String)
}