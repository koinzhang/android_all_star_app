package com.bupt.androidallstar.utils

import android.content.Context
import android.widget.Toast

/**
 * @author zhangyongkang
 * @date 2021/03/03
 * com.example.opencvkotlin.util
 */
object CommonUtils {
    fun Context.showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}