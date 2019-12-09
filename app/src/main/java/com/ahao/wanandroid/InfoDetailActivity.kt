package com.ahao.wanandroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.webkit.*
import com.ahao.wanandroid.baseview.BaseActivity
import com.ahao.wanandroid.util.ToastUtil
import kotlinx.android.synthetic.main.activity_info_detail.*

class InfoDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_detail)
        initData()
    }

    private fun initData() {
        val url = intent.getStringExtra(URL_PARAM)
        if (!TextUtils.isEmpty(url)) {


            web_view.apply {
                loadUrl(url)
                webChromeClient = object : WebChromeClient() {
                    override fun onReceivedTitle(view: WebView?, title: String?) {
                        if (!TextUtils.isEmpty(title)) {
                            ToastUtil.toast(title!!)
                        }
                    }


                }
            }

            web_view.settings.javaScriptEnabled = true
        }
    }

    companion object {
        private const val URL_PARAM = "urlParams"
        fun newIntent(context: Context?, url: String) = Intent(context, InfoDetailActivity::class.java).also {
            it.putExtra(URL_PARAM, url)
        }
    }
}