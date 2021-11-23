package com.example.facts.databinding

import androidx.databinding.InverseBindingAdapter
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewBindingAdapter  {

    @JvmStatic
    @InverseBindingAdapter(attribute = "selectedItems", event = "selectedItemsAttrChanged")
    fun <T> RecyclerView.getSelectedItems(): List<T> {
        return emptyList()
    }
}