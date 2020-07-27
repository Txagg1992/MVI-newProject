package com.curiousapps.mviwithcwm.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.curiousapps.mviwithcwm.model.BlogPost
import com.curiousapps.mviwithcwm.model.User
import com.curiousapps.mviwithcwm.repository.Repository
import com.curiousapps.mviwithcwm.ui.main.state.MainStateEvent
import com.curiousapps.mviwithcwm.ui.main.state.MainStateEvent.*
import com.curiousapps.mviwithcwm.ui.main.state.MainViewState
import com.curiousapps.mviwithcwm.util.AbsentLiveData
import com.curiousapps.mviwithcwm.util.DataState

class MainViewModel: ViewModel() {

    private val mStateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val mViewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = mViewState

    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(mStateEvent){stateEvent ->
//            stateEvent?.let {
//                handleStateEvent(it)
                stateEvent?.let { stateEvent
                    handleStateEvent(stateEvent)
                }
            }

    fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>>{
        return when(stateEvent){
            is GetBlogPostEvent->{
                return Repository.getBlogPosts()
            }
            is GetUserEvent ->{
                Repository.getUser(stateEvent.userId)
            }
            is None ->{
                AbsentLiveData.create()
            }
        }
    }

    fun getCurrentViewStateOrNew(): MainViewState{
        //inline variable with return
        return viewState.value?.let {
            it
        }?: MainViewState()
        /**
         *     long code NOT inline
        val value = viewState.value?.let {
        it
        }?: MainViewState()
        return value
         */
    }

    fun setBlogListData(blogPosts: List<BlogPost>){
        val update = getCurrentViewStateOrNew()
        update.blogPosts = blogPosts
        mViewState.value = update
    }

    fun setUser(user: User){
        val update = getCurrentViewStateOrNew()
        update.user = user
        mViewState.value  = update
    }

    fun setStateEvent(event: MainStateEvent){
        mStateEvent.value = event
    }

}