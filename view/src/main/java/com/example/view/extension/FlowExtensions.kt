package com.example.view.extension

import androidx.recyclerview.widget.ListAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

suspend fun <T> Flow<List<T>>.collectToListAdapter(adapter: ListAdapter<T, *>) {
    collect { adapter.submitList(it) }
}
