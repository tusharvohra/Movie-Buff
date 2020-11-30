package com.tusharvohra.moviebuff.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tusharvohra.moviebuff.R
import com.tusharvohra.moviebuff.data.model.movie.MovieResponse
import kotlinx.android.synthetic.main.movie_list_item.view.*

/**
 * Created by tusharvohra on 29/11/20.
 */
class MovieListAdapter(private val data: ArrayList<MovieResponse>) :
    RecyclerView.Adapter<MovieListAdapter.BaseViewHolder<MovieResponse>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<MovieResponse> {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<MovieResponse>, position: Int) {
        try {
            holder.bind(data[position], position)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T, position: Int)
    }

    inner class MovieViewHolder(itemView: View) : BaseViewHolder<MovieResponse>(itemView) {
        override fun bind(item: MovieResponse, position: Int) {
            Glide.with(itemView.context).load(item.poster).into(itemView.iv_poster)
            itemView.tv_movie_title.text = item.title
            itemView.tv_director.text = item.director
            itemView.tv_year.text = item.year
        }

    }

}