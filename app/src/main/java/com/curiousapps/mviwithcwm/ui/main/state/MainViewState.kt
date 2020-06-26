package com.curiousapps.mviwithcwm.ui.main.state

import com.curiousapps.mviwithcwm.model.BlogPost
import com.curiousapps.mviwithcwm.model.User

data class MainViewState(
    var blogPosts: List<BlogPost>? = null,
    var user: User? = null
) {
}