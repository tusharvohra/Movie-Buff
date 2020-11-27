package com.tusharvohra.moviebuff.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.tusharvohra.moviebuff.R


class MainActivity : AppCompatActivity() {

    val mainViewModel = MainViewModel()

    private lateinit var test: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        test = findViewById(R.id.tv_test)

        callApi()
        initObserver()

    }

    private fun callApi() {
//        mainViewModel.searchById("tt3896198")
        mainViewModel.searchById("superman")
    }

    fun initObserver() {
        mainViewModel.movieList.observe(this, Observer { movie ->
            test.text =
                movie.actors + movie.director + movie.country + movie.awards + movie.title + movie.genre +
                        movie.language + movie.plot + movie.released + movie.writer
        })
    }
}