package com.tusharvohra.moviebuff.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tusharvohra.moviebuff.data.ApiService
import com.tusharvohra.moviebuff.data.model.movie.MovieResponse
import com.tusharvohra.moviebuff.data.model.search.SearchResponse
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by tusharvohra on 28/11/20.
 */
class MainViewModel : ViewModel() {

    private val retrofit: Retrofit
    private val interceptor = HttpLoggingInterceptor()
    private val apiService: ApiService

    init {

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }


    private val movieLiveData = MutableLiveData<MovieResponse>()
    val movieList: LiveData<MovieResponse>
        get() = movieLiveData

    fun searchById(id: String) {
        viewModelScope.launch {
            apiService.searchById("82093993", id).let {
                if (it.isSuccessful) {
                    it.body()?.let { movie ->
                        movieLiveData.postValue(movie)
                    }
                } else {
                    Log.i("APPDATA", "error code: ${it.code()}")
                }
            }
        }
    }

    fun searchByTitle(title: String) {
        viewModelScope.launch {
            apiService.searchByTitle("82093993", title).let {
                if (it.isSuccessful) {
                    it.body()?.let { movie ->
                        movieLiveData.postValue(movie)
                    }
                } else {
                    Log.i("APPDATA", "error code: ${it.code()}")
                }
            }
        }
    }

    private val searchResponseMutableLiveData = MutableLiveData<SearchResponse>()
    val searchMovieList: LiveData<SearchResponse>
        get() = searchResponseMutableLiveData

    fun searchMovies(name: String) {
        viewModelScope.launch {
            apiService.searchMovies("82093993", name).let {
                if (it.isSuccessful) {
                    it.body()?.let { data ->
                        searchResponseMutableLiveData.postValue(data)
                    }
                } else {
                    Log.i("APPDATA", "error code: ${it.code()}")
                }
            }
        }
    }
}