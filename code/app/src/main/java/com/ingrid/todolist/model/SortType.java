package com.ingrid.todolist.model;

import static com.ingrid.todolist.model.TodoDatabase.*;

public enum SortType {
    NONE(null),
    TITLE_ASC(TITLE_COLUNM),
    TITLE_DESC(TITLE_COLUNM + " DESC"),
    PRIORITY_HIGH(PRIORITY_COLUMN + " DESC"),
    PRIORITY_LOW(PRIORITY_COLUMN);

    String sortCommand;
    SortType(String sortCommand){
        this.sortCommand = sortCommand;
    }

    public String getSortCommand() {
        return sortCommand;
    }
}
