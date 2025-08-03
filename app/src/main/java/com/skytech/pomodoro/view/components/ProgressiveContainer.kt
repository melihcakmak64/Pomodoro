package com.skytech.pomodoro.view.components


import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlin.math.sign
import kotlin.math.sqrt

@Composable
fun ProgressOutlineRoundedRect(
    modifier: Modifier = Modifier,
    progress: Float,
    color: Color,
    strokeWidth: Dp,
    cornerRadius: Dp // köşe yarıçapı
) {
    val stroke = with(LocalDensity.current) { strokeWidth.toPx() }
    val radiusPx = with(LocalDensity.current) { cornerRadius.toPx() }


    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val r = radiusPx

        // 4 kenar + 2 köşe (sadece alt köşeler)
        val totalLength =
            h + (w - 2 * r) + (h - 2 * r) + (Math.PI * r).toFloat() // Sağ kenar + alt yaylar + sol kenar


        var remaining = totalLength * progress

        fun drawLineSegment(start: Offset, end: Offset, length: Float) {
            val len = remaining.coerceAtMost(length)
            if (len <= 0f) return

            val dx = end.x - start.x
            val dy = end.y - start.y
            val distance = sqrt(dx * dx + dy * dy)
            val ratio = len / distance

            val mid = Offset(
                start.x + dx * ratio,
                start.y + dy * ratio
            )

            drawLine(color, start, mid, strokeWidth = stroke, cap = StrokeCap.Round)
            remaining -= len
        }

        fun drawArcSegment(
            topLeft: Offset,
            sweepAngle: Float,
            startAngle: Float,
            radius: Float
        ) {
            val arcLength = (Math.PI * radius * sweepAngle / 180f).toFloat()
            val len = remaining.coerceAtMost(arcLength)
            if (len <= 0f) return

            val sweep = 360 * (len / (2 * Math.PI * radius)).toFloat()
            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweep * (sweepAngle.sign),
                useCenter = false,
                topLeft = topLeft,
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
            remaining -= len
        }

        // 1. Sağ çizgi (sağ üst → sağ alt - r kadar kısa)
        drawLineSegment(Offset(w, 0f), Offset(w, h - r), h - r)

// 2. Sağ alt köşe yay
        drawArcSegment(
            topLeft = Offset(w - 2 * r, h - 2 * r),
            sweepAngle = 90f,
            startAngle = 0f,
            radius = r
        )

// 3. Alt çizgi (sağ alt → sol alt - 2r kadar kısa)
        drawLineSegment(Offset(w - r, h), Offset(r, h), w - 2 * r)

// 4. Sol alt köşe yay
        drawArcSegment(
            topLeft = Offset(0f, h - 2 * r),
            sweepAngle = 90f,
            startAngle = 90f,
            radius = r
        )

// 5. Sol çizgi (sol alt → sol üst - r kadar kısa)
        drawLineSegment(Offset(0f, h - r), Offset(0f, 0f), h - r)
    }
}

