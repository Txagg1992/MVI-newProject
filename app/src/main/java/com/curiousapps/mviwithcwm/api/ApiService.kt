package com.curiousapps.mviwithcwm.api

import androidx.lifecycle.LiveData
import com.curiousapps.mviwithcwm.model.BlogPost
import com.curiousapps.mviwithcwm.model.User
import com.curiousapps.mviwithcwm.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("placeholder/user/{userId}")
    fun getUser(
        @Path("userId") userId: String
    ): LiveData<GenericApiResponse<User>>

    @GET("placeholder/blogs")
    fun getBlogPosts(): LiveData<GenericApiResponse<List<BlogPost>>>

}