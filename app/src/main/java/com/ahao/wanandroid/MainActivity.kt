package com.ahao.wanandroid

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.ahao.wanandroid.bean.response.MainPageListResponse
import com.ahao.wanandroid.service.ServiceManager
import com.ahao.wanandroid.service.WanAndroidHttpService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var dataSource : MutableList<MainPageListResponse.Item>? = null

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
        }

    }

    private fun initEvent() {


    }

    private fun getMainPageList() {
        ServiceManager.getService(WanAndroidHttpService::class.java)
                ?.getMainPageList(0)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    print(it)
                },{
                    print(it)
                })
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    class MainListAdapter: RecyclerView.Adapter<MainHolder>() {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MainHolder {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getItemCount(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onBindViewHolder(p0: MainHolder, p1: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
