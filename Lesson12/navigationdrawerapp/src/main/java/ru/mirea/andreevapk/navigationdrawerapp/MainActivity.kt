package ru.mirea.andreevapk.navigationdrawerapp

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import ru.mirea.andreevapk.navigationdrawerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAppBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Нажатие кнопки", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(binding.appBarMain.fab)
                .show()
        }

        val drawer: DrawerLayout = binding.drawerLayout
        val navigationView: NavigationView = binding.navView

        mAppBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_green, R.id.navigation_orange, R.id.navigation_red),
            drawer
        )

        val navController =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
                ?.let { it as NavHostFragment }?.navController
                ?: throw IllegalStateException("NavController not found")

        setupActionBarWithNavController(navController, mAppBarConfiguration)
        navigationView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
                ?.let { it as NavHostFragment }?.navController
                ?: throw IllegalStateException("NavController not found")
        return navController.navigateUp(mAppBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val drawer: DrawerLayout = binding.drawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
