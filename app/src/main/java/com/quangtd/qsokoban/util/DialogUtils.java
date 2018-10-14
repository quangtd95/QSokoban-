package com.quangtd.qsokoban.util;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;

import com.quangtd.qsokoban.R;

import libs.mjn.prettydialog.PrettyDialog;


public class DialogUtils {
    private static Dialog dialog;

    private DialogUtils() {
        //no-op
    }

    public static void showLoadingDialog(Context context) {
        if (null == context) return;
        if (dialog != null) {
            if (dialog.isShowing()) {
                try {
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            dialog = null;
        }
        dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.show();
    }


    public static void hideLoadingDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public static void showError(Context context, String message) {
        PrettyDialog prettyDialog = new PrettyDialog(context);
        prettyDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        prettyDialog.setCancelable(true);
        prettyDialog.setTitle(message).show();
    }
}
