package com.tusharvohra.moviebuff.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tusharvohra.moviebuff.R
import com.tusharvohra.moviebuff.data.model.search.Search
import kotlinx.android.synthetic.main.movie_search_item.view.*

/**
 * Created by tusharvohra on 1/12/20.
 */
class MovieSearchAdapter(private val data: ArrayList<Search>) :
    RecyclerView.Adapter<MovieSearchAdapter.BaseViewHolder<Search>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Search> {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_search_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Search>, position: Int) {
        try {
            holder.bind(data[position], position)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount() = data.size

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T, position: Int)
    }

    inner class MovieViewHolder(itemView: View) : BaseViewHolder<Search>(itemView) {
        override fun bind(item: Search, position: Int) {
            Glide.with(itemView.context).load(item.Poster).into(itemView.iv_poster)
            itemView.tv_movie_title.text = "Title: ${item.Title}"
            itemView.tv_type.text = "Genre: ${item.Type}"
            itemView.tv_year.text = "Year: ${item.Year}"
        }

    }
}