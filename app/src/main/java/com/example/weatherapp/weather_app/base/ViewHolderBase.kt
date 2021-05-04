package com.example.weatherapp.weather_app.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable

abstract class ViewHolderBase<Model>(parent: ViewGroup, @LayoutRes layout: Int) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        layout,
        parent,
        false
    )
) {
    protected val compositeDisposable = CompositeDisposable()
    abstract fun bindData(model : Model)
    abstract fun destroy()

}