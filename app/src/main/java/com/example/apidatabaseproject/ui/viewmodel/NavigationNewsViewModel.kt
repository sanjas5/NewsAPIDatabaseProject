package com.example.apidatabaseproject.ui.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.ContactsContract
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.apidatabaseproject.NewsApplication
import com.example.apidatabaseproject.models.Article
import com.example.apidatabaseproject.models.NewsResponse
import com.example.apidatabaseproject.repository.NavigationNewsRepository
import com.example.apidatabaseproject.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NavigationNewsViewModel(
    app: Application,
    val newsRepository: NavigationNewsRepository,
) : AndroidViewModel(app) {


    val businessNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var businessNewsPage = 1
    var businessNewsResponse: NewsResponse? = null

    val entertainmentNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var entertainmentNewsPage = 1
    var entertainmentNewsResponse: NewsResponse? = null

    val scienceNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var scienceNewsPage = 1
    var scienceNewsResponse: NewsResponse? = null

    val technologyNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var technologyNewsPage = 1
    var technologyNewsResponse: NewsResponse? = null

    init {
        getBusinessNews("business")
        getEntertainmentNews("entertainment")
        getScienceNews("science")
        getTechnologyNews("technology")
    }


    fun getBusinessNews(categoryCode: String) = viewModelScope.launch {
        safeBusinessNewsCall(categoryCode)
    }

    fun getEntertainmentNews(categoryCode: String) = viewModelScope.launch {
        safeEntertainmentNewsCall(categoryCode)
    }

    fun getScienceNews(categoryCode: String) = viewModelScope.launch {
        safeScienceNewsCall(categoryCode)
    }

    fun getTechnologyNews(categoryCode: String) = viewModelScope.launch {
        safeTechnologyNewsCall(categoryCode)
    }

    private fun handleBusinessNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                businessNewsPage++
                if (businessNewsResponse == null) {
                    businessNewsResponse = resultResponse
                } else {
                    val oldArticles = businessNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(businessNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleEntertainmentNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                entertainmentNewsPage++
                if (entertainmentNewsResponse == null) {
                    entertainmentNewsResponse = resultResponse
                } else {
                    val oldArticles = entertainmentNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success( entertainmentNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleScienceNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                scienceNewsPage++
                if (scienceNewsResponse == null) {
                    scienceNewsResponse = resultResponse
                } else {
                    val oldArticles = scienceNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success( scienceNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleTechnologyNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                technologyNewsPage++
                if (technologyNewsResponse == null) {
                    technologyNewsResponse = resultResponse
                } else {
                    val oldArticles = technologyNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(technologyNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeBusinessNewsCall(categoryCode: String) {
        businessNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.getBusinessNews(categoryCode, businessNewsPage)
                businessNews.postValue(handleBusinessNewsResponse(response))
            } else {
                businessNews.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> businessNews.postValue(Resource.Error("Network Failure"))
                else -> businessNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private suspend fun safeEntertainmentNewsCall(categoryCode: String) {
        entertainmentNews.postValue(Resource.Loading())
            try {
                if(hasInternetConnection()) {
                    val response = newsRepository.getEntertainmentNews(categoryCode, entertainmentNewsPage)
                    entertainmentNews.postValue(handleEntertainmentNewsResponse(response))
                } else {
                    entertainmentNews.postValue(Resource.Error("No internet connection"))
                }
            } catch(t: Throwable) {
                when(t) {
                    is IOException -> entertainmentNews.postValue(Resource.Error("Network Failure"))
                    else -> entertainmentNews.postValue(Resource.Error("Conversion Error"))
                }
            }
        }

    private suspend fun safeScienceNewsCall(categoryCode: String) {
        scienceNews.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = newsRepository.getScienceNews(categoryCode, scienceNewsPage)
                scienceNews.postValue(handleScienceNewsResponse(response))
            } else {
                scienceNews.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> scienceNews.postValue(Resource.Error("Network Failure"))
                else -> scienceNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private suspend fun safeTechnologyNewsCall(categoryCode: String) {
        technologyNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.getTechnologyNews(categoryCode,  technologyNewsPage)
                technologyNews.postValue(handleTechnologyNewsResponse(response))
            } else {
                technologyNews.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException ->  technologyNews.postValue(Resource.Error("Network Failure"))
                else ->  technologyNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<NewsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ContactsContract.CommonDataKinds.Email.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}
