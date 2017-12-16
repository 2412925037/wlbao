package com.renhuikeji.wanlb.wanlibao.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/5/6.
 */

public class SearchHistorySQLite extends SQLiteOpenHelper {
    private static String name = "history.db";
    private static Integer version = 1;

    public SearchHistorySQLite(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //打开数据库，建立了一个叫records的表，里面只有一列name来存储历史记录：
        db.execSQL("create table records(id integer primary key autoincrement,name varchar(200))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion < oldVersion){
            onCreate(db);
        }
    }
}
