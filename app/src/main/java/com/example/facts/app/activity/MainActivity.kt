package com.example.facts.app.activity

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
import com.example.facts.app.fragment.CategoriesFragment
import com.example.facts.app.fragment.CreateFactFragment
import com.example.facts.databinding.ActivityMainBinding
import com.example.facts.viewmodel.FactsViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this
    }

    override fun onBackPressed() {
        if (!supportFragmentManager.popBackStackImmediate()) {
            super.onBackPressed()
        }
    }
}
