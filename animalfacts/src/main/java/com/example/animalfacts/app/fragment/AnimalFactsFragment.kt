package com.example.animalfacts.app.fragment

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
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animalfacts.databinding.CellAnimalFactBinding
import com.example.animalfacts.databinding.CellAnimalImageBinding
import com.example.animalfacts.databinding.FragmentAnimalFactsBinding
import com.example.animalfacts.model.AnimalFact
import com.example.animalfacts.model.AnimalImage
import com.example.animalfacts.viewmodel.AnimalFactsViewModel
import com.example.catfacts.service.CatFactsService
import com.example.view.adapter.BindingListAdapter
import com.example.view.adapter.BindingViewHolder
import com.example.view.adapter.CoroutineViewHolder
import com.example.view.adapter.RecyclerViewItemSpacing
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.internal.addHeaderLenient
import javax.inject.Inject

@AndroidEntryPoint
class AnimalFactsFragment : Fragment() {

    private val viewModel: AnimalFactsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val animalFactsAdapter = Adapter()

        viewModel.animalFacts.observe(viewLifecycleOwner) {
            animalFactsAdapter.submitList(it)
        }

        return FragmentAnimalFactsBinding.inflate(inflater, container, false).apply {
            animalFactsRecyclerView.apply {
                adapter = animalFactsAdapter
                layoutManager = LinearLayoutManager(inflater.context)
                addItemDecoration(RecyclerViewItemSpacing(20))
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
