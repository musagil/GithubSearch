package com.github.search.ktx

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

/**
 * Close keyboard and clear focus on the receiver view.
 */
fun View.hideKeyboard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    if (this is ViewGroup) {
        imm.hideSoftInputFromWindow(focusedChild?.windowToken, 0)
    } else {
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
    clearFocus()
}
