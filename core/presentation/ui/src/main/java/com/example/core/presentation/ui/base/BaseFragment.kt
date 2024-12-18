package com.example.core.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.core.presentation.ui.R
import com.example.core.presentation.ui.dialog.base.BaseDataDialog
import com.example.core.presentation.ui.dialog.base.BaseDataDialogGeneral
import com.example.core.presentation.ui.dialog.common.DialogGeneralError
import com.example.core.presentation.ui.dialog.common.DialogProgressBar
import com.example.core.presentation.ui.dialog.common.DialogWithAction
import com.example.core.presentation.ui.util.showDialog

abstract class BaseFragment<T : ViewBinding>(private val bindingInflater: (layoutInflater: LayoutInflater) -> T) :
    Fragment() {

    var binding: T by viewBinding()

    protected abstract fun setupView()
    protected abstract fun onClickButtonCancel()

    private var dialog: DialogProgressBar? = null
    private var dialogGeneralError: DialogGeneralError? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingInflater.invoke(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    protected fun showRetryDialog(title: String, error: String, actionRetry: () -> Unit) {
        val content = BaseDataDialog(
            title = title,
            content = error,
            primaryButtonText = getString(R.string.ok),
            primaryButtonShow = true,
            secondaryButtonText = "",
            secondaryButtonShow = false,
            buttonWithIconShow = true,
            buttonWithIconText = getString(R.string.retry),
            icon = R.drawable.ic_info,
        )

        showDialogWithActionButton(
            dataToDialog = content,
            actionClickPrimary = { },
            actionClickButtonWithIcon = { actionRetry() }
        )
    }

    protected fun showDialogWithActionButton(
        dataToDialog: BaseDataDialog,
        actionClickPrimary: () -> Unit,
        actionClickSecondary: (() -> Unit)? = null,
        actionClickButtonWithIcon: (() -> Unit)? = null,
        tag: String? = "",
    ) {
        val dialog = DialogWithAction(
            onClickButtonPrimary = { actionClickPrimary() },
            onClickButtonSecondary = { actionClickSecondary?.invoke() },
            onClickButtonWithIcon = { actionClickButtonWithIcon?.invoke() }
        ).apply { data = dataToDialog }
        if (tag?.isNotEmpty() == true) dialog.show(childFragmentManager, tag)
        else childFragmentManager.showDialog(dialog)
    }

    protected fun showDialogProgressBar() {
        dialog = DialogProgressBar { onClickButtonCancel() }
        dialog?.show(childFragmentManager, dialog?.tag)
    }

    protected fun hideDialogProgressBar() {
        dialog?.dismiss()
        dialog = null
    }

    private fun showGeneralError(
        data: BaseDataDialogGeneral,
        dismissClick: () -> Unit
    ) {
        dialogGeneralError = DialogGeneralError(data, dismissClick)
        dialogGeneralError?.show(childFragmentManager, tag)
    }

    protected fun showDialogGeneralError(title: String, error: String) {
        showGeneralError(
            BaseDataDialogGeneral(
                title = title,
                message = error,
                icon = R.drawable.ic_info,
                textPrimaryButton = getString(R.string.ok)
            ),
            dismissClick = { dismissDialogGeneralError() }
        )
    }

    private fun onDismissDialogGeneralError(): () -> Unit = {
        dialogGeneralError?.dismiss()
        dialogGeneralError = null
    }

    private fun dismissDialogGeneralError() {
        dialogGeneralError?.dismiss()
        dialogGeneralError = null
    }

    override fun onDestroyView() {
        dialog = null
        dialogGeneralError = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        dialog = null
        dialogGeneralError = null
        super.onDestroy()
    }
}