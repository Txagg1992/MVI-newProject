package com.curiousapps.mviwithcwm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.curiousapps.mviwithcwm.api.RetrofitBuilder
import com.curiousapps.mviwithcwm.model.BlogPost
import com.curiousapps.mviwithcwm.model.User
import com.curiousapps.mviwithcwm.ui.main.state.MainViewState
import com.curiousapps.mviwithcwm.util.*

object Repository {
    fun getBlogPosts(): LiveData<DataState<MainViewState>> {

        return object : NetworkBoundResource<List<BlogPost>, MainViewState>(){
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                result.value = DataState.data(
                    null,
                    data =  MainViewState(
                        blogPosts = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
                return RetrofitBuilder.apiService.getBlogPosts()
            }

        }.asLiveData()

        /**
         *     THis when statement was moved to NetworkBoundResource class
         *     This return statement is now different
         *        return Transformations
        .switchMap(RetrofitBuilder.apiService.getBlogPosts()) { apiResponse ->
        object : LiveData<DataState<MainViewState>>() {
        override fun onActive() {
        super.onActive()

        }
        }
        }

         */
    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {

        return object : NetworkBoundResource<User, MainViewState>(){
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.data(
                    null,
                    data =  MainViewState(
                        user = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return RetrofitBuilder.apiService.getUser(userId )
            }

        }.asLiveData()


        /**
         *     THis is the return we had before building the NetworkBoundResource class
         *         return Transformations
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

         */
    }
}