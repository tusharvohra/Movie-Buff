package com.tusharvohra.moviebuff.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.tusharvohra.moviebuff.R
import com.tusharvohra.moviebuff.data.model.movie.MovieResponse
import com.tusharvohra.moviebuff.ui.adapters.MovieListAdapter
import com.tusharvohra.moviebuff.ui.fragments.SearchResultFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var gridLayoutManager: GridLayoutManager

    private lateinit var movieListAdapter: MovieListAdapter

    private var movieList = ArrayList<MovieResponse>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initAdapter()
        initObserver()
        callApi()

    }

    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun initView() {

        iv_search.setOnClickListener {
            if (et_search.text.toString() == "" || et_search.text == null) {
                Toast.makeText(this, "Enter a movie name", Toast.LENGTH_SHORT).show()
            } else {
                closeKeyBoard()
                supportFragmentManager.beginTransaction()
                    .add(
                        R.id.fl_parent,
                        SearchResultFragment.newInstance(et_search.text.toString())
                    )
                    .addToBackStack(SearchResultFragment::class.java.name)
                    .commit()
            }
        }

        et_search.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (et_search.text.toString() == "") {
                    Toast.makeText(this, "Enter a movie name", Toast.LENGTH_SHORT).show()
                } else {
                    closeKeyBoard()
                    supportFragmentManager.beginTransaction()
                        .add(
                            R.id.fl_parent,
                            SearchResultFragment.newInstance(et_search.text.toString())
                        )
                        .addToBackStack(SearchResultFragment::class.java.name)
                        .commit()
                    return@OnEditorActionListener true
                }
            }
            false
        })

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
            Log.i("APPDATA", movie.title)
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