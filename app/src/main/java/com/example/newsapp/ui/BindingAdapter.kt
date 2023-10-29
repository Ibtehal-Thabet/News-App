package com.example.newsapp.ui

import android.content.Intent
import android.graphics.drawable.Drawable
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.util.*

@BindingAdapter(value = ["app:url", "app:placeholder"])
fun bindImageWithUrl(imageView: ImageView, url: String, placeholder: Drawable?) {
    Glide.with(imageView)
        .load(url)
        .placeholder(placeholder)
        .into(imageView)
}

@BindingAdapter("imageUrl")
fun bindImageWithUrl(imageView: ImageView, url: String) {
    Glide.with(imageView)
        .load(url)
        .into(imageView)
}

@BindingAdapter("launcherUrl")
fun onClickLauncherUrl(view: View?, url: String?) {
    view?.setOnClickListener {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        view.context.startActivity(intent)
    }
}

@BindingAdapter("imageId")
fun loadImageByDrawable(imageView: ImageView, image: Int) {
    imageView.setImageResource(image)
}

@BindingAdapter("backgroundColor")
fun changeCardBackground(cardView: CardView, backgroundColor: Int) {
    cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, backgroundColor))
}

@BindingAdapter("formatDate")
fun formatDate(view: TextView, timeText: String?) {

    try {
        val timeAgo = formatTimeAgo(timeText ?: "")
        view.text = timeAgo
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun formatTimeAgo(date1: String): String {  // Note : date1 must be in   "yyyy-MM-dd hh:mm:ss"   format
    var conversionTime = ""
    try {
        val format = "yyyy-MM-dd'T'HH:mm:ss'Z'"

        val sdf = SimpleDateFormat(format)

        val datetime = Calendar.getInstance()
        var date2 = sdf.format(datetime.time).toString()

        val dateObj1 = sdf.parse(date1)
        val dateObj2 = sdf.parse(date2)
        val diff = dateObj2.time - dateObj1.time

        val diffDays = diff / (24 * 60 * 60 * 1000)
        val diffhours = diff / (60 * 60 * 1000)
        val diffmin = diff / (60 * 1000)
        val diffsec = diff / 1000
        if (diffDays > 1) {
            conversionTime += diffDays.toString() + " days "
        } else if (diffhours > 1) {
            conversionTime += (diffhours - diffDays * 24).toString() + " hours "
        } else if (diffmin > 1) {
            conversionTime += (diffmin - diffhours * 60).toString() + " min "
        } else if (diffsec > 1) {
            conversionTime += (diffsec - diffmin * 60).toString() + " sec "
        }
    } catch (ex: java.lang.Exception) {
        Log.e("formatTimeAgo", ex.toString())
    }
    if (conversionTime != "") {
        conversionTime += "ago"
    }
    return conversionTime
}
