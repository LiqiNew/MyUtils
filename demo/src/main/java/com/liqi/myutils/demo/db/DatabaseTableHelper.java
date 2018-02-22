package com.liqi.myutils.demo.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创建数据库表对象
 *
 * @author Liqi
 */
public class DatabaseTableHelper extends SQLiteOpenHelper implements OnDatabaseTableHelperListener{

    public DatabaseTableHelper(Context context) {

        // 调用父类构造方法创建数据库
        super(context, TABLE_DB, null, TEST_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 营销订单
        db.execSQL("CREATE TABLE " + TEST_NAME + " (id integer primary key , "+TEST_CONTENT_ONE+" text, "+TEST_CONTENT_TWO+" text, "+TEST_CONTENT_THREE+" text, "+TEST_CONTENT_FOUR+" text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 判断数据库是否存在,存在就删掉然后重新创建表
        db.execSQL("DROP TABLE IF EXISTS " + TEST_NAME);
        onCreate(db);
    }

}