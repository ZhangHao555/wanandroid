package com.ahao.wanandroid.view

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import com.ahao.wanandroid.R
import com.ahao.wanandroid.util.dp2px

class ProgressDialog(context: Context) : Dialog(context) {
    private val loadingView = LoadingView(context).apply{
        setBackgroundResource(R.drawable.bg_progress_dialog)
    }

    init {
        setContentView(loadingView, ViewGroup.LayoutParams(dp2px(100f),dp2px(80f)))
        if (this.window != null) {
            this.window!!.setBackgroundDrawable(ColorDrawable(0))
            this.window!!.setDimAmount(0.0f)
        }

        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        loadingView.startAnimation()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        loadingView.stopAnimation()
    }


}