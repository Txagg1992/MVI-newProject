package com.curiousapps.mviwithcwm.api

import com.curiousapps.mviwithcwm.model.BlogPost
import com.curiousapps.mviwithcwm.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("placeholder/user/{userId}")
    fun getUser(
        @Path("userId") userId: String
    ): User

    @GET("placeholder/blogs")
    fun getBlogPosts(): List<BlogPost>

}