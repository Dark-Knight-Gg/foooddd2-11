package com.example.foodapp2.bindingadapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.databinding.BindingAdapter

class ImageBindingAdapter {
    companion object {
        @BindingAdapter("image")
        @JvmStatic
        fun loadImage(image: ImageView, picture: ByteArray) {
            val bitmap: Bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.size) as Bitmap
            image.setImageBitmap(bitmap)
        }
    }
}