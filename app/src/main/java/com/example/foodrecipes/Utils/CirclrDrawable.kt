package com.example.foodrecipes.Utils
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.Nullable


/**
 * Created by FTDsm on 2018/6/7.
 */
class CircleDrawable(drawable: Drawable, context: Context, size: Int) : Drawable() {
    private val bitmap: Bitmap
    private val bitmapShader: BitmapShader
    private val paint: Paint

    // 圆心
    private val cx: Float
    private val cy: Float

    // 半径
    private val radius: Float
    override fun draw(canvas: Canvas) {
        canvas.drawCircle(cx, cy, radius, paint)
    }

    /**
     * 缩放Drawable
     */
    private fun zoomDrawable(drawable: Drawable, w: Int, h: Int): Drawable {
        val width = drawable.intrinsicWidth
        val height = drawable.intrinsicHeight
        val oldbmp = drawableToBitmap(drawable)
        val matrix = Matrix()
        val scaleWidth = w.toFloat() / width
        val scaleHeight = h.toFloat() / height
        matrix.postScale(scaleWidth, scaleHeight)
        val newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true)
        return BitmapDrawable(null, newbmp)
    }

    /**
     * Drawable转Bitmap
     */
    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        val width = drawable.intrinsicWidth
        val height = drawable.intrinsicHeight
        val config = if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        val bitmap = Bitmap.createBitmap(width, height, config)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, width, height)
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * dp to px
     */
    private fun dip2px(context: Context, dipValue: Float): Int {
        val resources = context.resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, resources.displayMetrics).toInt()
    }

    private fun dip2px2(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(@Nullable colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    companion object {
        fun dip2px1(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }
    }

    init {
        var drawable = drawable
        var size = size
        size = dip2px(context, size.toFloat())
        drawable = zoomDrawable(drawable, dip2px(context, size.toFloat()), dip2px(context, size.toFloat()))
        bitmap = drawableToBitmap(drawable)
        bitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint = Paint()
        paint.isAntiAlias = true
        paint.shader = bitmapShader
        cx = size / 2.toFloat()
        cy = size / 2.toFloat()
        radius = size / 2.toFloat()
    }
}