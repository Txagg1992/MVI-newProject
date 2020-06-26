package com.curiousapps.mviwithcwm.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.curiousapps.mviwithcwm.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showMainFrag()
    }

    fun showMainFrag(){
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                MainFragment(), "MainFragment")
    }
}