package com.ahao.wanandroid.baseview

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.ahao.wanandroid.R
import com.ahao.wanandroid.rxbus.RxBus
import com.ahao.wanandroid.util.getColorInActivity
import com.ahao.wanandroid.view.ProgressDialog
import io.reactivex.disposables.Disposable

@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null
    private var disposable: Disposable? = null
    protected lateinit var statusView: View
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
        window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.decorView.fitsSystemWindows = true
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT

        val contentView = LayoutInflater.from(this).inflate(getResLayoutId(), null, false)
        statusView = View(this).apply { setBackgroundColor(getColorInActivity(R.color.shallow_blue)) }
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            addView(statusView, ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight())
            addView(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
        setContentView(container)
    }

    private fun FragmentActivity.getStatusBarHeight(): Int {
        var result = 0
        //获取状态栏高度的资源id
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    abstract fun getResLayoutId(): Int

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

    protected fun setStatusBarColor(color: Int) {
        statusView.setBackgroundColor(color)
    }

    protected fun requestPermission(permission : Array<String>){
        val notGrantedPermission = mutableListOf<String>()

        for (s in permission) {
            val checkSelfPermission = ActivityCompat.checkSelfPermission(this, s)
            if(checkSelfPermission == PERMISSION_DENIED){
                notGrantedPermission.add(s)
            }
        }
        if(notGrantedPermission.isNotEmpty()){
            ActivityCompat.requestPermissions(this,notGrantedPermission.toTypedArray(),11)
        }
    }



}