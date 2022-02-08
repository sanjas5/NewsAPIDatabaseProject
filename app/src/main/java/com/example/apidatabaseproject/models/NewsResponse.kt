package com.example.apidatabaseproject.models

import com.example.apidatabaseproject.models.Article

data class NewsResponse(
        val articles: MutableList<Article>,
        val status: String,
        val totalResults: Int
)