package com.mayandro.utility.extensions

import android.content.Context
import android.graphics.Paint
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}


fun ImageView.setImage(url: String, requestOptions: RequestOptions) {
    Glide.with(this.context)
        .setDefaultRequestOptions(requestOptions)
        .load(url)
        .fitCenter()
        .thumbnail(0.3f)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .transition(DrawableTransitionOptions.withCrossFade(1000))
        .into(this)
}

inline var TextView.strike: Boolean
    set(visible) {
        paintFlags = if (visible) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
    get() = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG == Paint.STRIKE_THRU_TEXT_FLAG