package com.example.core.presentation.ui.util

import android.view.View
import android.widget.RadioButton
import com.google.android.material.chip.Chip

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun Chip.checked() {
    this.isChecked = true
}

fun Chip.unchecked() {
    this.isChecked = false
}

fun RadioButton.checked() {
    this.isChecked = true
}

fun RadioButton.unchecked() {
    this.isChecked = false
}