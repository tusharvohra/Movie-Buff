package com.tusharvohra.moviebuff.data

import com.tusharvohra.moviebuff.data.model.movie.MovieResponse
import com.tusharvohra.moviebuff.data.model.search.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by tusharvohra on 28/11/20.
 */
interface ApiService {

    @GET("/")
    suspend fun searchById(@Query("apikey") apikey: String, @Query("i") id: String): Response<MovieResponse>

    @GET("/")
    suspend fun searchByTitle(@Query("apikey") apikey: String, @Query("t") title: String): Response<MovieResponse>

    @GET("/")
    suspend fun searchMovies(@Query("apikey") apikey: String, @Query("s") name: String): Response<SearchResponse>

}