package com.curiousapps.mviwithcwm.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.curiousapps.mviwithcwm.ui.main.state.MainViewState

class MainViewModel: ViewModel() {

    private val mViewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = mViewState
}