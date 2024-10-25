package com.example.core.presentation.ui.util

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

fun FragmentManager.showDialog(fragment: DialogFragment, tag: String) {
    if (this.findFragmentByTag(tag)?.isAdded != true) {
        fragment.showNow(this, tag)
    }
}

fun FragmentManager.showDialog(fragment: DialogFragment) {
    showDialog(fragment, fragment.tag ?: fragment::class.java.simpleName)
}