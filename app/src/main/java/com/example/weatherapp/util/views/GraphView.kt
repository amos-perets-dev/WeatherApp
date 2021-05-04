package com.example.weatherapp.util.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.example.weatherapp.R
import com.example.weatherapp.data.DataRangDay
import com.example.weatherapp.weather_app.WeatherConfiguration


class GraphView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val timeUpdate = WeatherConfiguration.TIME_TO_UPDATE

    var lengthAPoints: Int = 0
    private var arcPoints = ArrayList<PointF>()

    var currTime = 0
    var map = HashMap<Int, Point>()
    var isFirstTime = true

    var currPoint: Point? = null

    private var mTracerPaint = Paint()
        .apply {
            color = Color.YELLOW
            style = Paint.Style.FILL_AND_STROKE
        }

    private var mTracerPaintHole = Paint()
        .apply {
            color = Color.parseColor("#A600248F")
            style = Paint.Style.FILL_AND_STROKE
        }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val clipBounds = canvas?.clipBounds

        val largeRadius = (height * 0.1).toFloat()


        val pointX1 = largeRadius
        val pointY1 = (clipBounds?.bottom?.toFloat() ?: 0F) - largeRadius

        val pointX2 = (clipBounds?.right?.toFloat() ?: 0F) - largeRadius
        val pointY2 = (clipBounds?.bottom?.toFloat() ?: 0F) - largeRadius

        val pointX = (pointX2 - largeRadius) / 2
        val pointY = (height * 0.1).toFloat()

        drawCurvedArrow(
            pointX1,
            pointY1,
            pointX2,
            pointY2,
            largeRadius,
            canvas,
            pointX,
            pointY
        )

        if (currPoint == null) return

        canvas?.drawCircle(
            currPoint?.x?.toFloat() ?: 0F,
            currPoint?.y?.toFloat() ?: 0F,
            largeRadius,
            mTracerPaintHole
        ) // Radius of the coordinate circle drawn.

        canvas?.drawCircle(
            currPoint?.x?.toFloat() ?: 0F,
            currPoint?.y?.toFloat() ?: 0F,
            (largeRadius * 0.55).toFloat(),
            mTracerPaint
        ) // Radius of the coordinate circle drawn.


    }


    fun setTime(time: Int) {

        currPoint = map[time] ?: map[map.size - 1]
        Log.d("TEST_GAME", "set time, time: $time, currPoint: $currPoint")
        invalidate()
    }

    fun setRangDay(rangeDay: DataRangDay) {
        lengthAPoints = rangeDay.pointsDay
        currTime = rangeDay.currTime
        invalidate()
    }

    private fun drawCurvedArrow(
        x1: Float,
        y1: Float,
        x2: Float,
        y2: Float,
        largeRadius: Float,
        canvas: Canvas?,
        pointX: Float,
        pointY: Float
    ) {
        val mPath = Path()

        val paint = Paint()
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = Color.GREEN

        mPath.moveTo(x1, y1)
        mPath.cubicTo(x1, y1, pointX, pointY, x2, y2)
//        canvas?.drawPath(mPath, paint)

        val bitmapWidth = (x2).toInt()
        val decodeResource = drawableToBitmap(
            ContextCompat.getDrawable(context, R.drawable.background_weather),
            bitmapWidth
        )

        val decodeResourceBase = drawableToBitmap(
            ContextCompat.getDrawable(context, R.drawable.background_weather_base),
            bitmapWidth
        )

        if (decodeResource != null && decodeResourceBase != null) {
            val temp = Canvas(decodeResource)


            val transparentPaint = Paint()
            transparentPaint.color = resources.getColor(android.R.color.transparent)
            transparentPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            transparentPaint.isAntiAlias = true

            val left = largeRadius
            val top_ = (height * 0.3).toFloat()
            canvas?.drawBitmap(decodeResourceBase, 0F, top_, Paint())
            canvas?.drawBitmap(decodeResource, 0F, 0F, Paint())

            temp.drawPath(mPath, transparentPaint)

        }


        val pm = PathMeasure(mPath, false)
        val xyCoordinate = floatArrayOf(x1, y1)
        val pathLength = pm.length

        if (isFirstTime) {
            isFirstTime = false
            for (i in 0..lengthAPoints) {
                pm.getPosTan(pathLength * i / this.lengthAPoints, xyCoordinate, null)

                val point = Point(xyCoordinate[0].toInt(), xyCoordinate[1].toInt())
                map[i] = point

            }

            val time = map.size - 1 - currTime
            currPoint = map[currTime] ?: map[map.size - 1]

        }

    }

    private fun drawableToBitmap(drawable: Drawable?, width: Int): Bitmap? {
        drawable ?: return null
        if (drawable is BitmapDrawable) {
            if (drawable.bitmap != null) {
                return drawable.bitmap
            }
        }
        val bitmap: Bitmap? = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(
                width,
                height,
                Bitmap.Config.ARGB_8888
            ) // Single color bitmap will be created of 1x1 pixel
        } else {
            Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        }
        bitmap ?: return null
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}