package com.ahao.wanandroid.bean.response

import java.io.Serializable

data class CategoryItem(var children: MutableList<CategoryItem>,
                        var courseId: Int,
                        var id: Int,
                        var name: String,
                        var order: Int,
                        var parentChapterId: Int,
                        var userControlSetTop: Boolean,
                        var visible: Int
):Serializable

