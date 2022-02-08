package com.example.apidatabaseproject.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apidatabaseproject.repository.NavigationNewsRepository

class NavigationNewsViewModelProviderFactory (
    val app: Application,
    val newsRepository: NavigationNewsRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NavigationNewsViewModel(app, newsRepository) as T
    }


}