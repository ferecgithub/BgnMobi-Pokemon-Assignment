package com.ferechamitbeyli.bgnmobipokemonassignment

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.ferechamitbeyli.bgnmobipokemonassignment.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Ferec Hamitbeyli on 29.11.2022
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigationComponents()
        setupDrawerMenu()

    }

    private fun setupDrawerMenu() {
        drawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayoutMain,
            R.string.menu_open,
            R.string.menu_close
        )
        binding.drawerLayoutMain.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

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
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupNavigationComponents() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView_main)
                    as NavHostFragment
        navController = navHostFragment.navController
    }
}