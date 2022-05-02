package com.example.week_4_task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val defaultFragment = HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragementFrameLayout, defaultFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)


        bottomNavigationView.setOnItemSelectedListener {
            item ->
            when(item.itemId) {

                R.id.homeNav -> {
                    // Respond to navigation item 1 click
                    val homeFragment = HomeFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragementFrameLayout, homeFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                    true
                }

                R.id.peopleNav -> {
                    // Respond to navigation item 2 click
                    val peopleFragment = PeopleFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragementFrameLayout, peopleFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                    true
                }

                R.id.giftsNav -> {
                    // Respond to navigation item 2 click
                    val giftsFragment = GiftsFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragementFrameLayout, giftsFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                    true
                }
                else -> false
            }
        }

        bottomNavigationView.setOnItemReselectedListener { item ->
            when(item.itemId) {
                R.id.homeNav -> {
                    // Respond to navigation item 1 reselection
                     false
                }
                R.id.peopleNav -> {
                    // Respond to navigation item 2 reselection
                     false
                }
                R.id.giftsNav -> {
                    // Respond to navigation item 2 reselection
                     false
                }
                else -> false
            }
        }
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//    }

}