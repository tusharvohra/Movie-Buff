package com.tusharvohra.moviebuff.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.tusharvohra.moviebuff.R
import com.tusharvohra.moviebuff.data.model.movie.MovieResponse
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val mainViewModel = MainViewModel()

    private lateinit var gridLayoutManager: GridLayoutManager

    private lateinit var movieListAdapter: MovieListAdapter

    var movieList = ArrayList<MovieResponse>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAdapter()
        initObserver()
        callApi()

    }

    private fun callApi() {
        mainViewModel.searchById("tt3896198")
        mainViewModel.searchByTitle("kung fu panda")
        mainViewModel.searchByTitle("up")
        mainViewModel.searchById("tt2911666")
        mainViewModel.searchByTitle("lincoln")
        mainViewModel.searchByTitle("insidious")
        mainViewModel.searchByTitle("schindler's list")
        mainViewModel.searchByTitle("lights out")
        mainViewModel.searchByTitle("mud")
        mainViewModel.searchByTitle("spotlight")
    }

    private fun initObserver() {
        mainViewModel.movieList.observe(this, { movie ->
            movieList.add(movie)
            movieListAdapter.notifyDataSetChanged()
        })
    }

    private fun initAdapter() {
        gridLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        rv_movie_list.layoutManager = gridLayoutManager
        movieListAdapter = MovieListAdapter(movieList)
        rv_movie_list.adapter = movieListAdapter
    }
}