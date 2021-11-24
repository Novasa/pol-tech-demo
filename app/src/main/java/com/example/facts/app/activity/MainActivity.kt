package com.example.facts.app.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.facts.R
import com.example.facts.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        (supportFragmentManager.findFragmentById(R.id.mainFragmentContainerView) as? NavHostFragment)?.navController?.let { navController ->
            NavigationUI.setupWithNavController(binding.mainBottomNavigation, navController)

            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                Timber.d("OnDestinationChanged: $destination, arguments: $arguments")
            }
        }
    }
}
