package com.ahao.wanandroid.bean.response

data class ProjectCategoryItem(var children: MutableList<ProjectCategoryItem>,
                               var courseId: Int,
                               var id: Int,
                               var name: String,
                               var order: Int,
                               var parentChapterId: Int,
                               var userControlSetTop: Boolean,
                               var visible: Int
)

