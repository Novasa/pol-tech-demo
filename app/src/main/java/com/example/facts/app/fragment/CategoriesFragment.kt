package com.example.facts.app.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.facts.model.Category
import com.example.facts.databinding.CellCategoryBinding
import com.example.facts.databinding.FragmentCategoriesBinding
import com.example.facts.model.query.CategoryWithFacts
import com.example.facts.viewmodel.FactsViewModel
import com.example.view.adapter.BindingListAdapter
import com.example.view.adapter.BindingViewHolder
import com.example.view.adapter.RecyclerViewItemSpacing
import com.example.view.extension.collectToListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    val factsViewModel: FactsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentCategoriesBinding.inflate(inflater, container, false).root.apply {
            adapter = Adapter(factsViewModel).also { adapter ->
                lifecycleScope.launch {
                    factsViewModel.categoryFlow.collectToListAdapter(adapter)
                }
            }
            layoutManager = LinearLayoutManager(inflater.context)
            addItemDecoration(RecyclerViewItemSpacing(20))
        }
    }

    object CategoryDiffCallback : DiffUtil.ItemCallback<CategoryWithFacts>() {
        override fun areItemsTheSame(oldItem: CategoryWithFacts, newItem: CategoryWithFacts): Boolean = oldItem.category.id == newItem.category.id
        override fun areContentsTheSame(oldItem: CategoryWithFacts, newItem: CategoryWithFacts): Boolean = true
    }

    class Adapter(val viewModel: FactsViewModel) : BindingListAdapter<CategoryWithFacts, Adapter.CategoryViewHolder>(CategoryDiffCallback) {

        override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): CategoryViewHolder {
            return CategoryViewHolder(CellCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onViewAttachedToWindow(holder: CategoryViewHolder) {
            super.onViewAttachedToWindow(holder)
            holder.binding.viewModel = viewModel
        }

        override fun onViewDetachedFromWindow(holder: CategoryViewHolder) {
            holder.binding.viewModel = null
            super.onViewDetachedFromWindow(holder)
        }

        class CategoryViewHolder(override val binding: CellCategoryBinding) : RecyclerView.ViewHolder(binding.root), BindingViewHolder<CellCategoryBinding, CategoryWithFacts> {

            override fun onBind(position: Int, binding: CellCategoryBinding, item: CategoryWithFacts) {
                binding.category = item.category
            }
        }
    }
}
