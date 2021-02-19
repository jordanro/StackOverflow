package com.jordanro.stackoverflow.utils

import android.content.Context
import android.util.TypedValue

class UIUtil {

    companion object{

        fun convertDPToPixel(context: Context, dp:Float): Int{
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.resources.displayMetrics
            ).toInt()
        }
    }
}