package com.curiousapps.mviwithcwm.ui.main

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.curiousapps.mviwithcwm.R
import com.curiousapps.mviwithcwm.ui.DataStateListener
import com.curiousapps.mviwithcwm.util.DataState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DataStateListener {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        showMainFrag()
    }

    fun showMainFrag(){
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                MainFragment(), "MainFragment")
            .commit()
    }

    override fun onDataStateChange(dataState: DataState<*>?) {
        handleDataStateChange(dataState)
    }

    private fun handleDataStateChange(dataState: DataState<*>?) {
        dataState?.let {
            //handle Loading
            showProgressBar(it.loading)
            //handle message
            it.message?.let { event ->
                event.getContentIfNotHandled()?.let {message ->
                    showToast(message)
                }

            }
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showProgressBar(isVisible: Boolean){
        if (isVisible){
            progress_bar.visibility = View.VISIBLE
        }else{
            progress_bar.visibility = View.INVISIBLE
        }
    }
}