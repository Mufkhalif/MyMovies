package com.example.movies.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.movies.R
import com.example.movies.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val sectionsPagerAdapter = HomePagerAdapter(this)
        activityMainBinding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(
            activityMainBinding.tabs,
            activityMainBinding.viewPager
        ) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.movie)
                1 -> tab.text = getString(R.string.bookmark)
            }
        }.attach()

        checkModuleBookmark()

        supportActionBar?.elevation = 0f
    }

    private fun checkModuleBookmark() {

        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val moduleBookmark = "bookmark"

        if(splitInstallManager.installedModules.contains(moduleBookmark)) {
            activityMainBinding.tabs.visibility = View.VISIBLE
            activityMainBinding.viewPager.isUserInputEnabled = true
        } else {
            activityMainBinding.tabs.visibility = View.GONE
            activityMainBinding.viewPager.isUserInputEnabled = false
            activityMainBinding.viewPager.setPadding(0, 20, 0, 0)
        }

    }
}