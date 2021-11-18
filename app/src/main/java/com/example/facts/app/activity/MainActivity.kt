package com.example.facts.app.activity

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.animalfacts.app.fragment.AnimalFactsFragment
import com.example.animalfacts.app.fragment.AnimalImagesFragment
import com.example.animalfacts.viewmodel.AnimalFactsViewModel
import com.example.facts.R
import com.example.facts.databinding.ActivityMainBinding
import com.example.facts.viewmodel.FactsViewModel
import com.example.facts.app.fragment.CategoriesFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

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

    private val factsViewModel: FactsViewModel by viewModels()
    private val animalFactsViewModel: AnimalFactsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.categoryViewModel = factsViewModel
        binding.animalFactsViewModel = animalFactsViewModel
        binding.lifecycleOwner = this

        binding.viewPager.adapter = object : FragmentStateAdapter(this@MainActivity) {
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

            val input = EditText(context).apply {
                inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                hint = "Indtast tekst"
            }

            AlertDialog.Builder(context)
                .setTitle("Ny fact")
                .setView(input)
                .setPositiveButton("OK") { _, _ ->
                    val text = input.text.toString()

                    lifecycleScope.launch {
                        factsViewModel.categoryFlow.collectLatest { categories ->
                            val selected = HashSet<Int>()

                            AlertDialog.Builder(context)
                                .setTitle("Vælg kategorier")
                                .setMultiChoiceItems(categories.map { it.category.name }.toTypedArray(), null) { _, which, checked ->
                                    if (checked) {
                                        selected.add(which)
                                    } else {
                                        selected.remove(which)
                                    }
                                }
                                .setPositiveButton("OK") { _, _ ->
                                    factsViewModel.createFact(text, categories
                                        .filterIndexed { index, _ -> selected.contains(index) }
                                        .map { it.category })
                                }
                                .setNegativeButton("Afbryd") { _, _ -> }
                                .create()
                                .show()

                            cancel()
                        }
                    }
                }
                .setNegativeButton("Afbryd") { _, _ -> }
                .create()
                .show()
        }
    }
}
