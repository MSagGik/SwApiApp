package io.github.msaggik.util

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object Utils {

    fun getFormatDate(date: String): LocalDate? =
        runCatching {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        }.getOrNull()
}

fun View.showAndHideOthers(hiddenViews: Array<View>?) {
    hiddenViews?.let { views ->
        views.forEach {
            it.visibility = View.GONE
        }
    }
    visibility = View.VISIBLE
}

fun Context.closeKeyBoard(view: View) {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(view.windowToken, 0)
}