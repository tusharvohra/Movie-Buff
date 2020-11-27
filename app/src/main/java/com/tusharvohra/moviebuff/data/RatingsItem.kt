package com.tusharvohra.moviebuff.data

import com.google.gson.annotations.SerializedName

data class RatingsItem(@SerializedName("Value")
                       val value: String = "",
                       @SerializedName("Source")
                       val source: String = "")