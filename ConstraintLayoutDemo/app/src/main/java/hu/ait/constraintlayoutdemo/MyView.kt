package hu.ait.constraintlayoutdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class MyView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var paintBackground: Paint
    var paintLine: Paint

    init {
        paintBackground = Paint()
        paintBackground.setColor(Color.GREEN)

        paintLine = Paint()
        paintLine.setColor(Color.RED)
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(0f,0f,
            width.toFloat(),
            height.toFloat(),paintBackground)

        canvas.drawLine(0f,0f,
            width.toFloat(),
            height.toFloat(),paintLine)

    }


}