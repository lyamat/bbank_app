package com.example.core.presentation.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.core.presentation.ui.dialog.base.BaseDataDialog
import com.example.core.presentation.ui.dialog.common.DialogWithAction

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    lateinit var binding: T
    protected abstract fun initBinding(): T
    protected abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = initBinding()
        setContentView(binding.root)
        initView()
    }

    fun showDialogWithActionButton(
        dataToDialog: BaseDataDialog,
        actionClickPrimary: () -> Unit,
        actionClickSecondary: () -> Unit,
        tag: String
    ) {
        DialogWithAction(
            onClickButtonPrimary = { actionClickPrimary() },
            onClickButtonSecondary = { actionClickSecondary() }
        ).apply { data = dataToDialog }.show(supportFragmentManager, tag)
    }
}