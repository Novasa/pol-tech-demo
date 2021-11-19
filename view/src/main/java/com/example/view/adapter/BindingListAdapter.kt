package com.example.view.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BindingListAdapter<TItem : Any, TViewHolder> : ListAdapter<TItem, TViewHolder>
        where TViewHolder : RecyclerView.ViewHolder,
              TViewHolder : BindingViewHolder<*, TItem> {

    constructor(diffCallback: DiffUtil.ItemCallback<TItem>) : super(diffCallback)
    constructor(config: AsyncDifferConfig<TItem>) : super(config)

    override fun getItemViewType(position: Int): Int = getItemViewType(position, getItem(position))
    open fun getItemViewType(position: Int, item: TItem): Int = super.getItemViewType(position)

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TViewHolder {
        return onCreateViewHolder(parent.context, parent, viewType).also { holder ->
            (holder as? Attachable)?.onCreate()
        }
    }

    abstract fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): TViewHolder

    override fun onViewAttachedToWindow(holder: TViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttach()
    }

    override fun onViewDetachedFromWindow(holder: TViewHolder) {
        holder.onDetach()
        super.onViewDetachedFromWindow(holder)
    }

    override fun onBindViewHolder(holder: TViewHolder, position: Int) {
        holder.onBind(position, getItem(position))
    }

    override fun onBindViewHolder(holder: TViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            holder.onBind(position, getItem(position), payloads)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }
}
