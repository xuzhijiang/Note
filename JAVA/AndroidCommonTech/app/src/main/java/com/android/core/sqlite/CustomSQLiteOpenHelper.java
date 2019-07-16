package com.android.core.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CustomSQLiteOpenHelper extends SQLiteOpenHelper {

    private static SQLiteDatabase mInstance;

    private static final String DATABASE_NAME = "ott.db";

    public CustomSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String renameSql = "";

        db.execSQL(renameSql);
    }

    public boolean isDatabaseOpen() {
        if (mInstance != null) {
            return mInstance.isOpen();
        }
        return false;
    }

    /**
     * 打开数据库
     */
    public void openDatabase() {
        if (mInstance != null && !mInstance.isOpen()) {
            mInstance = getWritableDatabase();
        }
    }

    /**
     * 关闭数据库
     */
    public void closeDatabase() {
        if (mInstance != null && mInstance.isOpen()) {
            mInstance.close();
        }
    }

    /**
     * 插入记录
     */
    public long inserData(ContentValues values, String tableName) {
        long id = -1;
        if (mInstance != null) {
            try {
                mInstance.beginTransaction();
                id = mInstance.insert(tableName, "COL_NAME", values);
                mInstance.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mInstance.endTransaction();
            }
        }
        return id;
    }

    /**
     * 根据条件删除记录
     */
    public int delete(String whereClause, String[] whereArgs, String tableName) {
        int count = -1;
        if (mInstance != null) {
            try {
                mInstance.beginTransaction();
                count = mInstance.delete(tableName, whereClause, whereArgs);
                mInstance.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mInstance.endTransaction();
            }
        }
        return count;
    }

    /**
     * 更新记录
     */
    public int updateData(ContentValues values, String whereClause,
                          String[] whereArgs, String tableName) {
        int count = -1;
        if (mInstance != null) {
            try {
                mInstance.beginTransaction();
                count = mInstance.update(tableName, values, whereClause, whereArgs);
                mInstance.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mInstance.endTransaction();
            }
        }
        return count;
    }

    /**
     * 根据条件查询记录
     */
    public Cursor query(String[] columns, String selection,
                        String[] selectionArgs, String sortOrder, String tableName) {
        Cursor c = null;
        if (mInstance != null) {
            try {
                mInstance.beginTransaction();
                c = mInstance.query(tableName, columns, selection, selectionArgs,
                        null, null, sortOrder);
                mInstance.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mInstance.endTransaction();
            }
        }
        return c;
    }

    /**
     * 查询数据库所有记录
     */
    public Cursor queryAll(String tableName) {
        Cursor c = null;
        if (mInstance != null) {
            try {
                mInstance.beginTransaction();
                c = mInstance.query(tableName, null, null, null, null, null, null);
                mInstance.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mInstance.endTransaction();
            }
        }
        return c;
    }

}
