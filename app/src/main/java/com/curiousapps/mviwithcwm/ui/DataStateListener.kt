package com.curiousapps.mviwithcwm.ui

import com.curiousapps.mviwithcwm.util.DataState

interface DataStateListener{
    fun onDataStateChange(dataState: DataState<*>?)
}