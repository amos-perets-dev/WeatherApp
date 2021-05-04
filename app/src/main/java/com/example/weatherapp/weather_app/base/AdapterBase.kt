package com.example.weatherapp.weather_app.base

import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

abstract class AdapterBase<Model>(private var items: List<Model>) :
    RecyclerView.Adapter<ViewHolderBase<Model>>() {
    constructor() : this(Collections.emptyList())

    private val setVH = HashSet<ViewHolderBase<Model>>()

    protected fun getDataList() = items

    private lateinit var recyclerView: RecyclerView

    fun updateList(list: List<Model>?) {
        if(list != null){
            this.items = ArrayList(list)
            notifyDataSetChanged()
        }
    }

    open fun removeAt(position: Int) {
        (this.items as ArrayList).removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBase<Model> {
        return addViewHolder(getViewHolder(parent, viewType))
    }

    override fun onBindViewHolder(holder: ViewHolderBase<Model>, position: Int) {

        val model = getItemByPos(position) ?: getItem(position)

        holder.bindData(model)
    }

    private fun getItemByPos(position: Int): Model {
        return items[position]
    }

    open fun dispose() {
        setVH.forEach { it.destroy() }
    }

    abstract fun getItem(position: Int): Model

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBase<Model>

    override fun getItemCount(): Int = items.size

    override fun onViewRecycled(holder: ViewHolderBase<Model>) {
        super.onViewRecycled(holder)
        holder.destroy()
    }

    @CallSuper
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
    }

    protected open fun addViewHolder(viewHolder: ViewHolderBase<Model>): ViewHolderBase<Model> {
        setVH.add(viewHolder)
        return viewHolder
    }

}
