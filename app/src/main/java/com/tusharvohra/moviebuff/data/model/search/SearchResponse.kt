package com.tusharvohra.moviebuff.data.model.search

data class SearchResponse(
    val Response: String,
    val Search: List<Search>,
    val totalResults: String
)