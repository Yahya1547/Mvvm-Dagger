package com.future.mvvm_dagger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import kotlin.math.abs

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        val appbar = findViewById<AppBarLayout>(R.id.appbar)
        val collapsingToolbar = findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbar)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if(abs(verticalOffset) >= appbar.totalScrollRange){
                collapsingToolbar.title = "Baju Kaos Pria Tee T-Shirt"
                tabLayout.visibility = View.VISIBLE
            }else{
                collapsingToolbar.title = ""
                tabLayout.visibility = View.GONE
            }
        })
    }
}