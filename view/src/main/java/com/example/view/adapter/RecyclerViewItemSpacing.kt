package com.example.view.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewItemSpacing(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        val columns = (parent.layoutManager as? GridLayoutManager)?.spanCount ?: 1

        val pos = parent.getChildAdapterPosition(view)
        val column = pos % columns

        outRect.left = spacing - column * spacing / columns
        outRect.right = (column + 1) * spacing / columns

        if (pos < columns) outRect.top = spacing
        outRect.bottom = spacing
    }
}