package com.example.weatherapp.util.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ImageUtilImpl @Inject constructor(private val context: Context) : IImageUtil {

    override fun loadImage(
        url: String
    ): Single<Bitmap> {

        return Single.create<Bitmap> { emitter ->
            Glide.with(context)
                .asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        emitter.onSuccess(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
        }.subscribeOn(Schedulers.io())


    }

}