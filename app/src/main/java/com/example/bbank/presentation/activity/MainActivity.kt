package com.example.bbank.presentation.activity

import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bbank.R
import com.example.bbank.databinding.ActivityMainBinding
import com.example.bbank.presentation.settings.SettingsFragment
import com.example.core.presentation.ui.base.BaseActivity
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val navController by lazy { findNavController(R.id.hostFragment) }
    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(com.example.news.presentation.R.id.news_navigation_graph),
            binding.drawerLayout
        )
    }

    override fun initBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        MapKitFactory.initialize(this)
        prepareNavigation()
    }

    private fun prepareNavigation() {
        setupNavController()
        setNavigationViewItemListener()
        manageHidingBottomNavigation()
    }

    override fun onSupportNavigateUp() =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()


    private fun setupNavController() =
        binding.apply {
            bottomNavigation.setupWithNavController(navController)
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }

    private fun setNavigationViewItemListener() =
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.settingsItem -> {
                    navController.navigate(R.id.settingsFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                else -> false
            }
        }

    private fun manageHidingBottomNavigation() {
        supportFragmentManager.registerFragmentLifecycleCallbacks(object :
            FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(
                fm: FragmentManager,
                f: Fragment,
                v: View,
                savedInstanceState: Bundle?
            ) {
                TransitionManager.beginDelayedTransition(
                    binding.root,
                    Slide(Gravity.BOTTOM).excludeTarget(R.id.hostFragment, true)
                )
                manageBottomNavigationVisibility(f)
            }
        }, true)
    }

    private fun manageBottomNavigationVisibility(fragment: Fragment) {
        binding.bottomNavigation.visibility = when (fragment) {
            is SettingsFragment -> View.GONE
            else -> View.VISIBLE
        }
    }
}