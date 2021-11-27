package com.example.animalfacts.app.fragment

import android.content.Context
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animalfacts.databinding.CellAnimalFactBinding
import com.example.animalfacts.databinding.FragmentAnimalFactsBinding
import com.example.animalfacts.model.AnimalFact
import com.example.animalfacts.viewmodel.AnimalFactsViewModel
import com.example.view.adapter.BindingListAdapter
import com.example.view.adapter.BindingViewHolder
import com.example.view.adapter.RecyclerViewItemSpacing
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AnimalFactsFragment : Fragment() {

    private val viewModel: AnimalFactsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val animalFactsAdapter = Adapter()

        lifecycleScope.launchWhenStarted {
            viewModel.animalFacts.collectLatest {
                animalFactsAdapter.submitList(it)
            }
        }

        return FragmentAnimalFactsBinding.inflate(inflater, container, false).apply {
            animalFactsRecyclerView.apply {
                adapter = animalFactsAdapter
                layoutManager = LinearLayoutManager(inflater.context)
                addItemDecoration(RecyclerViewItemSpacing(20))
            }

            if (animalFactsAdapter.itemCount == 0) {
                viewModel.updateAnimalFacts()
            }

            animalFactsFabUser.setOnClickListener {
                findNavController()
                    .navigate(AnimalFactsFragmentDirections.actionAnimalFactsFragmentToAnimalFactsCatUserFragment())
            }
        }.root
    }

    private object AnimalFactDiffCallback : DiffUtil.ItemCallback<AnimalFact>() {
        override fun areItemsTheSame(oldItem: AnimalFact, newItem: AnimalFact): Boolean = oldItem.text == newItem.text
        override fun areContentsTheSame(oldItem: AnimalFact, newItem: AnimalFact): Boolean = true
    }

    private class Adapter : BindingListAdapter<AnimalFact, Adapter.ItemViewHolder>(AnimalFactDiffCallback) {

        override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): ItemViewHolder {
            return ItemViewHolder(CellAnimalFactBinding.inflate(LayoutInflater.from(context), parent, false))
        }

        class ItemViewHolder(override val binding: CellAnimalFactBinding) : RecyclerView.ViewHolder(binding.root), BindingViewHolder<CellAnimalFactBinding, AnimalFact> {

            override fun onBind(position: Int, binding: CellAnimalFactBinding, item: AnimalFact) {
                binding.fact = item
            }
        }
    }
}
