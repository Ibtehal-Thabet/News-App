package com.example.newsapp.ui.categories

import com.example.newsapp.R

data class Category(
    var id: String,
    var title: Int,
    var imageId: Int,
    var cardColor: Int
) {
    companion object {
        fun getCategoryList(): List<Category> {
            return listOf(
                Category(
                    "general",
                    R.string.general,
                    R.drawable.general,
                    R.color.blue
                ),
                Category(
                    "sports",
                    R.string.sports,
                    R.drawable.sports,
                    R.color.red
                ),
                Category(
                    "health",
                    R.string.health,
                    R.drawable.health,
                    R.color.bink
                ),
                Category(
                    "technology",
                    R.string.tech,
                    R.drawable.technology,
                    R.color.babyBlue
                ),
                Category(
                    "business",
                    R.string.business,
                    R.drawable.bussines,
                    R.color.orange
                ),
                Category(
                    "science",
                    R.string.science,
                    R.drawable.science,
                    R.color.yellow
                ),
            )
        }
    }
}