package com.tusharvohra.moviebuff.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by tusharvohra on 28/11/20.
 */
interface ApiService {

    @GET("t={id}")
    suspend fun searchById(@Path("id") id: String): Response<MovieResponse>

}