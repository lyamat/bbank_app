package com.example.core.presentation.ui.dialog.base

import androidx.annotation.DrawableRes

data class BaseDataDialogGeneral(
    val title: String? = "",
    val message: String? = "",
    @DrawableRes val icon: Int? = null,
    val textPrimaryButton: String?,
    var secondaryIsVisible: Boolean? = false,
    val isCancelable: Boolean = true,
    val dismissOnAction: Boolean = false,
    val visibleBackToSplash: Boolean = true,
)