package com.msaggik.common_util

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object Utils {

    fun getFormatDate(date: String): LocalDate? {
        var localDate: LocalDate?
        try {
            localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        } catch (e: DateTimeParseException) {
            e.printStackTrace()
            localDate = null
        }
        return localDate
    }

    fun visibilityView(views: Array<View>? = emptyArray(), v: View) {
        if (views != null) {
            for(view in views) view.visibility = View.GONE
        }
        v.visibility = View.VISIBLE
    }

    fun doToPx(dp: Float, context: Context): Int {
        return TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
            .toInt()
    }

    fun closeKeyBoard(context: Context, view: View) {
        val closeKeyBoard = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        closeKeyBoard?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}