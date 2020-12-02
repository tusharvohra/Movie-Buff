package com.tusharvohra.moviebuff.ui

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tusharvohra.moviebuff.data.ApiService
import com.tusharvohra.moviebuff.data.model.movie.MovieResponse
import com.tusharvohra.moviebuff.data.model.search.SearchResponse
import kotlinx.coroutines.launch

/**
 * Created by tusharvohra on 28/11/20.
 */
class MainViewModel @ViewModelInject constructor(
    private val apiService: ApiService
) : ViewModel() {

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