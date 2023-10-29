package com.example.newsapp.ui.details

import android.R
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.data.api.model.newsResponse.News
import com.example.newsapp.databinding.ActivityNewsDetailsBinding


class NewsDetailsActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityNewsDetailsBinding
    lateinit var news: News

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initViews()
        val toolbar = viewBinding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun initViews() {
        news = (intent.getParcelableExtra("news") as? News)!!
        viewBinding.news = news
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}