package com.example.facts.app.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.facts.databinding.CellSelectableBinding
import com.example.facts.databinding.FragmentCreateFactBinding
import com.example.facts.databinding.Selectable
import com.example.facts.model.Category
import com.example.facts.viewmodel.FactsViewModel
import com.example.view.adapter.BindingListAdapter
import com.example.view.adapter.BindingViewHolder
import com.example.view.extension.collectToListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CreateFactFragment : Fragment() {

    val factsViewModel: FactsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentCreateFactBinding.inflate(inflater, container, false).also { binding ->
            binding.viewmodel = factsViewModel

            binding.factCategories.layoutManager = LinearLayoutManager(inflater.context)
            binding.factCategories.adapter = Adapter().also { adapter ->

                lifecycleScope.launch(Dispatchers.IO) {
                    factsViewModel.categoryFlow
                        .map { categories -> categories.map { Selectable(it.category, it.category.name) } }
                        .collect {
                            withContext(Dispatchers.Main) {
                                adapter.submitList(it)
                                cancel()
                            }
                        }
                }
            }
        }.root
    }

    object ItemCallback : DiffUtil.ItemCallback<Selectable<Category>>() {
        override fun areItemsTheSame(oldItem: Selectable<Category>, newItem: Selectable<Category>): Boolean = oldItem.item == newItem.item
        override fun areContentsTheSame(oldItem: Selectable<Category>, newItem: Selectable<Category>): Boolean = oldItem.selected == newItem.selected
    }

    class Adapter : BindingListAdapter<Selectable<Category>, Adapter.ViewHolder>(ItemCallback) {

        override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(CellSelectableBinding.inflate(LayoutInflater.from(context), parent, false))

        class ViewHolder(override val binding: CellSelectableBinding) : RecyclerView.ViewHolder(binding.root), BindingViewHolder<CellSelectableBinding, Selectable<Category>> {
            override fun onBind(position: Int, binding: CellSelectableBinding, item: Selectable<Category>) {
                binding.item = item
            }
        }
    }
}