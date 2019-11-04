package com.ahao.wanandroid

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ahao.wanandroid.bean.response.MainPageListResponse
import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService
import com.ahao.wanandroid.util.ToastUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    var dataSource: ArrayList<MainPageListResponse.Item> = ArrayList()
    var response: MainPageListResponse? = null
    var adapter: MainListAdapter = MainListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initEvent()
        getMainPageList()

    }

    private fun initData() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

    }

    private fun initEvent() {
        adapter.clickListener = { view: View, position: Int ->
            ToastUtil.toast("click $position")
        }
    }

    private fun getMainPageList() {
        ServiceManager.getService(WanAndroidHttpService::class.java)
                ?.getMainPageList(0)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    if (it.isOK()) {
                        notifyDataSetHasGet(it.data)
                    } else {
                        print("数据请求错误")
                    }
                    Log.e("","")
                    print(it)
                }, {
                    print(it)
                })
    }

    private fun notifyDataSetHasGet(data: MainPageListResponse) {
        response = data
        dataSource.apply {
            clear()
            addAll(response?.datas ?: ArrayList())
        }
        adapter.notifyDataSetChanged()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    inner class MainListAdapter : RecyclerView.Adapter<MainHolder>() {
        var clickListener: ((View, Int) -> Unit)? = null

        override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MainHolder {
            val view = LayoutInflater.from(this@MainActivity).inflate(R.layout.view_item_main_list, viewGroup, false)
            val holder = MainHolder(view)
            view.setOnClickListener {
                clickListener?.invoke(it, holder.adapterPosition)

            }
            return holder
        }

        override fun getItemCount(): Int {
            return dataSource.size
        }

        override fun onBindViewHolder(holder: MainHolder, position: Int) {
            holder.title.text = dataSource[position].title
        }

    }

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.title)
    }
}
