package com.example.facts.databinding

import androidx.databinding.InverseBindingAdapter
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject
import javax.inject.Singleton

object RecyclerViewBindingAdapter  {

    @JvmStatic
    @InverseBindingAdapter(attribute = "selectedItems", event = "selectedItemsAttrChanged")
    fun <T> RecyclerView.getSelectedItems(): List<T> {
        return emptyList()
    }
}