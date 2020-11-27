package com.tusharvohra.moviebuff.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tusharvohra.moviebuff.data.ApiService
import com.tusharvohra.moviebuff.data.MovieResponse
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by tusharvohra on 28/11/20.
 */
class MainViewModel : ViewModel() {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.omdbapi.com/?apikey=82093993&")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    private val movieLiveData = MutableLiveData<MovieResponse>()
    val movieList: LiveData<MovieResponse>
        get() = movieLiveData

    fun searchById(id: String) {
        viewModelScope.launch {
            apiService.searchById(id).let {
                if (it.isSuccessful) {
                    Log.i("APPDATA", "success")
                    it.body()?.let { movie ->
                        Log.i("APPDATA", movie.actors)
                        movieLiveData.postValue(movie)
                    }
                } else {
                    Log.i("APPDATA", "error code: ${it.code()} msg: ${it.message()}")
                }
            }
        }
    }
}