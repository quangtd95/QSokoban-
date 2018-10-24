package com.quangtd.qsokoban.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import com.quangtd.qsokoban.R;

import org.jetbrains.annotations.NotNull;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


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
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
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

    public static void showError(Context context, String message, PrettyDialogCallback prettyDialogCallback) {
        PrettyDialog prettyDialog = new PrettyDialog(context);
        prettyDialog.setCancelable(false);
        prettyDialog.setIconCallback(prettyDialogCallback);
        prettyDialog.setTitle(message).show();
    }

    public static void showReload(Context context, PrettyDialogCallback onClickReload, PrettyDialogCallback onClickCancel) {
        PrettyDialog prettyDialog = new PrettyDialog(context);
        prettyDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        prettyDialog.setCancelable(true);
        prettyDialog.setTitle(context.getString(R.string.reload)).setIcon(R.drawable.reload)
                .addButton(context.getString(R.string.OK), R.color.pdlg_color_black, R.color.bgGame, () -> {
                    onClickReload.onClick();
                    prettyDialog.dismiss();
                })
                .addButton(context.getString(R.string.cancel), R.color.pdlg_color_black, R.color.pdlg_color_white, () -> {
                    onClickCancel.onClick();
                    prettyDialog.dismiss();
                })
                .show();
    }

    public static void showUnlockAlert(@NotNull Context context) {
        PrettyDialog prettyDialog = new PrettyDialog(context);
        prettyDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        prettyDialog.setTitle(context.getString(R.string.lock_level_warning));
        prettyDialog.addButton(context.getString(R.string.OK), R.color.pdlg_color_black, R.color.pdlg_color_green, prettyDialog::dismiss);
        prettyDialog.setCancelable(true);
        prettyDialog.setIcon(R.drawable.btn_locked).setIconTint(R.color.pdlg_color_yellow)
                .setIconCallback(prettyDialog::dismiss);
        prettyDialog.show();
    }

    public static void showExitConfirm(Context context, PrettyDialogCallback onClickExit, PrettyDialogCallback onClickCancel) {
        PrettyDialog prettyDialog = new PrettyDialog(context);
        prettyDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        prettyDialog.setCancelable(true);
        prettyDialog.setIcon(R.drawable.ic_close)
                .setTitle(context.getString(R.string.exit_game_confirm))
                .addButton(context.getString(R.string.OK), R.color.pdlg_color_black, R.color.pdlg_color_red, () -> {
                    prettyDialog.dismiss();
                    onClickExit.onClick();
                })
                .addButton(context.getString(R.string.cancel), R.color.pdlg_color_black, R.color.pdlg_color_white, () -> {
                    onClickCancel.onClick();
                    prettyDialog.dismiss();
                })
                .show();
    }

    public static void showWin(Context context, String title, PrettyDialogCallback onClickNext) {
        PrettyDialog prettyDialog = new PrettyDialog(context);
        prettyDialog.setCancelable(true);
        prettyDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        prettyDialog.setTitle(title).setIcon(R.drawable.ic_win)
                .addButton(context.getString(R.string.next), R.color.pdlg_color_black, R.color.pdlg_color_green, () -> {
                    prettyDialog.dismiss();
                    onClickNext.onClick();
                })
                .show();
    }

    public static void showPlaceBoomToDestroy(@NotNull Context context) {
        PrettyDialog prettyDialog = new PrettyDialog(context);
        prettyDialog.setCancelable(true);
        prettyDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        prettyDialog.setIcon(R.drawable.boom);
        prettyDialog.setTitle(context.getString(R.string.place_boom));
        prettyDialog.show();
    }

    public static void showWinBonusCoin(@NotNull Context context, @NotNull String title, int coin, PrettyDialogCallback onClickNext) {
        PrettyDialog prettyDialog = new PrettyDialog(context);
        prettyDialog.setCancelable(true);
        prettyDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        prettyDialog.setMessage("coin + " + coin);
        prettyDialog.setTitle(title).setIcon(R.drawable.ic_win)
                .addButton(context.getString(R.string.next), R.color.pdlg_color_black, R.color.pdlg_color_green, () -> {
                    prettyDialog.dismiss();
                    onClickNext.onClick();
                })
                .show();
    }

    public static void showNotEnoughBoomAlert(@NotNull Context context) {
        PrettyDialog prettyDialog = new PrettyDialog(context);
        prettyDialog.setCancelable(true);
        prettyDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        prettyDialog.setIcon(R.drawable.boom);
        prettyDialog.setTitle(context.getString(R.string.not_enough_boom));
        prettyDialog.show();
    }

    public static void showRewardBoom(@NotNull Context context) {
        PrettyDialog prettyDialog = new PrettyDialog(context);
        prettyDialog.setCancelable(true);
        prettyDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        prettyDialog.setTitle(context.getString(R.string.receive_more_boom)).setIcon(R.drawable.boom)
                .addButton(context.getString(R.string.next), R.color.pdlg_color_black, R.color.pdlg_color_green, prettyDialog::dismiss)
                .show();
    }
}
