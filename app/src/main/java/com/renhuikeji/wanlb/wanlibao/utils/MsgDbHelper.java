package com.renhuikeji.wanlb.wanlibao.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.renhuikeji.wanlb.wanlibao.bean.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */

public class MsgDbHelper extends SQLiteOpenHelper {

    private static Integer version = 2;
    private SQLiteDatabase db;

    public MsgDbHelper(Context context) {
        super(context, Constant.DB_MSGS, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //打开数据库，建立了一个叫messages的表，信息内容和是否已读(0未读)：
        db.execSQL("create table messages(id integer primary key autoincrement,content varchar(200),isread integer,jumptype varchar(200),receid varchar(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion < oldVersion){
            onCreate(db);
        }
    }
//添加数据
    public int insert(String content, Integer isread,String jumptype,String receid) {
        ContentValues cv=new ContentValues();
        cv.put("content", content);
        cv.put("isread", isread);
        cv.put("jumptype", jumptype);
        cv.put("receid",receid);
        long index=getWritableDatabase().insert(Constant.DB_MSGS, null, cv);
        return (int) index;
    }
//    查询未读信息
    public List<Message> queryUnReadMsg()
    {
        db=getReadableDatabase();
        List<Message> msgs=new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from "+Constant.DB_MSGS+" where isread=0",null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0); //获取第一列的值,第一列的索引从0开始
            String content = cursor.getString(1);//获取第二列的值
            int isread = cursor.getInt(2);//获取第三列的值
            String jumpType=cursor.getString(3);
            String rece_id=cursor.getString(4);
            Message msg=new Message(id,content,isread,jumpType,rece_id);
            msgs.add(msg);
        }
        cursor.close();
        db.close();
        return msgs;
    }
//    更新状态
    public int updateMsg(int id,int isread){
        db=getReadableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put("isread", isread);
        int updateResult = db.update(Constant.DB_MSGS, contentValues, "id=?", new String[]{id+""});
        db.close();
        return updateResult;
    }
//    删除已读的数据
    public void deteMsg(int id){
        db=getWritableDatabase();
        int i=db.delete(Constant.DB_MSGS, "id=?", new String[]{id+""});
    }
//    删除客服所有的数据
    public void deteMsgByKefu(){
        db=getWritableDatabase();
        int i=db.delete(Constant.DB_MSGS, "jumptype=?", new String[]{Constant.JUMP_SESSION});
    }
//    删除数据by receid
    public  void deleteByRece(String receid){
        db=getWritableDatabase();
        db.delete(Constant.DB_MSGS,"receid=?",new String[]{receid});
    }
}
