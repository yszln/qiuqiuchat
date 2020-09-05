package com.yszln.lib.loading

import android.app.Dialog
import android.content.Context
import com.yszln.lib.R


/**
 * 加载框
 */
class LoadingDialog(context: Context) : Dialog(context, R.style.dialog),
    ILoading {
    init {

        setContentView(R.layout.dialog_loading)
        setCanceledOnTouchOutside(false)

    }


    override fun showLoading() {
        show()
    }

    override fun dismissLoading() {
        dismiss()
    }
}