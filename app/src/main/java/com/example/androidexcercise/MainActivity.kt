package com.example.androidexcercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, homeFragment)
                .addToBackStack(homeFragment.tag)
                .commit()
    }

}