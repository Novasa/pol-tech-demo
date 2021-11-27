package com.example.facts.app.fragment

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.transition.Scene
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.facts.R
import com.example.facts.database.query.CategoryWithFacts
import com.example.facts.databinding.CellCategoryBinding
import com.example.facts.databinding.CellFactBinding
import com.example.facts.databinding.FragmentCategoriesBinding
import com.example.facts.model.Category
import com.example.facts.model.Fact
import com.example.facts.model.WeirdoTypeArg
import com.example.facts.model.WeirdoTypeExample
import com.example.facts.viewmodel.FactsViewModel
import com.example.styling.ThemedAttributeProvider
import com.example.view.adapter.BindingListAdapter
import com.example.view.adapter.BindingViewHolder
import com.example.view.adapter.RecyclerViewItemSpacing
import com.example.view.extension.collectToListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private val factsViewModel: FactsViewModel by activityViewModels()

    @Inject
    lateinit var themedAttributeProvider: ThemedAttributeProvider

    private var fabsExpanded = false

    private lateinit var constraintsCollapsed: ConstraintSet
    private lateinit var constraintsExpanded: ConstraintSet

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false).also { binding ->
            binding.lifecycleOwner = viewLifecycleOwner

            val factsAdapter = Adapter(factsViewModel)

            binding.categoriesRecyclerView.apply {
                adapter = factsAdapter
                layoutManager = LinearLayoutManager(inflater.context)
                addItemDecoration(RecyclerViewItemSpacing(themedAttributeProvider.getDimensionP(R.attr.facts_dimension_spacing)))
            }

            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    factsViewModel.categoryFlow
                        .map { categories ->
                            categories.flatMap { category ->
                                listOf(CategoryAdapterItem(category)) + category.facts.map { FactAdapterItem(it, category.category) }
                            }
                        }
                        .onEach { list -> Timber.d("$list") }
                        .collectToListAdapter(factsAdapter)
                }
            }

            binding.categoriesFabAdd.setOnClickListener {
                toggleFabsExpanded()
            }

            binding.categoriesFabAddCategory.setOnClickListener {

                val input = EditText(binding.root.context).apply {
                    inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                    hint = "Indtast navn"
                }

                AlertDialog.Builder(binding.root.context)
                    .setTitle("Ny kategori")
                    .setView(input)
                    .setPositiveButton("OK") { _, _ -> factsViewModel.createCategory(input.text.toString()) }
                    .create()
                    .show()
            }

            binding.categoriesFabAddFact.setOnClickListener {
                val arg = WeirdoTypeArg(WeirdoTypeExample::class.java)

                findNavController()
                    .navigate(CategoriesFragmentDirections.actionCategoriesFragmentToCreateFactFragment(arg))
            }

            constraintsExpanded = ConstraintSet().apply {
                clone(binding.categoriesContentView)
            }

            constraintsCollapsed = ConstraintSet().apply {
                clone(binding.categoriesContentView)
                centerVertically(binding.categoriesFabAddCategory.id, binding.categoriesFabAdd.id, ConstraintSet.TOP, 0, binding.categoriesFabAdd.id, ConstraintSet.BOTTOM, 0, .5f)
                centerVertically(binding.categoriesFabAddFact.id, binding.categoriesFabAdd.id, ConstraintSet.TOP, 0, binding.categoriesFabAdd.id, ConstraintSet.BOTTOM, 0, .5f)
                applyTo(binding.categoriesContentView)
            }

            fabsExpanded = false
        }

        return binding.root
    }

    private fun toggleFabsExpanded() {
        val constraints = if (fabsExpanded) constraintsCollapsed else constraintsExpanded
        constraints.applyTo(binding.categoriesContentView)
        TransitionManager.go(Scene(binding.categoriesContentView))
        fabsExpanded = !fabsExpanded
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

        /** Base ViewHolder class to minimize extensions */
        abstract class ViewHolder<TBinding : ViewDataBinding, TItem : AdapterItem>(override val binding: TBinding) : RecyclerView.ViewHolder(binding.root), BindingViewHolder<TBinding, TItem>

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
