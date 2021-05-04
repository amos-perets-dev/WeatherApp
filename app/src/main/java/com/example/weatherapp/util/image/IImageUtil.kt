package com.example.weatherapp.util.image

import android.graphics.Bitmap
import io.reactivex.Single

interface IImageUtil {

    /**
     * Load tne image from the server
     */
    fun loadImage(
        url: String
    ): Single<Bitmap>
}