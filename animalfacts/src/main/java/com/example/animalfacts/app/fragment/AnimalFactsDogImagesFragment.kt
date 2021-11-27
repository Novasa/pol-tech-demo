package com.example.animalfacts.app.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animalfacts.databinding.CellAnimalImageBinding
import com.example.animalfacts.databinding.FragmentAnimalFactsBinding
import com.example.animalfacts.databinding.FragmentDogImagesBinding
import com.example.animalfacts.model.AnimalImage
import com.example.animalfacts.viewmodel.AnimalFactsViewModel
import com.example.view.adapter.BindingListAdapter
import com.example.view.adapter.BindingViewHolder
import com.example.view.adapter.CoroutineViewHolder
import com.example.view.adapter.RecyclerViewItemSpacing
import com.example.view.extension.collectToListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber

@AndroidEntryPoint
class AnimalFactsDogImagesFragment : Fragment() {

    private val viewModel by activityViewModels<AnimalFactsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val animalImagesAdapter = Adapter(viewModel)

        val binding = FragmentDogImagesBinding.inflate(inflater, container, false).apply {
            dogImagesRecyclerView.apply {
                adapter = animalImagesAdapter
                layoutManager = GridLayoutManager(inflater.context, 3)
                addItemDecoration(RecyclerViewItemSpacing(20))
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.dogImages.collectToListAdapter(animalImagesAdapter)
        }

        return binding.root
    }

    private object AnimalImageDiffCallback : DiffUtil.ItemCallback<AnimalImage>() {
        override fun areItemsTheSame(oldItem: AnimalImage, newItem: AnimalImage): Boolean = oldItem.uri == newItem.uri
        override fun areContentsTheSame(oldItem: AnimalImage, newItem: AnimalImage): Boolean = true
    }

    private class Adapter(val viewModel: AnimalFactsViewModel) : BindingListAdapter<AnimalImage, Adapter.ImageViewHolder>(AnimalImageDiffCallback) {

        override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): ImageViewHolder {
            return ImageViewHolder(CellAnimalImageBinding.inflate(LayoutInflater.from(context), parent, false))
        }

        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
            super.onBindViewHolder(holder, position)

            if (position == itemCount - 1) {
                viewModel.loadMoreAnimalImages()
            }
        }

        class ImageViewHolder(override val binding: CellAnimalImageBinding) : CoroutineViewHolder(binding.root), BindingViewHolder<CellAnimalImageBinding, AnimalImage> {

            override fun onBind(position: Int, binding: CellAnimalImageBinding, item: AnimalImage) {
                binding.imageLoadingScope = viewHolderScope
                binding.itemImageView.apply {
                    tag = position
                    setImageDrawable(null)
                }
                binding.item = item

                Timber.d("[$position]: $item")
            }
        }
    }
}
