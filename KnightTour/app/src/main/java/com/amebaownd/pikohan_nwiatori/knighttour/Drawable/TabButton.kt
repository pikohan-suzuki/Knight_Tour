package com.amebaownd.pikohan_nwiatori.knighttour.Drawable

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable

enum class Direction(val value:Int){
    LEFT(0),
    TOP(1),
    RIGHT(2),
    BOTTOM(3)
}

class TabButton(private val color:Int,private val direction:Direction) : Drawable() {
    private var paint = Paint()

    override fun draw(canvas: Canvas) {
        paint.color=color
        paint.isAntiAlias=true
        val rectF= RectF(bounds)
        if(direction==Direction.LEFT) {
            canvas.drawArc(rectF, 90f, 180f, true, paint)
            rectF.left = rectF.right / 2
            canvas.drawRect(rectF, paint)
        }else if(direction==Direction.RIGHT){
            canvas.drawArc(rectF, -90f, 180f, true, paint)
            rectF.right = rectF.right / 2
            canvas.drawRect(rectF, paint)
        }else if(direction==Direction.BOTTOM){
            canvas.drawArc(rectF,0f, 180f, true, paint)
            rectF.bottom = rectF.bottom / 2
            canvas.drawRect(rectF, paint)
        }else{
            canvas.drawArc(rectF, -180f, 180f, true, paint)
            rectF.top = rectF.bottom / 2
            canvas.drawRect(rectF, paint)
        }
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }
    override fun getOpacity() = paint.alpha

    override fun setColorFilter(colorFilter: ColorFilter) {
        paint.colorFilter = colorFilter
    }
}