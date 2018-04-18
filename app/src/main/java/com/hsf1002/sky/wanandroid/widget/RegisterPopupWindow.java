package com.hsf1002.sky.wanandroid.widget;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.hsf1002.sky.wanandroid.R;

/**
 * Created by hefeng on 18-4-17.
 */

public class RegisterPopupWindow extends PopupWindow {
    public EditText usernameEdit;
    public EditText passwordEdit;
    public EditText repasswordEdit;

    public RegisterPopupWindow(Activity context, View.OnClickListener listener) {
        super(context);

        View rootView = LayoutInflater.from(context).inflate(R.layout.popup_window_register, null);

        usernameEdit = rootView.findViewById(R.id.register_account_edit);
        passwordEdit = rootView.findViewById(R.id.register_password_edit);
        repasswordEdit = rootView.findViewById(R.id.register_re_password_edit);

        Button registerBtn = rootView.findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(listener);

        setOutsideTouchable(true);
        setContentView(rootView);

        //Window dialogWindow = context.getWindow();
        WindowManager wm = context.getWindowManager();
        //WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();

        Display display = wm.getDefaultDisplay();

        setWidth((int)(display.getWidth() * 0.75));
        setHeight((int)(display.getHeight() * 0.5));

        setFocusable(true);
    }
}
