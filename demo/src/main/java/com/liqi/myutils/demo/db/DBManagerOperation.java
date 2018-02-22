package com.liqi.myutils.demo.db;

import android.content.Context;

import com.liqi.utils.db.BaseDBManagerOperation;

/**
 * 数据库信息表业务操作对象
 */
public class DBManagerOperation {
    public static BaseDBManagerOperation BaseDBManagerOperation(Context context) {
        return BaseDBManagerOperation.getBaseDBManagerOperation(new DatabaseTableHelper(context.getApplicationContext()));
    }
}
