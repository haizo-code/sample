package com.example.myapplication3.sample.utils

import android.graphics.Color
import android.graphics.Point
import android.graphics.Rect
import android.view.View
import androidx.annotation.FloatRange
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Random
import java.util.TimeZone
import java.util.concurrent.TimeUnit

object Utils {
    /**
     * Calculates the amount of the visibility of a [View] on the screen.
     * Used to determine the visibility area ratio (a value between 0.0 and 1.0)
     *
     * @param view the view to check its visibility
     * @return the visibility are offset (a value between 0.0 and 1.0)
     */
    @FloatRange(from = 0.0, to = 1.0)
    fun getViewVisibleAreaOffset(view: View): Float {
        if (view.parent == null) {
            return 0f
        }
        val drawRect = Rect()
        val playerRect = Rect()
        view.getDrawingRect(drawRect)
        val drawArea = drawRect.width() * drawRect.height()
        val isVisible = view.getGlobalVisibleRect(playerRect, Point())
        if (isVisible && drawArea > 0) {
            val visibleArea = playerRect.height() * playerRect.width()
            return visibleArea / drawArea.toFloat()
        }
        return 0f
    }

    fun getTimeString(
        timeStamp: Long,
        isShowHoursEventItsZero: Boolean = false,
        isShowMillis: Boolean = false,
        locale: Locale = Locale.ENGLISH,
    ): String {
        var mainFormat = "mm:ss"
        if (TimeUnit.MILLISECONDS.toHours(timeStamp) > 0 || isShowHoursEventItsZero) {
            mainFormat = mainFormat.prependIndent("HH:")
        }
        if (isShowMillis) {
            mainFormat = mainFormat.plus(".SS")
        }
        val dateFormat = SimpleDateFormat(mainFormat, locale)
        dateFormat.timeZone = TimeZone.getTimeZone("GMT+0")
        return dateFormat.format(Date(timeStamp))
    }

    fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }
}