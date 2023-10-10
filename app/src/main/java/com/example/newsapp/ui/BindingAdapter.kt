package com.example.newsapp.ui

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter(value = ["app:url", "app:placeholder"])
fun bindImageWithUrl(imageView: ImageView, url: String, placeholder: Drawable?) {
    Glide.with(imageView)
        .load(url)
        .placeholder(placeholder)
        .into(imageView)
}