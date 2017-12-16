package com.renhuikeji.wanlb.wanlibao.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */

public class DbUtil {

    private SearchHistorySQLite helper;
    private SQLiteDatabase db;


    public DbUtil(Context context) {
        //实例化数据库SQLiteOpenHelper子类对象
        helper = new SearchHistorySQLite(context);
    }

    /**
     * 获取数据库中所有数据
     */
    private List<String> queryData() {
        List<String> historyLabels = new ArrayList<>();
        Cursor query = helper.getReadableDatabase().query("records", null, null, null, null, null, null);
        while (query.moveToNext()) {
            historyLabels.add(query.getString(query.getColumnIndex("name")));
        }
       return historyLabels;
    }


    /**
     * 添加历史记录
     *
     * @param tempName
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        if (!hasData(tempName)) {
            db.execSQL("insert into records(name) values('" + tempName + "')");
            db.close();
        } else {
            changeHistoryPosi(tempName);
        }

    }

    /*清空数据*/
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }

    /*检查数据库中是否已经有该条记录*/
    private boolean hasData(String tempName) {
        //从Record这个表里找到name=tempName的id
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }


    private void changeHistoryPosi(String tempName) {
        //先删除在添加
        db = helper.getWritableDatabase();
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        if (cursor.moveToNext()) {
            db.delete("records", "name=?", new String[]{tempName});
            db.execSQL("insert into records(name) values('" + tempName + "')");
            db.close();
        }
        queryData();
    }


}
