package com.ahao.wanandroid.baseview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ahao.wanandroid.rxbus.RxBus
import com.ahao.wanandroid.view.ProgressDialog
import io.reactivex.disposables.Disposable

open class BaseActivity : AppCompatActivity() {

    private lateinit var progressDialog: ProgressDialog
    private var disposable: Disposable? = null

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(this)

        disposable = RxBus.toObservable(this.javaClass.toString()).subscribe({
            if (it.data is Boolean) {
                if (it.data) {
                    progressDialog.show()
                } else {
                    progressDialog.hide()
                }
            }
        }, {

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        disposable = null
    }
}