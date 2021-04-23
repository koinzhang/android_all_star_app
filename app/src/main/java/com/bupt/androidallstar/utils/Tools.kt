package com.bupt.androidallstar.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @author zhangyongkang
 * @date 2021/04/23
 * @email zyk970512@163.com
 */
object Tools {
    fun showKeyboard(context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    }

    fun hideKeyboard(view: View) {
        val imm: InputMethodManager = view.context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}