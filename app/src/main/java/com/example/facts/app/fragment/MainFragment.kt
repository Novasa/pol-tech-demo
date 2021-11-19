package com.example.facts.app.fragment

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.animalfacts.app.fragment.AnimalFactsFragment
import com.example.animalfacts.app.fragment.AnimalImagesFragment
import com.example.animalfacts.viewmodel.AnimalFactsViewModel
import com.example.facts.R
import com.example.facts.databinding.FragmentMainBinding
import com.example.facts.viewmodel.FactsViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    class Page(
        val title: String,
        val cls: Class<out Fragment>
    )

    companion object {
        private val PAGES = listOf(
            Page("Categories", CategoriesFragment::class.java),
            Page("Facts", AnimalFactsFragment::class.java),
            Page("Images", AnimalImagesFragment::class.java)
        )
    }

    private val factsViewModel by activityViewModels<FactsViewModel>()
    private val animalFactsViewModel by activityViewModels<AnimalFactsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = FragmentMainBinding.inflate(inflater, container, false).also { binding ->

        val context = inflater.context

        binding.categoryViewModel = factsViewModel
        binding.animalFactsViewModel = animalFactsViewModel
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = PAGES.size
            override fun createFragment(position: Int): Fragment = PAGES[position].cls.newInstance()
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = PAGES[position].title
        }.attach()

        binding.buttonCreateCategory.setOnClickListener {

            val input = EditText(context).apply {
                inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                hint = "Indtast navn"
            }

            AlertDialog.Builder(context)
                .setTitle("Ny kategori")
                .setView(input)
                .setPositiveButton("OK") { _, _ ->
                    factsViewModel.createCategory(input.text.toString())
                }
                .create()
                .show()
        }

        binding.buttonCreateFact.setOnClickListener {
            parentFragmentManager.commit {
                setCustomAnimations(R.anim.anim_in_from_bottom, R.anim.anim_out_to_bottom)
                add(R.id.fragmentContainer, CreateFactFragment::class.java.newInstance())
                addToBackStack(CreateFactFragment::class.java.name)
            }
//            val input = EditText(context).apply {
//                inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
//                hint = "Indtast tekst"
//            }
//
//            AlertDialog.Builder(context)
//                .setTitle("Ny fact")
//                .setView(input)
//                .setPositiveButton("OK") { _, _ ->
//                    val text = input.text.toString()
//
//                    lifecycleScope.launch {
//                        factsViewModel.categoryFlow.collectLatest { categories ->
//                            val selected = HashSet<Int>()
//
//                            AlertDialog.Builder(context)
//                                .setTitle("Vælg kategorier")
//                                .setMultiChoiceItems(categories.map { it.category.name }.toTypedArray(), null) { _, which, checked ->
//                                    if (checked) {
//                                        selected.add(which)
//                                    } else {
//                                        selected.remove(which)
//                                    }
//                                }
//                                .setPositiveButton("OK") { _, _ ->
//                                    factsViewModel.createFact(text, categories
//                                        .filterIndexed { index, _ -> selected.contains(index) }
//                                        .map { it.category })
//                                }
//                                .setNegativeButton("Afbryd") { _, _ -> }
//                                .create()
//                                .show()
//
//                            cancel()
//                        }
//                    }
//                }
//                .setNegativeButton("Afbryd") { _, _ -> }
//                .create()
//                .show()
        }
    }.root
}