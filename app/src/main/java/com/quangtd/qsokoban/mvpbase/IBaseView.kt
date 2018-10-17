package com.quangtd.qsokoban.mvpbase

import android.content.Context
import android.os.Bundle
import com.quangtd.qsokoban.util.DialogUtils

/**
 * Created by QuangTD
 * on 1/12/2018.
 */
interface IBaseView {
    fun getViewContext(): Context

    fun showLoading() {
        DialogUtils.showLoadingDialog(getViewContext())
    }

    fun hideLoading() {
        DialogUtils.hideLoadingDialog()
    }

    fun showErrorDialog(message: String) {
        DialogUtils.showError(getViewContext(), message){}
    }

    fun startActivity(clazz: Class<*>, bundle: Bundle)
}