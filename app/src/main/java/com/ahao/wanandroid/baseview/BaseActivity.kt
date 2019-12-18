package com.ahao.wanandroid.baseview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ahao.wanandroid.rxbus.RxBus
import com.ahao.wanandroid.view.ProgressDialog
import io.reactivex.disposables.Disposable

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null
    private var disposable: Disposable? = null

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disposable = RxBus.toObservable(this.javaClass.toString()).subscribe({
            if (it.data is Boolean) {
                if (it.data) {
                    showProgressDialog()
                } else {
                    hideProgressDialog()
                }
            }
        }, {

        })
    }

    protected fun hideProgressDialog() {
        progressDialog?.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        disposable = null
    }

    protected fun showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(this)
        }
        progressDialog?.show()
    }
}