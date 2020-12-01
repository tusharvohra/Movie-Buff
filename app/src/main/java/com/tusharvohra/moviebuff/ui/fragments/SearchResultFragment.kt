package com.tusharvohra.moviebuff.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tusharvohra.moviebuff.R
import com.tusharvohra.moviebuff.data.model.search.Search
import com.tusharvohra.moviebuff.ui.MainViewModel
import com.tusharvohra.moviebuff.ui.adapters.MovieSearchAdapter
import kotlinx.android.synthetic.main.fragment_search_result.*

private const val NAME = "param1"

class SearchResultFragment : Fragment() {

    private var name: String? = null

    private val mainViewModel = MainViewModel()

    lateinit var linearLayoutManager: LinearLayoutManager

    private var movieSearchList = ArrayList<Search>()

    private lateinit var movieSearchAdapter: MovieSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserver()
        initAdapter()
        callApi()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            SearchResultFragment().apply {
                arguments = Bundle().apply {
                    putString(NAME, param1)
                }
            }
    }

    private fun initView(){
        tv_search_results.text = "Search results for: '$name' :"
    }

    private fun callApi() {
        mainViewModel.searchMovies(name!!)
    }

    private fun initObserver() {
        mainViewModel.searchMovieList.observe(viewLifecycleOwner, {
            movieSearchList.addAll(it.Search)
            movieSearchAdapter.notifyDataSetChanged()
//            tv_test.text = it.Search[0].Title + it.Search[0].Year + it.Search[1].Title + it.Search[1].Year + it.Search[2].Title + it.Search[2].Year
        })
    }

    private fun initAdapter() {
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_search_results.layoutManager = linearLayoutManager
        movieSearchAdapter = MovieSearchAdapter(movieSearchList)
        rv_search_results.adapter = movieSearchAdapter
    }
}