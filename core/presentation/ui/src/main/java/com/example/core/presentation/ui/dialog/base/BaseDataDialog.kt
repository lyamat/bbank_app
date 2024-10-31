package com.example.core.presentation.ui.dialog.base

import androidx.annotation.DrawableRes

class BaseDataDialog(
    val title: String,
    val content: String,
    val secondaryButtonShow: Boolean,
    val secondaryButtonText: String,
    val primaryButtonShow: Boolean,
    val primaryButtonText: String,
    @DrawableRes val icon: Int? = null,
    val buttonWithIconShow: Boolean = false,
    val buttonWithIconText: String = ""
)