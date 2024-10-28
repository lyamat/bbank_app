package com.example.core.presentation.ui.dialog.base

import androidx.annotation.DrawableRes

data class BaseDataDialogGeneral(
    val title: String? = "",
    val message: String? = "",
    @DrawableRes val icon: Int? = null,
    val textPrimaryButton: String?,
    val isCancelable: Boolean = true
)