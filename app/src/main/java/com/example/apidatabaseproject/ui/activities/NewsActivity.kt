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
import com.example.apidatabaseproject.R.id
import com.example.apidatabaseproject.database.ArticleDatabase
import com.example.apidatabaseproject.repository.NewsRepository
import com.example.apidatabaseproject.ui.viewmodel.NewsViewModel
import com.example.apidatabaseproject.ui.viewmodel.NewsViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_news.*


class NewsActivity : AppCompatActivity(){
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)

        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)
        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            id.share_option -> {
                val intent = Intent(this, SharingActivity::class.java)
                this.startActivity(intent)
                return true
            }
            id.activity_option -> {
                val intent = Intent(this, NavigationNewsActivity::class.java)
                this.startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
