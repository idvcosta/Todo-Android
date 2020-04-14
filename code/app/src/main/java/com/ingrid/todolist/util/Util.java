package com.ingrid.todolist.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class Util {
    public static boolean isValid(String string) {
        return string != null && !string.isEmpty();
    }

    public static void showKeyboard(Context context, EditText target) {
        target.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
