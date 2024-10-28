package com.example.core.presentation.ui.dialog.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.core.presentation.ui.databinding.FragmentDialogWithActionBinding
import com.example.core.presentation.ui.dialog.base.BaseDataDialog
import com.example.core.presentation.ui.util.hide
import com.example.core.presentation.ui.util.show

class DialogWithAction(
    private val onClickButtonPrimary: () -> Unit,
    private val onClickButtonWithIcon: (() -> Unit)? = null,
    private val onClickButtonSecondary: (() -> Unit)? = null
) : DialogFragment() {

    lateinit var binding: FragmentDialogWithActionBinding
    lateinit var data: BaseDataDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDialogWithActionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupListeners()
    }

    fun setDialog(data: BaseDataDialog) {
        this.data = data
    }

    override fun onStart() {
        super.onStart()
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.GRAY).apply { alpha = 150 })
    }

    private fun setupView() = with(binding) {
        tvTitleCommonDialogSingleButton.text = data.title
        tvContentCommonDialogSingleButton.text = data.content
        btnActionPrimaryCommonDialog.text = data.primaryButtonText
        btnActionSecondaryCommonDialog.text = data.secondaryButtonText
        if (data.buttonWithIconText.isNotBlank()) {
            btnActionPrimaryWithIconCommonDialog.text = data.buttonWithIconText
        }

        data.icon?.let { ivIconDialogWithAction.setImageResource(it) }

        if (data.isIconShow) ivExclamationBlack.show()
        else ivExclamationBlack.hide()

        if (data.buttonWithIconShow) {
            btnActionPrimaryWithIconCommonDialog.show()
        }
        validateButtonPrimaryAndSecondary()
    }

    private fun validateButtonPrimaryAndSecondary() = with(binding) {
        when {
            data.primaryButtonShow && data.secondaryButtonShow -> {
                btnActionPrimaryCommonDialog.show()
                btnActionSecondaryCommonDialog.show()
            }

            data.primaryButtonShow -> {
                btnActionPrimaryCommonDialog.show()
                btnActionSecondaryCommonDialog.hide()
            }

            data.secondaryButtonShow -> {
                btnActionSecondaryCommonDialog.show()
                btnActionPrimaryCommonDialog.hide()
            }

            else -> {
                btnActionSecondaryCommonDialog.hide()
                btnActionPrimaryCommonDialog.hide()
            }
        }
    }

    private fun setupListeners() = with(binding) {
        btnActionSecondaryCommonDialog.setOnClickListener {
            onClickButtonSecondary?.invoke()
            dismiss()
        }
        btnActionPrimaryCommonDialog.setOnClickListener {
            onClickButtonPrimary()
            dismiss()
        }
        btnActionPrimaryWithIconCommonDialog.setOnClickListener {
            onClickButtonWithIcon?.invoke()
            dismiss()
        }
    }
}