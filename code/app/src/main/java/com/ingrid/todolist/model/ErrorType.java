package com.ingrid.todolist.model;

import com.ingrid.todolist.R;

public enum ErrorType {
    EMPTY_DESCRIPTION(R.string.error_empty_description),
    EMPTY_TITLE(R.string.error_empty_title),
    LARGE_TITLE(R.string.error_large_title),
    SMALL_TITLE(R.string.error_small_title);

    private int resId;

    ErrorType(int resId) {
        this.resId = resId;
    }

    public int getResId() {
        return resId;
    }
}
