package com.example.animalfacts.app.fragment

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
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

            binding.catUserFabEdit.setOnClickListener {
                val input = EditText(binding.root.context).apply {
                    inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                    hint = "Navn"
                }

                AlertDialog.Builder(binding.root.context)
                    .setTitle("Rediger navn")
                    .setView(input)
                    .setPositiveButton("OK") { _, _ -> viewModel.updateCatUser(input.text.toString()) }
                    .create()
                    .show()
            }

        }.root
    }
}
