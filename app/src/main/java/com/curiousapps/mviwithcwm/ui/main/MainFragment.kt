package com.curiousapps.mviwithcwm.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.curiousapps.mviwithcwm.R
import com.curiousapps.mviwithcwm.model.BlogPost
import com.curiousapps.mviwithcwm.ui.DataStateListener
import com.curiousapps.mviwithcwm.ui.main.state.MainStateEvent
import com.curiousapps.mviwithcwm.ui.main.state.MainStateEvent.*
import com.curiousapps.mviwithcwm.ui.main.state.MainViewState
import com.curiousapps.mviwithcwm.util.DataState
import com.curiousapps.mviwithcwm.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_main.*
import java.lang.ClassCastException
import java.lang.Exception

class MainFragment: Fragment(),
BlogListAdapter.Interaction{

    lateinit var viewModel: MainViewModel

    lateinit var dataStateHandler: DataStateListener

    lateinit var blogListAdapter: BlogListAdapter

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
        initRecyclerView()
    }

    private fun initRecyclerView(){
        recyclerview.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            blogListAdapter = BlogListAdapter(this@MainFragment)
            adapter = blogListAdapter
        }
    }

    private fun subscribeObservers(){
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState->
            println("**DEBUG: DataState: $dataState")
            //Handle loading and message
            dataStateHandler.onDataStateChange(dataState)
            //Handle data
            dataState.data?.let {event ->
                event.getContentIfNotHandled()?.let {mainViewState ->
                    println("DEBUG: DataState: ${mainViewState}")
                    mainViewState.blogPosts?.let { blogPosts ->
                        //Set BlogPost data
                        viewModel.setBlogListData(blogPosts)
                    }
                    mainViewState.user?.let { user ->
                        //Set user Data
                        viewModel.setUser(user)
                    }
                }

            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState->
            viewState.blogPosts?.let {
                println("DEBUG: Setting blogPosts to RecyclerView: ${it}")
                blogListAdapter.submitList(it)
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateHandler = context as DataStateListener
        }catch (e: ClassCastException){
            println("DEBUG: $context must implement DataStateListener")
        }
    }

    override fun onItemSelected(position: Int, item: BlogPost) {
        println("DEBUG: Clicked >>> $position")
        println("DEBUG: Clicked >>> $item")
    }
}