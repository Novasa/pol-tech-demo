package com.example.animalfacts.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.animalfacts.databinding.FragmentCatUserBinding
import com.example.animalfacts.viewmodel.AnimalFactsViewModel

class AnimalFactsCatUserFragment : Fragment() {

    private val viewModel: AnimalFactsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentCatUserBinding.inflate(inflater, container, false).also { binding ->
            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = viewModel

        }.root
    }
}
