package com.android.core.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.android.core.sqlite.CustomSQLiteOpenHelper;

public class CustomProvider extends ContentProvider {

    private final static UriMatcher uriMatcher;

    private CustomSQLiteOpenHelper mSQLiteOpenHelper;

    private final static int DATA_LIST = 1;
    private final static int DATA_ID = 2;
    private final static int Cdn_SLB = 3;

    private final static int STBINFO = 21;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //对表stbconfig的uri
        uriMatcher.addURI("stbconfig_authority", "stbconfig" , DATA_LIST);
        uriMatcher.addURI("stbconfig_authority",
                "stbconfig" + "/cdn_slb", Cdn_SLB);
        uriMatcher.addURI("stbconfig_authority",
                "stbconfig" + "/#", DATA_ID);

        uriMatcher.addURI("stbconfig", "stbconfig/*", STBINFO);
    }

    @Override
    public boolean onCreate() {
        // 初始化资源
        mSQLiteOpenHelper = new CustomSQLiteOpenHelper(getContext(), null, null, 1);
        if (!mSQLiteOpenHelper.isDatabaseOpen()) {
            mSQLiteOpenHelper.openDatabase();
        }
        return true;
    }

    @Override
    public Cursor query( Uri uri,   String[] projection,   String selection,   String[] selectionArgs,   String sortOrder) {
        String tableName = getTableName(uri);
        Cursor c;
        String where = null;
        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = "name DESC";// name降序
        } else {
            orderBy = sortOrder;
        }
        switch (uriMatcher.match(uri)) {
            case DATA_LIST:
                c = mSQLiteOpenHelper.query(projection, selection, selectionArgs, orderBy, tableName);
                break;
            case Cdn_SLB:
                if (selection != null && !"".equals(selection)) {
                    where = selection;
                }
                if (where == null) {
                    where = "name=?";
                }
                if (projection == null || projection.length == 0) {
                    projection = new String[]{"name"};
                }
                if (selectionArgs == null || selectionArgs.length == 0) {
                    selectionArgs = new String[]{"cdn_slb"};
                }
                c = mSQLiteOpenHelper.query(projection, where, selectionArgs, orderBy, tableName);
                break;
            default:
                c = null;
                break;
        }
        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case DATA_LIST:
                return "vnd.android.cursor.dir/vnd.constants.parameter";
            case Cdn_SLB:
                return "vnd.android.cursor.item/vnd.constants.parameter";
            default:
                throw new RuntimeException("Unknown URI...");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = -1;
        String tableName = getTableName(uri);
        Uri localUri = null;
        int code = uriMatcher.match(uri);
        switch (code) {
            case Cdn_SLB:
                values.put("name", "cdn_slb");
                id = mSQLiteOpenHelper.inserData(values, tableName);
                if (id > 0) {
                    localUri = ContentUris.withAppendedId(uri, id);
                }
                break;
        }
        return localUri;
    }

    @Override
    public int delete(  Uri uri, String selection,String[] selectionArgs) {
        String tableName = getTableName(uri);
        int count;
        String where = null;
        switch (uriMatcher.match(uri)) {
            case DATA_LIST:
                count = mSQLiteOpenHelper.delete(selection, selectionArgs, tableName);
                break;
            case Cdn_SLB:
                if (selection != null && !"".equals(selection)) {
                    where = selection;
                }
                if (where == null) {
                    where = "name='cdn_slb'";
                }
                count = mSQLiteOpenHelper.delete(where, selectionArgs, tableName);
                break;
            case DATA_ID:
                long id = ContentUris.parseId(uri);
                where = "_id=" + id;
                if (selection != null && !"".equals(selection)) {
                    where = where + " and " + selection;
                }
                count = mSQLiteOpenHelper.delete(where, selectionArgs, tableName);
                break;
            default:
                count = 0;
                break;
        }
        return count;
    }

    @Override
    public int update(  Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String tableName = getTableName(uri);
        int count = 0;
        String where = null;
        switch (uriMatcher.match(uri)) {
            case Cdn_SLB:
                if (selection != null && !"".equals(selection)) {
                    where = selection;
                }
                if (where == null) {
                    where = "name='cdn_slb'";
                }
                count = mSQLiteOpenHelper.updateData(values, where, selectionArgs, tableName);
                break;
        }
        return count;
    }

    /**
     * 从uri中解析出要操作的表名
     */
    private String getTableName(Uri uri) {
        //从uri中得到要操作的表名
        java.util.List<String> list = uri.getPathSegments();
        String tableName = list.get(0);
        return tableName;
    }

}
