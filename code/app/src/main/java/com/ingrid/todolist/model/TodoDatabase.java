package com.ingrid.todolist.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TodoDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "todos";
    private static final int VERSION = 2;
    private static final String TODOS_TABLE = "TODOS";
    private static final String ID_COLUNM = "ID";
    private static final String TITLE_COLUNM = "TITLE";
    private static final String DESCRIPTION_COLUNM = "DESCRIPTION";
    private static final String MARKED_COLUMN = "MARKED";
    private static final String CREATE_TODOS = "CREATE TABLE " + TODOS_TABLE + " (" +
            ID_COLUNM + " INTEGER PRIMARY KEY, " +
            TITLE_COLUNM + " TEXT, " +
            DESCRIPTION_COLUNM + " TEXT, " +
            MARKED_COLUMN + " INTEGER" +
            ")";

    public TodoDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(
                "ALTER TABLE " +
                        TODOS_TABLE +
                        " ADD COLUMN " + MARKED_COLUMN + " INTEGER DEFAULT 0;"
        );
    }

    public void saveTodo(TodoItem todoItem) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE_COLUNM, todoItem.getTitle());
        values.put(DESCRIPTION_COLUNM, todoItem.getDescription());
        values.put(MARKED_COLUMN, todoItem.isMarked() ? 1 : 0);

        db.insert(TODOS_TABLE, null, values);
        db.close();
    }

    public List<TodoItem> allTodos() {
        ArrayList<TodoItem> result = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TODOS_TABLE, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Long id = cursor.getLong(cursor.getColumnIndex(ID_COLUNM));
                String title = cursor.getString(cursor.getColumnIndex(TITLE_COLUNM));
                String description = cursor.getString(cursor.getColumnIndex(DESCRIPTION_COLUNM));
                boolean marked = cursor.getInt(cursor.getColumnIndex(MARKED_COLUMN)) == 1;
                TodoItem todoItem = new TodoItem(id, title, description, marked);
                result.add(todoItem);
            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();

        return result;
    }

    public void editItem(TodoItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE_COLUNM, item.getTitle());
        values.put(DESCRIPTION_COLUNM, item.getDescription());

        db.update(TODOS_TABLE, values, "id = ?", new String[]{item.getId().toString()});
        db.close();
    }

    public void delete(List<Long> ids) {
        SQLiteDatabase db = getWritableDatabase();
        String questionMarks = TextUtils.join(",", Collections.nCopies(ids.size(), "?"));
        String whereClause = String.format("id IN (%s)", questionMarks);
        String[] whereArgs = idsToArgs(ids);

        db.delete(TODOS_TABLE, whereClause, whereArgs);
        db.close();
    }

    public void mark(List<Long> ids) {
        setMark(ids,1);
    }

    public void unmark(List<Long> ids) {
        setMark(ids,0);
    }

    private void setMark(List<Long> ids, int mark) {
        SQLiteDatabase db = getWritableDatabase();
        String questionMarks = TextUtils.join(",", Collections.nCopies(ids.size(), "?"));
        String whereClause = String.format("id IN (%s)", questionMarks);
        String[] whereArgs = idsToArgs(ids);

        ContentValues values = new ContentValues();
        values.put(MARKED_COLUMN, mark);

        db.update(TODOS_TABLE, values, whereClause, whereArgs);
        db.close();
    }

    private String[] idsToArgs(List<Long> ids) {
        return ids.stream()
                .map(id -> id.toString())
                .collect(Collectors.toList())
                .toArray(new String[ids.size()]);
    }
}
