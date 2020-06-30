package com.curiousapps.mviwithcwm.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.curiousapps.mviwithcwm.ui.main.state.MainStateEvent
import com.curiousapps.mviwithcwm.ui.main.state.MainStateEvent.*
import com.curiousapps.mviwithcwm.ui.main.state.MainViewState
import com.curiousapps.mviwithcwm.util.AbsentLiveData

class MainViewModel: ViewModel() {

    private val mStateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val mViewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = mViewState

    val dataState: LiveData<MainViewState> = Transformations
        .switchMap(mStateEvent){stateEvent ->
//            stateEvent?.let {
//                handleStateEvent(it)
                stateEvent?.let { stateEvent
                    handleStateEvent(stateEvent)
                }
            }

    fun handleStateEvent(stateEvent: MainStateEvent): LiveData<MainViewState>{
        return when(stateEvent){
            is GetBlogPostEvent->{
                AbsentLiveData.create()
            }
            is GetUserEvent ->{
                AbsentLiveData.create()
            }
            is None ->{
                AbsentLiveData.create()
            }
        }
    }
}