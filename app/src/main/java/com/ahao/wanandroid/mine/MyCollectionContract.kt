package com.ahao.wanandroid.mine

import com.ahao.wanandroid.baseview.ListViewInterface

interface MyCollectionContract {
    interface View<T> : ListViewInterface<T> {
        fun notifyDataSetChanged()
        fun notifyItemDeleted(position : Int)
    }

    interface Presenter{
        fun cancelCollection(id:Int)
    }

}