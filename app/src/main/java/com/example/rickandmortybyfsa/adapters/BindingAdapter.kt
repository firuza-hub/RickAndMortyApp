package com.example.rickandmortybyfsa.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.rickandmortybyfsa.R

@BindingAdapter("avatarImage")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {

        Glide.with(view.context)
            .load(url)
            .into(view)
    }
}