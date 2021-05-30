package com.example.carvtask

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.opencsv.CSVReader
import java.io.InputStreamReader


class FootMatrixView(context: Context, attributes: AttributeSet) : View(context, attributes) {

    private val coordinates: List<Array<String>>
    // scale for drawing
    private val scale = 3

    init {
        // reading coordinates and radius for the circles from resources
        val csvReader = CSVReader(InputStreamReader(resources.openRawResource(R.raw.text)))
        coordinates = csvReader.readAll()
    }

    private fun drawFootMatrix(canvas: Canvas) {
        val paint = Paint()
        // Assuming radius for all the circles is the same
        val radius = coordinates[0][2].toFloat()
        paint.color = ContextCompat.getColor(context, R.color.colorPrimary)
        // Shift canvas by radius to prevent circles being cut off by getting drawn out of bounds
        canvas.translate(radius, radius)
        coordinates.forEach {
            canvas.drawCircle(
                // x coordinate * scale factor
                it[0].toFloat() * scale,
                // y coordinate * scale factor
                it[1].toFloat() * scale,
                // radius
                it[2].toFloat(),
                paint
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawFootMatrix(canvas)
    }
}
