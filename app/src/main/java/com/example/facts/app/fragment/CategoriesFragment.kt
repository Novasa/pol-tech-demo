package com.example.facts.app.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.facts.model.Category
import com.example.facts.databinding.CellCategoryBinding
import com.example.facts.databinding.CellFactBinding
import com.example.facts.databinding.FragmentCategoriesBinding
import com.example.facts.model.Fact
import com.example.facts.model.query.CategoryWithFacts
import com.example.facts.viewmodel.FactsViewModel
import com.example.view.adapter.BindingListAdapter
import com.example.view.adapter.BindingViewHolder
import com.example.view.adapter.RecyclerViewItemSpacing
import com.example.view.extension.collectToListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.IllegalArgumentException

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    val factsViewModel: FactsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentCategoriesBinding.inflate(inflater, container, false).root.apply {
            adapter = Adapter(factsViewModel).also { adapter ->
                lifecycleScope.launch {
                    factsViewModel.categoryFlow
                        .map { categories ->
                            categories.flatMap { category ->
                                listOf(CategoryAdapterItem(category)) + category.facts.map { FactAdapterItem(it, category.category) }
                            }
                        }
                        .onEach { list -> Timber.d("$list") }
                        .collectToListAdapter(adapter)
                }
            }
            layoutManager = LinearLayoutManager(inflater.context)
            addItemDecoration(RecyclerViewItemSpacing(20))
        }
    }

    object CategoryDiffCallback : DiffUtil.ItemCallback<AdapterItem>() {
        override fun areItemsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean = true
    }

    companion object ViewTypes {
        const val CATEGORY = 1
        const val FACT = 2
    }

    interface AdapterItem {
        val id: Long
        val viewType: Int
    }

    data class CategoryAdapterItem(val item: CategoryWithFacts) : AdapterItem {
        override val id: Long = item.category.id
        override val viewType: Int = ViewTypes.CATEGORY
    }

    data class FactAdapterItem(val item: Fact, val category: Category) : AdapterItem {
        override val id: Long = item.id
        override val viewType: Int = ViewTypes.FACT
    }

    class Adapter(val viewModel: FactsViewModel) : BindingListAdapter<AdapterItem, RecyclerView.ViewHolder>(CategoryDiffCallback) {

        override fun getItemViewType(position: Int): Int = getItem(position).viewType

        override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int) = LayoutInflater.from(parent.context).let { inflater ->
            when (viewType) {
                ViewTypes.CATEGORY -> CategoryViewHolder(CellCategoryBinding.inflate(inflater, parent, false))
                ViewTypes.FACT -> FactViewHolder(CellFactBinding.inflate(inflater, parent, false))
                else -> throw NotImplementedError()
            }
        }

        override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
            super.onViewAttachedToWindow(holder)
            (holder as? CategoryViewHolder)?.binding?.viewModel = viewModel
        }

        override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
            (holder as? CategoryViewHolder)?.binding?.viewModel = null
            super.onViewDetachedFromWindow(holder)
        }

        abstract class ViewHolder<TBinding : ViewDataBinding, TItem : Any>(override val binding: TBinding) : RecyclerView.ViewHolder(binding.root), BindingViewHolder<TBinding, TItem>

        class CategoryViewHolder(binding: CellCategoryBinding) : ViewHolder<CellCategoryBinding, CategoryAdapterItem>(binding) {
            override fun onBind(position: Int, binding: CellCategoryBinding, item: CategoryAdapterItem) {
                binding.category = item.item.category
            }
        }

        class FactViewHolder(override val binding: CellFactBinding) : ViewHolder<CellFactBinding, FactAdapterItem>(binding) {
            override fun onBind(position: Int, binding: CellFactBinding, item: FactAdapterItem) {
                binding.fact = item.item
            }
        }
    }
}
