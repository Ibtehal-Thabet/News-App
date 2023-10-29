package com.example.newsapp.ui.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityHomeBinding
import com.example.newsapp.ui.categories.CategoriesFragment
import com.example.newsapp.ui.categories.Category
import com.example.newsapp.ui.home.news.NewsFragment
import com.example.newsapp.ui.search.SearchFragment
import com.example.newsapp.ui.settings.SettingsFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    CategoriesFragment.OnItemClickListener {
    private lateinit var viewBinding: ActivityHomeBinding
    private lateinit var drawerLayout: DrawerLayout
    private var categoriesFragment = CategoriesFragment()
    private var searchView: SearchView? = null
    private var searchMenu: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        categoriesFragment.onItemClickListener = this
        navigateToCategories()
        viewDrawer()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchMenu = menu.findItem(R.id.searchBar)
        searchView = searchMenu?.actionView as SearchView?

        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView?.queryHint = "Search..."
        searchMenu?.collapseActionView()


        searchMenu?.icon?.setVisible(false, false)
        viewSearch(searchView)

        return super.onCreateOptionsMenu(menu)
    }

    private fun viewSearch(searchView: SearchView?) {
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.length!! > 2) {
                    showSearchFragment(query)
                } else {
                    Toast.makeText(
                        this@HomeActivity,
                        "Please, type more letters",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun showSearchFragment(query: String? = null) {
        val searchFragment = SearchFragment()
        val bundle = Bundle()
        bundle.putString("query", query)
        searchFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, searchFragment)
            .addToBackStack(null)
            .commit()
    }


    override fun onItemClick(categories: Category) {
        showCategoryNewsFragment(categories)
    }

    private fun showCategoryNewsFragment(categories: Category) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, NewsFragment.getInstance(categories))
            .addToBackStack(null)
            .commit()
        searchMenu?.isVisible = true
    }

    private fun viewDrawer() {
        drawerLayout = viewBinding.drawerLayout

        val toolbar = viewBinding.toolbar
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)

        val navigationView = viewBinding.navView
        navigationView.setNavigationItemSelectedListener(this)

        val toggle =
            ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.categories -> navigateToCategories()
            R.id.settings -> navigateToSettings()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun navigateToCategories() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, categoriesFragment)
            .commit()
        searchMenu?.isVisible = false
    }

    private fun navigateToSettings() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, SettingsFragment())
            .commit()
        searchMenu?.isVisible = false
    }

}