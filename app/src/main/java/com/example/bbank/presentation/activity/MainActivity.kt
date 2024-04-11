package com.example.bbank.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bbank.R
import com.example.bbank.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController = findNavController(R.id.shoppingHostFragment)
        binding.bottomNavigation.setupWithNavController(navController)

        setSupportActionBar(binding.topAppBar)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
//                R.id.exchangesFragment -> {
//                    binding.topAppBar.apply {
//                        menu.clear()
//                        inflateMenu(R.menu.menu_exchanges)
//                        title = "Exchanges"
//                    }
//                }
//
//                R.id.newsFragment -> {
//                    binding.topAppBar.apply {
//                        menu.clear()
//                        inflateMenu(R.menu.menu_news_bar)
//                        title = "News"
//                    }
//                }
            }
        }
    }
}
