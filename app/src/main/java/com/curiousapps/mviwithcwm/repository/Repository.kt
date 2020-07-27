package com.curiousapps.mviwithcwm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.curiousapps.mviwithcwm.api.RetrofitBuilder
import com.curiousapps.mviwithcwm.ui.main.state.MainViewState
import com.curiousapps.mviwithcwm.util.ApiEmptyResponse
import com.curiousapps.mviwithcwm.util.ApiErrorResponse
import com.curiousapps.mviwithcwm.util.ApiSuccessResponse
import com.curiousapps.mviwithcwm.util.DataState

object Repository {
    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getBlogPosts()) { apiResponse ->
                object : LiveData<DataState<MainViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        when (apiResponse) {
                            is ApiSuccessResponse -> {
                                value = DataState.data(
                                    message = null,
                                    data = MainViewState(
                                        blogPosts = apiResponse.body
                                    )
                                )
                            }
                            is ApiErrorResponse -> {
                                value = DataState.error(
                                    message = apiResponse.errorMessage
                                )
                            }
                            is ApiEmptyResponse -> {
                                value = DataState.error(
                                    message = "HTTP 204. Returned NOTHING!"
                                )
                            }
                        }
                    }
                }
            }
    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getUser(userId)) { apiResponse ->
                object : LiveData<DataState<MainViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        value = when (apiResponse) {
                            is ApiSuccessResponse -> {
                                DataState.data(
                                    data = MainViewState(
                                        user = apiResponse.body
                                    )
                                )
                            }
                            is ApiErrorResponse -> {
                                DataState.error(
                                    message = apiResponse.errorMessage
                                )
                            }
                            is ApiEmptyResponse -> {
                                DataState.error(
                                    message = "HTTP 204. Returned NOTHING!"
                                )
                            }
                        }
                    }
                }
            }
    }
}