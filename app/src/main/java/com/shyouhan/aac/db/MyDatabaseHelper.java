package com.shyouhan.aac.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shyouhan.aac.constant.AppConstant;

/**
 * 数据库建表 time 20120426
 *
 * @author chengyuan
 *
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {


//	SharedPrefs sp;

	public MyDatabaseHelper(Context context, String name, int version) {
		super(context, name, null, version);
//		sp = new SharedPrefs(context);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		System.out.println("onCreate()");

		arg0.execSQL("create table if not exists table_search_history("
				+ "Id varchar(10) primary key, " + // 主键
				"pickid varchar(30)" + // 运单编号
				")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO 修改数据库
		arg0.execSQL("drop table searchHistory");

		// 用户信息
		arg0.execSQL("create table if not exists table_search_history("
				+ "Id varchar(10) primary key, " + // 主键
				"pickid varchar(30)" + // 运单编号
				")");
		System.out.println("修改数据库onUpdate()");
	}

	public boolean isColumnExists(SQLiteDatabase db, String tabName, String col) {
		boolean result = true;
		Cursor cursor = null;
		try {
			cursor = db.rawQuery("select * from " + tabName, null);
			if (cursor.getColumnIndex(col) == -1) {
				result = false;
			}
		} catch (Exception e) {
			result = false;
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return result;
	}

	public boolean IsTableExists(SQLiteDatabase db, String tabName) {
		boolean result = false;
		if (tabName == null) {
			return false;
		}
		Cursor cursor = null;
		try {
			String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"
					+ tabName.trim() + "' ";
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = true;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return result;
	}

	// 删除数据库
	public boolean deleteDataBase(Context context) {
		return context.deleteDatabase(AppConstant.dbName);
	}

	public boolean deleteFriendDataBase(Context friendContext,
										String databaseName) {
		return friendContext.deleteDatabase(databaseName);
	}
}