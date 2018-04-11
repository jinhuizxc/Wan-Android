package com.hsf1002.sky.wanandroid.utils;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.hsf1002.sky.wanandroid.R;

/**
 * Created by hefeng on 18-4-10.
 */

public class CommonAlertDialog {
    private AlertDialog alertDialog;

    public static CommonAlertDialog newInstance()
    {
        return CommonAlertDialogHolder.COMMON_ALERT_DIALOG;
    }

    private static class CommonAlertDialogHolder
    {
        private static final CommonAlertDialog COMMON_ALERT_DIALOG = new CommonAlertDialog();
    }

    public void cancelDialog(boolean isAdd)
    {
        if (isAdd && alertDialog != null && alertDialog.isShowing())
        {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

    public void showDialog(Activity activity, String content, String btn_content)
    {
        if (activity == null)
        {
            return;
        }

        if (alertDialog == null)
        {
            alertDialog = new AlertDialog.Builder(activity, R.style.myCorDialog).create();
        }

        if (!alertDialog.isShowing())
        {
            alertDialog.show();
        }

        alertDialog.setCanceledOnTouchOutside(false);

        Window window = alertDialog.getWindow();

        if (window != null)
        {
            window.setContentView(R.layout.common_alert_dialog);
            TextView contentTv = (TextView)window.findViewById(R.id.dialog_content);
            contentTv.setText(content);

            Button okBtn = (Button)window.findViewById(R.id.dialog_btn);
            okBtn.setText(btn_content);
            okBtn.setOnClickListener(v ->
            {
                if (alertDialog != null)
                {
                    alertDialog.cancel();
                    alertDialog = null;
                }
            });

            View divider = window.findViewById(R.id.dialog_btn_divider);
            divider.setVisibility(View.GONE);

            Button negBtn = (Button)window.findViewById(R.id.dialog_negative_btn);
            negBtn.setVisibility(View.GONE);
        }
    }
}
