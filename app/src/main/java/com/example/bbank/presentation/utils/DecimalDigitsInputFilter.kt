package com.example.bbank.presentation.utils

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Matcher
import java.util.regex.Pattern

internal class DecimalDigitsInputFilter : InputFilter {
    private val mPattern: Pattern = Pattern.compile("^[0-9]*+((\\.[0-9]{0,2})?)?$")

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val matcher: Matcher = mPattern.matcher(dest.toString() + source.toString())
        if (!matcher.matches()) {
            return ""
        }
        return null
    }
}