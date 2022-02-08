package com.example.apidatabaseproject.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.apidatabaseproject.R
import com.example.apidatabaseproject.database.ArticleDatabase
import com.example.apidatabaseproject.repository.NavigationNewsRepository
import com.example.apidatabaseproject.ui.viewmodel.NavigationNewsViewModel
import com.example.apidatabaseproject.ui.viewmodel.NavigationNewsViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_navigation_news.*


class NavigationNewsActivity : AppCompatActivity(){
    lateinit var viewModel: NavigationNewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_news)


        val newsRepository = NavigationNewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NavigationNewsViewModelProviderFactory(application, newsRepository)

        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NavigationNewsViewModel::class.java)
        naw_view.setupWithNavController(newsNavHostFragment2.findNavController())
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.go_back,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.back_option -> {
                val intent = Intent(this, NewsActivity::class.java)
                this.startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

