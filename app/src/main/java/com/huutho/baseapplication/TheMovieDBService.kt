package com.huutho.baseapplication

import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDBService {

    @GET("movie/popular")
    suspend fun getPopular(): Movie

    @GET("movie/popular")
    suspend fun getPopular2(@Query("language") language: String): Movie

    @GET("movie/top_rated")
    suspend fun getTopRate(): Movie

}