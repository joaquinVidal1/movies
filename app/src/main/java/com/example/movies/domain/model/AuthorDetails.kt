package com.example.movies.domain.model

data class AuthorDetails(val avatarPath: String,
                         val name: String,
                         val rating: Int,
                         val username: String = "")