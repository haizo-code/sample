package com.example.myapplication3.sample.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition
import com.example.myapplication3.R

@GlideModule
class MyAppGlideModule : AppGlideModule()

object GlideImageUtils {

    fun setImage(
        context: Context, imageToLoad: Any?, fastLoadUrl: Any? = null, imageView: ImageView,
        callback: ImageRequestListener.Callback? = null
    ) {
        setImage(context, false, imageToLoad, fastLoadUrl, imageView, callback)
    }

    fun setCircleImage(
        context: Context, imageToLoad: Any?, fastLoadUrl: Any? = null, imageView: ImageView,
        callback: ImageRequestListener.Callback? = null
    ) {
        setImage(context, true, imageToLoad, fastLoadUrl, imageView, callback)
    }

    fun preLoadImage(context: Context, imageUrl: String?) {
        Glide.with(context)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .preload()
    }

    @SuppressLint("CheckResult")
    private fun setImage(
        context: Context, isCircle: Boolean, imageToLoad: Any?, fastLoadUrl: Any? = null,
        imageView: ImageView, callback: ImageRequestListener.Callback? = null
    ) {

        val requestOptions = RequestOptions()
            .apply {
                if (isCircle) {
                    circleCrop().error(R.color.red)
                } else {
                    centerCrop().error(R.color.gray)
                }
            }

        getGlide(context)
            .load(imageToLoad)
            .apply {
                fastLoadUrl?.let { thumbnail(getGlide(context).load(it).apply(requestOptions)) }
            }
            .transition(DrawableTransitionOptions.withCrossFade())
            .listener(ImageRequestListener(callback))
            .apply(requestOptions)
            .into(imageView)
    }

    private fun getGlide(context: Context): RequestManager {
        return Glide.with(context.applicationContext)
    }
}

class ImageRequestListener(private val callback: Callback? = null) : RequestListener<Drawable> {

    interface Callback {
        fun onFailure(message: String?)
        fun onSuccess(dataSource: String)
    }

    override fun onResourceReady(
        resource: Drawable, model: Any, target: Target<Drawable>,
        dataSource: DataSource, isFirstResource: Boolean
    ): Boolean {
        callback?.onSuccess(dataSource.toString())
        target.onResourceReady(resource, DrawableCrossFadeTransition(500, isFirstResource))
        return true
    }

    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>,
        isFirstResource: Boolean
    ): Boolean {
        callback?.onFailure(e?.message)
        return false
    }
}