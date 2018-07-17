package com.shyouhan.aac.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.shyouhan.aac.constant.AppConstant;
import com.shyouhan.aac.widget.LogUtils;
import com.shyouhan.aac.widget.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作类
 *
 * @author Administrator
 */
public class DBUtils {
    private MyDatabaseHelper dbHelper = null;
    private SQLiteDatabase db = null;
    public Context con;


    public DBUtils(Context context) {
        this.con = context;
    }

    public SQLiteDatabase getMyDataBase() {
        if (db == null) {
            dbHelper = new MyDatabaseHelper(con, AppConstant.dbName,
                    AppConstant.dbVer);
            db = dbHelper.getWritableDatabase();
        }
        return db;
    }

    public void closeDataBase() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    public boolean deleteDatabase() {
        dbHelper = new MyDatabaseHelper(con, AppConstant.dbName, AppConstant.dbVer);
        return dbHelper.deleteDataBase(con);
    }

    // 插入数据
    public void insert(String tablename, String[] clomname, String[] clomstr) {
        try {
            getMyDataBase();// 获得数据库对象
            Cursor cursor = db.rawQuery("select * from table_search_history where pickid = '" + clomstr[0] + "'",null);
            if (cursor.getCount() > 0){
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    String pickid = cursor.getString(cursor.getColumnIndex("pickid"));
                    if (pickid.equals(clomstr[0])) {
                        db.execSQL("delete from table_search_history where pickid= '" + clomstr[0] + "'");
                    }
                }
            }
            //插入数据
            ContentValues values = new ContentValues();
            values.put("pickid", clomstr[0]);
            cursor = db.rawQuery("select * from table_search_history", null);

            if (cursor.getCount() > 0){
                if (cursor.getCount() == 5) {
                    //先删除最先的一条
                    cursor.moveToNext();
                    if (cursor.isFirst()) {
                        db.execSQL("delete from table_search_history where pickid='" + cursor.getString(cursor.getColumnIndex("pickid")) + "'");
//					db.execSQL("delete from table_search_history limit 0,1 ",null);
                    }
//				db.execSQL("delete from table_search_history where name='"+cursor.getString(cursor.getColumnIndex("pickid"))+"'",null);

                }
            }

            db.insert("table_search_history", null, values);
//            ToastUtils.showShort("插入成功");
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShort("插入失败" + e.getMessage());
            LogUtils.a(
                    "DBUTIL.insert异常:" + tablename + e.getMessage());
        } finally {
            closeDataBase();
        }
    }

    /**
     * 删除数据记录
     * @param pickid
     */
    public void deleteOneRecord(String pickid) {
        getMyDataBase();
        db.execSQL("delete from table_search_history where pickid='" + pickid + "'");
        closeDataBase();
//        ToastUtils.showShort("删除数据成功");
    }

    /**
     * 获取查询历史列表
     *
     * @return
     */
    public List<String> getAllSearchList() {
        getMyDataBase();// 获得数据库对象
        ArrayList<String> datas = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from table_search_history", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            datas.add(cursor.getString(cursor.getColumnIndex("pickid")));
//			datas.add(cursor.getColumnName(cursor.getColumnIndex("pickid")));
        }
        cursor.close();
        closeDataBase();
        return datas;
    }

}