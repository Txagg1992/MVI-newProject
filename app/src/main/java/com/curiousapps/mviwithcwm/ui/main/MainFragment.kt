package com.curiousapps.mviwithcwm.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.curiousapps.mviwithcwm.R
import com.curiousapps.mviwithcwm.ui.main.state.MainStateEvent
import com.curiousapps.mviwithcwm.ui.main.state.MainStateEvent.*
import java.lang.Exception

class MainFragment: Fragment(){

    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }?: throw Exception("Invalid Activity")
        subscribeObservers()
    }

    fun subscribeObservers(){
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState->
            println("**DEBUG: DataState: ${dataState}")
            dataState.blogPosts?.let { blogPosts ->
                //Set BlogPost data
                viewModel.setBlogListData(blogPosts)
            }
            dataState.user?.let { user ->
                //Set user Data
                viewModel.setUser(user)
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState->
            viewState.blogPosts?.let {
                println("DEBUG: Setting blogPosts to RecyclerView: ${it}")
            }
            viewState.user?.let {
                println("DEBUG: Setting user data: ${it}")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_get_user -> triggerGetUserEvent()
            R.id.action_get_blogs -> triggerGetBlogsEvent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun triggerGetBlogsEvent() {
        viewModel.setStateEvent(GetBlogPostEvent)// not set as retrieve a function
                                                        // because this is an OBJECT in StateEvent
    }

    private fun triggerGetUserEvent() {
        viewModel.setStateEvent(GetUserEvent("1"))
    }
}