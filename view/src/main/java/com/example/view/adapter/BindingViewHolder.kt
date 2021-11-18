package com.example.view.adapter

import androidx.databinding.ViewDataBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

interface BindingViewHolder<TBinding : ViewDataBinding, TItem : Any> : Attachable {

    val binding: TBinding

    fun onBind(position: Int, item: TItem, payloads: MutableList<Any>) {
        onBind(position, binding, item, payloads)
        binding.executePendingBindings()
    }

    fun onBind(position: Int, item: TItem) {
        onBind(position, binding, item)
        binding.executePendingBindings()
    }

    fun onBind(position: Int, binding: TBinding, item: TItem, payloads: MutableList<Any>) = onBind(position, binding, item)
    fun onBind(position: Int, binding: TBinding, item: TItem)

}
