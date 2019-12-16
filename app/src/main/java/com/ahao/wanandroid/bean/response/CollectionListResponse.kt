package com.ahao.wanandroid.bean.response

data class CollectionListResponse(var curPage: Int?,
                                  var datas: MutableList<Item>,
                                  var offset: Int,
                                  var over: Boolean,
                                  var pageCount: Int,
                                  var size: Int,
                                  var total: Int) {

    data class Item(
                    var author: String,
                    var chapterId: Int,
                    var chapterName: String,
                    var courseId: Int,
                    var desc: String,
                    var envelopePic: String,
                    var id: Int,
                    var link: String,
                    var niceDate: String,
                    var origin: String,
                    var originId: Int,
                    var publishTime: Long,
                    var title: String,
                    var userId: Int,
                    var visible: Int,
                    var zan: Int)
}