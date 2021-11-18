package com.example.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

open class CoroutineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), Attachable {

    private var _scope: CoroutineScope? = null
    val viewHolderScope: CoroutineScope
        get() = _scope?.let {
            if (it.isActive) it else null
        } ?: CoroutineScope(CoroutineName("ViewModelScope") + Dispatchers.IO).also {
            _scope = it
        }

    override fun onDetach() {
        _scope?.cancel()
        super.onDetach()
    }
}
