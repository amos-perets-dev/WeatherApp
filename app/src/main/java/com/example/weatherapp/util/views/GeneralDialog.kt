package com.example.weatherapp.util.views

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.text.Html
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.example.weatherapp.R
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.general_dialog_layout.*
import java.util.concurrent.TimeUnit

class GeneralDialog(
    context_: Context,
    title: String? = ""
) :
    Dialog(context_, R.style.GeneralAlertDialog) {

    init {

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
        setContentView(R.layout.general_dialog_layout)

        val displayMetrics = context.resources.displayMetrics
        val width = (displayMetrics.widthPixels * 0.8).toInt()
        val height = (displayMetrics.heightPixels * 0.4).toInt()
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(window?.attributes)
        lp.width = width
        lp.height = height
        window?.attributes = lp

        window?.attributes?.windowAnimations = R.style.AnimationGeneralDialog

        noBtn.setOnClickListener { closeDialog() }

        dialogTitle.text = Html.fromHtml(title)
    }

    fun getOkClick(): Observable<Any> =
        RxView.clicks(yesBtn)
            .doOnNext {
                noBtn.visibility = View.GONE
                loader.animate().alpha(1F).withStartAction { yesBtn.text = "" }.setDuration(200)
                    .start()
            }
            .delay(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())



    override fun show() {
        if (!isShowing) {
            super.show()
        }
    }

    fun closeDialog() {
        dismiss()
    }

    class Builder {

        private var mTitle: String? = ""

        private var mContext: Activity? = null

        fun setContext(mContext: Activity) = apply { this.mContext = mContext }
        fun setTitle(mTitle: String?) = apply { this.mTitle = mTitle }

        fun build() = mContext?.let { GeneralDialog(it, mTitle) }
    }
}
