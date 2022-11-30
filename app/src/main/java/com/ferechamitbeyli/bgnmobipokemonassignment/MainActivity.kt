package com.ferechamitbeyli.bgnmobipokemonassignment

import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.ferechamitbeyli.bgnmobipokemonassignment.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Ferec Hamitbeyli on 29.11.2022
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigationComponents()
        setupDrawerMenu()

    }

    private fun setupDrawerMenu() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navigationViewMain.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuItem_closeApplication -> {
                    this@MainActivity.finishAndRemoveTask()
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun setupNavigationComponents() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView_main)
                    as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayoutMain)

        binding.toolbarMain.setupWithNavController(navController, appBarConfiguration)
    }
}