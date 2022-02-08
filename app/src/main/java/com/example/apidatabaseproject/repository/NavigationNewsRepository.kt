package com.example.apidatabaseproject.repository

import com.example.apidatabaseproject.api.RetrofitInstance
import com.example.apidatabaseproject.database.ArticleDatabase
import com.example.apidatabaseproject.models.Article

class NavigationNewsRepository(
        val db: ArticleDatabase
) {
    suspend fun getBusinessNews(categoryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBusinessNews(categoryCode, pageNumber)

    suspend fun getEntertainmentNews(categoryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getEntertainmentNews(categoryCode, pageNumber)

    suspend fun getScienceNews(categoryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getScienceNews(categoryCode, pageNumber)

    suspend fun getTechnologyNews(categoryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getTechnologyNews(categoryCode, pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

}