package com.liqi.utils.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.liqi.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 数据库信息表业务操作对象
 */
public final class BaseDBManagerOperation {
    private static BaseDBManagerOperation mBaseDBManagerOperation;
    private final String TAG = getClass().getName();
    private SQLiteOpenHelper mSQLiteOpenHelper;

    private BaseDBManagerOperation() {

    }

    private BaseDBManagerOperation(SQLiteOpenHelper sqliteOpenHelper) {
        mSQLiteOpenHelper = sqliteOpenHelper;
    }

    /**
     * 静态获取数据库操作方法
     *
     * @param sqliteOpenHelper B
     * @param <B>              继承SQLiteOpenHelper泛型
     *                         {@link android.database.sqlite.SQLiteOpenHelper}
     * @return BaseDBManagerOperation
     */
    public static <B extends SQLiteOpenHelper> BaseDBManagerOperation getBaseDBManagerOperation(@NonNull B sqliteOpenHelper) {
        return mBaseDBManagerOperation = null == mBaseDBManagerOperation ? new BaseDBManagerOperation(sqliteOpenHelper) : mBaseDBManagerOperation;
    }

    /**
     * 数据库数据增加(单个)
     *
     * @param table  表名
     * @param values
     * @return -1写入失败
     */
    public synchronized long save(String table, ContentValues values) {
        long saveSign;
        SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
        saveSign = db.insert(table, null, values);
        db.close();
        return saveSign;
    }

    /**
     * 数据库数据增加(批量)
     *
     * @param table      表名
     * @param valuesList 要增加key和value
     * @return -1写入失败
     */
    public synchronized long save(String table, ArrayList<ContentValues> valuesList) {
        long saveSign = -1;
        if (null != valuesList && !valuesList.isEmpty()) {
            SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
            for (int i = 0; i < valuesList.size(); i++) {
                ContentValues contentValues = valuesList.get(i);
                saveSign = db.insert(table, null, contentValues);
                if (saveSign < 0) {
                    Logger.e(TAG, "save批量添加：写入失败>>>>索引：" + i);
                }
            }
            db.close();
        } else {
            Logger.e(TAG, "save批量添加：增加数据集合无数据");
        }
        return saveSign;
    }

    /**
     * 根据指定的字段更新数据库（单个）
     *
     * @param table  表名
     * @param tag    指定字段
     * @param values 更新值
     * @param tagInt 指定字段的类型。只支持 int ,double ,String.
     */
    public synchronized void update(String table, String tag, ContentValues values,
                                    DataBaseTypeEnum tagInt) {
        String tagValues = "";
        switch (tagInt) {
            case INT:
                int idOne = values.getAsInteger(tag);
                tagValues = String.valueOf(idOne);
                break;
            case DOUBLE:
                double idTwo = values.getAsDouble(tag);
                tagValues = String.valueOf(idTwo);
                break;
            case STRING:
                tagValues = values.getAsString(tag);
                break;
        }
        if (!"".equals(tagValues)) {
            SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
            db.update(table, values, tag + "=?", new String[]{tagValues});
            db.close();
        } else {
            Logger.e(TAG, "数据库更新字段类型获取失败");
        }
    }

    /**
     * 根据指定的字段更新数据库（批量）
     *
     * @param table        表名
     * @param tag          指定字段
     * @param values       集合更新值
     * @param baseTypeEnum 指定字段的类型。只支持 int ，double ，String。
     */
    public synchronized void update(String table, String tag,
                                    ArrayList<ContentValues> values, DataBaseTypeEnum baseTypeEnum) {
        SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
        for (ContentValues contentValues : values) {
            String tagValues = "";
            switch (baseTypeEnum) {
                case INT:
                    int idOne = contentValues.getAsInteger(tag);
                    tagValues = String.valueOf(idOne);
                    break;
                case DOUBLE:
                    double idTwo = contentValues.getAsDouble(tag);
                    tagValues = String.valueOf(idTwo);
                    break;
                case STRING:
                    tagValues = contentValues.getAsString(tag);
                    break;
            }
            if (!"".equals(tagValues)) {
                db.update(table, contentValues, tag + "=?",
                        new String[]{tagValues});
            } else {
                Logger.e(TAG, "数据库更新字段类型获取失败");
            }

        }
        db.close();
    }

    /**
     * 数据库中无更新就添加数据
     *
     * @param table        表名
     * @param tag          指定写入字段键
     * @param values       指定写入字段键值对象
     * @param baseTypeEnum 指定更新字段的类型。
     */
    public synchronized void addOrUpdate(String table, String tag, ContentValues values,
                                         DataBaseTypeEnum baseTypeEnum) {
        String tagValues = "";
        switch (baseTypeEnum) {
            case INT:
                int tagInt = values.getAsInteger(tag);
                tagValues = String.valueOf(tagInt);
                break;
            case DOUBLE:
                double tagDouble = values.getAsDouble(tag);
                tagValues = String.valueOf(tagDouble);
                break;
            case STRING:
                tagValues = values.getAsString(tag);
                break;
            case LONG:
                long tagLong = values.getAsLong(tag);
                tagValues = String.valueOf(tagLong);
                break;
        }
        if (!"".equals(tagValues)) {
            SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
            if (!db.isOpen()) {
                mSQLiteOpenHelper.onOpen(db);
            }
            int update = db.update(table, values, tag + "=?", new String[]{tagValues});
            //如果数据中没有可更新的，就往数据里面添加
            if (update == 0) {
                db.insert(table, null, values);
            }
            db.close();
        } else {
            Logger.e(TAG, "更新或者添加数据：数据库更新字段类型获取失败");
        }
    }
    /**
     * 根据批量字段和值更新数据库（批量）
     *
     * @param table           表名
     * @param whereKeyValuess 指定字段key和值
     * @param values          要更新的值(集合)
     */
//    public void update(String table, Map<String, String> whereKeyValuess, ArrayList<ContentValues> values) {
//
//        if (null != whereKeyValuess && !whereKeyValuess.isEmpty() && null != values && !values.isEmpty()) {
//            SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
//            Map<String, Object> map = getWhereSq(whereKeyValuess);
//            for (ContentValues contentValues : values) {
//                db.update(table, contentValues, map.get("whereSq").toString(), (String[]) map.get("values"));
//            }
//            db.close();
//        } else {
//            Log.e("数据库更新", "数据库更新字段类型或者更新的值集合为空");
//        }
//    }

    /**
     * 根据批量字段和值更新数据库（单个）
     * <p>
     * <hint>
     * 单键为精准匹配更新。
     * 查询Map为多键时，sq语句拼接为....and 拼接条件语句
     * </hint>
     *
     * @param table          表名
     * @param whereKeyValues 指定字段key和值
     * @param value          要更新的值
     */
    public synchronized int updateAnd(String table, Map<String, String> whereKeyValues, ContentValues value) {

        if (null != whereKeyValues && !whereKeyValues.isEmpty() && null != value && value.size() > 0) {
            SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
            Map<String, Object> map = getWhereSq(whereKeyValues, DataBaseValuesEnum.QUERY_SYMBOL_NULL);
            int updateCode = db.update(table, value, map.get("whereSq").toString(), (String[]) map.get("values"));
            db.close();
            return updateCode;
        } else {
            Logger.e(TAG, "数据库更新字段类型或者更新的值为空");
        }
        return 0;
    }

    /**
     * 根据批量字段和值更新数据库（单个）
     * <p>
     * <hint>
     * 单键为精准匹配更新。
     * 查询Map为多键时，sq语句拼接为....or 拼接条件语句
     * </hint>
     *
     * @param table          表名
     * @param whereKeyValues 指定字段key和值
     * @param value          要更新的值
     */
    public synchronized int updateOr(String table, Map<String, String> whereKeyValues, ContentValues value) {

        if (null != whereKeyValues && !whereKeyValues.isEmpty() && null != value && value.size() > 0) {
            SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
            Map<String, Object> map = getWhereSq(whereKeyValues, DataBaseValuesEnum.QUERY_SYMBOL_NULL_OR);
            int updateCode = db.update(table, value, map.get("whereSq").toString(), (String[]) map.get("values"));
            db.close();
            return updateCode;
        } else {
            Logger.e(TAG, "数据库更新字段类型或者更新的值为空");
        }
        return 0;
    }

    /**
     * 拼接更新whereSQ语句
     *
     * @param whereKeyValuess
     * @return
     */
    private Map<String, Object> getWhereSq(Map<String, String> whereKeyValuess, DataBaseValuesEnum baseValuesEnum) {
        Map<String, Object> map = new HashMap<>();

        String whereSq = "";
        String[] values = new String[whereKeyValuess.size()];
        Set<Entry<String, String>> es = whereKeyValuess.entrySet();
        Iterator<Entry<String, String>> it = es.iterator();
        int tag = 0;
        while (it.hasNext()) {
            Entry<String, String> entry = it.next();
            String key = entry.getKey();
            String value = entry.getValue();
            if (tag == 0) {
                whereSq += key + " = ?";
            } else {
                whereSq += baseValuesEnum.getQueryJointSymbol() + key + " = ?";
            }
            values[tag] = baseValuesEnum.getQuerySymbol() + value + baseValuesEnum.getQuerySymbol();
            tag++;
        }
        map.put("whereSq", whereSq);
        map.put("values", values);
        return map;
    }

    /**
     * 根据数据库里面的数据数量来实现当前数据的是否增加和删除还是更新
     *
     * @param table       表名
     * @param keyToVlsInt 指定键（键对应的值必须为int or String）
     * @param valuesList  变动的key 和value值集合
     */
    public synchronized void updateAndInsertAndDele(String table, String keyToVlsInt,
                                                    ArrayList<ContentValues> valuesList) {
        SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
        int size = getDbSize(table, db);
        int listSize = valuesList.size();
        // 如果数据库存在的就根据ID更新,如果没有存在就增加数据
        for (int i = 0; i < listSize; i++) {
            ContentValues values = valuesList.get(i);
            if (i < size) {
                String keyToValues;
                try {
                    int id = values.getAsInteger(keyToVlsInt);
                    keyToValues = String.valueOf(id);
                } catch (Exception e) {
                    keyToValues = values.getAsString(keyToVlsInt);
                }
                db.update(table, values, keyToVlsInt + "=?",
                        new String[]{keyToValues});
            } else {
                db.insert(table, null, values);
            }
        }
        // 如果增加集合数据长度数量比数据库存在数据集合数量少，就把数据库里面多余的数据删掉
        while (listSize < size) {
            db.delete(table, keyToVlsInt + "=?",
                    new String[]{String.valueOf(listSize++)});
        }
        db.close();
    }

    /**
     * 获取数据库长度
     *
     * @return 数据库长度
     */
    public synchronized int getDbSize(String table, SQLiteDatabase db) {
        if (null == db) {
            db = mSQLiteOpenHelper.getWritableDatabase();
        }

        Cursor cursor = db.query(table, null, null, null, null, null, null);
        int size = cursor.getCount();
        cursor.close();
        return size;
    }

    /**
     * 全部更新数据库
     *
     * @param table  表名
     * @param values 更新内容
     */
    public synchronized void allUpdate(String table, ContentValues values) {
        SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
        db.update(table, values, null, null);
        db.close();
    }

    /**
     * 根据指定多键字段精准查找所有数据,返回集合
     * <hint>
     * 单键为精准匹配查询。
     * 查询Map为多键时，sq语句拼接为....and queryKeyMapKey like queryKeyMapValues
     * </hint>
     *
     * @param table                 表名
     * @param queryKeyMap           查询多键Map
     * @param fetchValuesAllKeyList 要取出值的所有键集合
     * @return 数据库查询值
     */
    public synchronized ArrayList<Map<String, String>> allPrecisionFindByIdList(String table,
                                                                                Map<String, String> queryKeyMap, ArrayList<String> fetchValuesAllKeyList) {
        return findById(table, queryKeyMap, fetchValuesAllKeyList, DataBaseValuesEnum.QUERY_SYMBOL_NULL);
    }

    /**
     * 根据指定多键字段精准查找所有数据,返回集合
     * <hint>
     * 单键为精准匹配查询。
     * 查询Map为多键时，sq语句拼接为....or queryKeyMapKey like queryKeyMapValues
     * </hint>
     *
     * @param table                 表名
     * @param queryKeyMap           查询多键Map
     * @param fetchValuesAllKeyList 要取出值的所有键集合
     * @return 数据库查询值
     */
    public synchronized ArrayList<Map<String, String>> orPrecisionFindByIdList(String table,
                                                                               Map<String, String> queryKeyMap, ArrayList<String> fetchValuesAllKeyList) {
        return findById(table, queryKeyMap, fetchValuesAllKeyList, DataBaseValuesEnum.QUERY_SYMBOL_NULL_OR);
    }

    /**
     * 根据指定多键字段模糊查找所有数据,返回集合
     * <hint>
     * 单键为模糊匹配查询。
     * 查询Map为多键时，sq语句拼接为....or queryKeyMapKey like %queryKeyMapValues%
     * </hint>
     *
     * @param table                 表名
     * @param queryKeyMap           查询多键集合
     * @param fetchValuesAllKeyList 要取出值的所有键集合
     * @return 数据库查询值
     */
    public synchronized ArrayList<Map<String, String>> allFuzzyFindByIdList(String table,
                                                                            Map<String, String> queryKeyMap, ArrayList<String> fetchValuesAllKeyList) {
        return findById(table, queryKeyMap, fetchValuesAllKeyList, DataBaseValuesEnum.QUERY_SYMBOL_FUZZY);
    }

    /**
     * 根据指定多键字段通过and拼接，模糊查找所有数据,返回集合
     * <hint>
     * 单键为模糊匹配查询。
     * 查询Map为多键时，sq语句拼接为....and queryKeyMapKey like %queryKeyMapValues%
     * </hint>
     *
     * @param table                 表名
     * @param queryKeyMap           查询多键Map
     * @param fetchValuesAllKeyList 要取出值的所有键集合
     * @return 数据库查询值
     */
    public synchronized ArrayList<Map<String, String>> andFuzzyFindByIdList(String table,
                                                                            Map<String, String> queryKeyMap, ArrayList<String> fetchValuesAllKeyList) {
        return findById(table, queryKeyMap, fetchValuesAllKeyList, DataBaseValuesEnum.QUERY_SYMBOL_FUZZY_NULL);
    }


    /**
     * 根据指定多键字段精准查找所有数据,返回Map
     * <hint>
     * 单键为精准匹配查询。
     * 查询Map为多键时，sq语句拼接为....and queryKeyMapKey like queryKeyMapValues
     * </hint>
     *
     * @param table                 表名
     * @param queryKeyMap           查询多键Map
     * @param fetchValuesAllKeyList 要取出值的所有键集合
     * @return 数据库查询值
     */
    public synchronized Map<String, String> allPrecisionFindByIdMap(String table,
                                                                    Map<String, String> queryKeyMap, ArrayList<String> fetchValuesAllKeyList) {
        return findByIdMap(table, queryKeyMap, fetchValuesAllKeyList, DataBaseValuesEnum.QUERY_SYMBOL_NULL);
    }

    /**
     * 根据指定多键字段精准查找所有数据,返回Map
     * <hint>
     * 单键为精准匹配查询。
     * 查询Map为多键时，sq语句拼接为....or queryKeyMapKey like queryKeyMapValues
     * </hint>
     *
     * @param table                 表名
     * @param queryKeyMap           查询多键Map
     * @param fetchValuesAllKeyList 要取出值的所有键集合
     * @return 数据库查询值
     */
    public synchronized Map<String, String> orPrecisionFindByIdMap(String table,
                                                                   Map<String, String> queryKeyMap, ArrayList<String> fetchValuesAllKeyList) {
        return findByIdMap(table, queryKeyMap, fetchValuesAllKeyList, DataBaseValuesEnum.QUERY_SYMBOL_NULL_OR);
    }

    /**
     * 根据指定多键字段模糊查找所有数据,返回Map
     * <hint>
     * 单键为模糊匹配查询。
     * 查询Map为多键时，sq语句拼接为....or queryKeyMapKey like %queryKeyMapValues%
     * </hint>
     *
     * @param table                 表名
     * @param queryKeyMap           查询多键集合
     * @param fetchValuesAllKeyList 要取出值的所有键集合
     * @return 数据库查询值
     */
    public synchronized Map<String, String> allFuzzyFindByIdMap(String table,
                                                                Map<String, String> queryKeyMap, ArrayList<String> fetchValuesAllKeyList) {
        return findByIdMap(table, queryKeyMap, fetchValuesAllKeyList, DataBaseValuesEnum.QUERY_SYMBOL_FUZZY);
    }

    /**
     * 根据指定多键字段通过and拼接，模糊查找所有数据,返回Map
     * <hint>
     * 单键为模糊匹配查询。
     * 查询Map为多键时，sq语句拼接为....and queryKeyMapKey like %queryKeyMapValues%
     * </hint>
     *
     * @param table                 表名
     * @param queryKeyMap           查询多键Map
     * @param fetchValuesAllKeyList 要取出值的所有键集合
     * @return 数据库查询值
     */
    public synchronized Map<String, String> andFuzzyFindByIdMap(String table,
                                                                Map<String, String> queryKeyMap, ArrayList<String> fetchValuesAllKeyList) {
        return findByIdMap(table, queryKeyMap, fetchValuesAllKeyList, DataBaseValuesEnum.QUERY_SYMBOL_FUZZY_NULL);
    }

    /**
     * 根据指定多键字段查找所有数据(返回集合)
     *
     * @param table                 表名
     * @param queryKeyMap           查询多键Map
     * @param fetchValuesAllKeyList 要取出值的所有键集合
     * @return 数据库查询值
     */
    private ArrayList<Map<String, String>> findById(String table,
                                                    Map<String, String> queryKeyMap, ArrayList<String> fetchValuesAllKeyList, DataBaseValuesEnum dataBaseValuesEnum) {
        ArrayList<Map<String, String>> list = null;
        if (fetchValuesAllKeyList != null && queryKeyMap != null && !queryKeyMap.isEmpty()) {
            list = new ArrayList<>();
            String sql = "select * from " + table + " where ";

            sql += jointQueryContent(queryKeyMap, dataBaseValuesEnum);
            Logger.e(TAG, "多键字段查找SQ语句：" + sql);
            // 获取数据库实例
            SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
            // 执行sql
            Cursor cursor = db.rawQuery(sql,// sql语句
                    null);
            // 逐行读取
            while (cursor.moveToNext()) {
                // 每条记录
                Map<String, String> hashtable = new Hashtable<>();
                for (String tagString : fetchValuesAllKeyList) {
                    int index = cursor
                            .getColumnIndex(tagString);
                    if (index >= 0) {
                        String tagVal = cursor.getString(index);
                        hashtable.put(tagString, tagVal == null ? "" : tagVal);
                    }
                }
                list.add(hashtable);
            }

            // 关闭游标
            cursor.close();
            // 关闭数据库实例
            db.close();
        } else {
            if (fetchValuesAllKeyList == null) {
                Logger.e(TAG, "多键字段查找：数据取出键集合为空");
            }
            if (queryKeyMap == null) {
                Logger.e(TAG, "多键字段查找：查询键集合为空");
            }
        }
        return list;
    }

    /**
     * 根据指定多键字段查找所有数据(精准查询)(返回单个对象)
     *
     * @param table                 表名
     * @param queryKeyMap           查询多键集合
     * @param fetchValuesAllKeyList 要取出值的所有键集合
     * @return 数据库查询值
     */
    private synchronized Map<String, String> findByIdMap(String table, Map<String, String> queryKeyMap, ArrayList<String> fetchValuesAllKeyList, DataBaseValuesEnum dataBaseValuesEnum) {
        Map<String, String> hashtable = new Hashtable<>();
        if (fetchValuesAllKeyList != null && queryKeyMap != null && !queryKeyMap.isEmpty()) {
            String sql = "select * from " + table + " where ";

            sql += jointQueryContent(queryKeyMap, dataBaseValuesEnum);
            Logger.e(TAG, "单键字段查找SQ语句：" + sql);
            // 获取数据库实例
            SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
            // 执行sql
            Cursor cursor = db.rawQuery(sql,// sql语句
                    null);
            // 逐行读取
            while (cursor.moveToNext()) {
                // 每条记录
                for (String tagString : fetchValuesAllKeyList) {
                    int index = cursor
                            .getColumnIndex(tagString);
                    if (index >= 0) {
                        String tagVal = cursor.getString(index);
                        hashtable.put(tagString, tagVal == null ? "" : tagVal);
                    }
                }
            }

            // 关闭游标
            cursor.close();
            // 关闭数据库实例
            db.close();
        } else {
            if (fetchValuesAllKeyList == null) {
                Logger.e(TAG, "多键字段查找：数据取出键集合为空");
            }
            if (queryKeyMap == null) {
                Logger.e(TAG, "多键字段查找：查询键集合为空");
            }
        }
        return hashtable;
    }

    /**
     * 根据Hashtable来拼接SQ请求语句(and是多个条件同时符合，or是只有一个条件符合就可以)
     *
     * @param hash
     * @return
     * @throws Exception
     */
    private String jointQueryContent(Map<String, String> hash, DataBaseValuesEnum dataBaseValuesEnum) {
        String kvString = "";
        Set<Entry<String, String>> es = hash.entrySet();
        Iterator<Entry<String, String>> it = es.iterator();
        int tag = 0;
        while (it.hasNext()) {
            Entry<String, String> entry = it.next();
            String key = entry.getKey();
            String value = entry.getValue();
            if (tag == 0) {
                kvString += key + " like '" + dataBaseValuesEnum.getQuerySymbol() + value + dataBaseValuesEnum.getQuerySymbol() + "'";
                tag = 1;
            } else {
                kvString += dataBaseValuesEnum.getQueryJointSymbol() + key + " like '" + dataBaseValuesEnum.getQuerySymbol() + value + dataBaseValuesEnum.getQuerySymbol() + "'";
            }
        }
        return kvString;
    }

    /**
     * 查询当前表的所有值
     *
     * @param table   表名
     * @param tagList 要取出值的所有键集合
     * @return 数据库查询值
     */
    public ArrayList<Map<String, String>> findByAll(String table,
                                                    ArrayList<String> tagList) {
        ArrayList<Map<String, String>> list = null;
        if (tagList != null) {
            list = new ArrayList<>();
            String sql = "select * from " + table + ";";

            System.out.println(sql);
            // 获取数据库实例
            SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
            // 执行sql
            Cursor cursor = db.rawQuery(sql,// sql语句
                    new String[]{});
            // 逐行读取
            while (cursor.moveToNext()) {
                // 每条记录
                Map<String, String> hashTable = new Hashtable<>();
                for (String tagString : tagList) {
                    int index = cursor
                            .getColumnIndex(tagString);
                    if (index >= 0) {
                        String tagVal = cursor.getString(index);
                        hashTable.put(tagString, tagVal == null ? "" : tagVal);
                    }
                }
                list.add(hashTable);
            }
            // 关闭游标
            cursor.close();
            // 关闭数据库实例
            db.close();
        } else {
            Logger.e(TAG, "查询当前表的所有值：取出键集合为空");
        }
        return list;
    }

    /**
     * (根据指定字段降序)查询指定分页
     *
     * @param pageIndex 页数。从一开始
     * @param max       一页返回多少条数据
     * @param table     表名
     * @param tag       指定字段
     * @param tagList   要取出值的所有键集合
     * @return 数据库查询值
     */
    public ArrayList<Map<String, String>> findByPageIndex(int pageIndex,
                                                          int max, String table, String tag, ArrayList<String> tagList) {
        // 0-19 20 -39 40-59 max*(pageIndex-1) max*pageIndex -1;
        ArrayList<Map<String, String>> list = null;
        if (tagList != null) {
            if (pageIndex != 0) {
                int start = max * (pageIndex - 1);
                String sql = "select * from " + table + " order by " + tag
                        + " desc" + "  limit  " + start + "," + max + ";";
                list = new ArrayList<>();
                // 获取数据库实例
                SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
                // 执行sql
                Cursor cursor = db.rawQuery(sql,// sql语句
                        new String[]{});
                // 逐行读取
                while (cursor.moveToNext()) {
                    Map<String, String> hashtable = new HashMap<>();
                    for (String tagString : tagList) {
                        int index = cursor
                                .getColumnIndex(tagString);
                        if (index >= 0) {
                            String tagVal = cursor.getString(index);
                            hashtable.put(tagString, tagVal == null ? "" : tagVal);
                        }
                    }
                    list.add(hashtable);
                }
                cursor.close();
                // 关闭数据库实例
                db.close();
            } else {
                Logger.e(TAG, "分页查询：pageIndex值为0");
            }
        } else {
            Logger.e(TAG, "分页查询：取出键集合为空");
        }
        return list;
    }

    /**
     * 根据指定多个字段去删除
     * <hint>
     * 单键为精准匹配删除。
     * 查询Map为多键时，sq语句拼接为....and 拼接条件语句
     * </hint>
     *
     * @param table          表名
     * @param whereKeyValues 指定多个字段键和值
     * @return 删除数据受影响行数，>0删除成功。
     */
    public synchronized int deleteAnd(String table, Map<String, String> whereKeyValues) {
        int num = 0;
        if (null != whereKeyValues && !whereKeyValues.isEmpty()) {
            SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
            Map<String, Object> map = getWhereSq(whereKeyValues, DataBaseValuesEnum.QUERY_SYMBOL_NULL);
            num = db.delete(table, map.get("whereSq").toString(),
                    (String[]) map.get("values"));
            db.close();
        } else {
            Logger.e(TAG, "数据库删除多个字段键和值为空");
        }
        return num;
    }

    /**
     * 根据指定多个字段去删除
     * <hint>
     * 单键为精准匹配删除。
     * 查询Map为多键时，sq语句拼接为....or 拼接条件语句
     * </hint>
     *
     * @param table          表名
     * @param whereKeyValues 指定多个字段键和值
     * @return 删除数据受影响行数，>0删除成功。
     */
    public synchronized int deleteOr(String table, Map<String, String> whereKeyValues) {
        int num = 0;
        if (null != whereKeyValues && !whereKeyValues.isEmpty()) {
            SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
            Map<String, Object> map = getWhereSq(whereKeyValues, DataBaseValuesEnum.QUERY_SYMBOL_NULL_OR);
            num = db.delete(table, map.get("whereSq").toString(),
                    (String[]) map.get("values"));
            db.close();
        } else {
            Logger.e(TAG, "数据库删除多个字段键和值为空");
        }
        return num;
    }

    /**
     * 根据指定字段去删除
     *
     * @param table   表名
     * @param idKey   指定字段键
     * @param idValue 指定字段值
     * @return 删除数据受影响行数，>0删除成功。
     */
    public synchronized int delete(String table, String idKey, Object idValue) {
        SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();

        int num = db.delete(table, idKey + "=?",
                new String[]{String.valueOf(idValue)});
        db.close();
        return num;
    }

    /**
     * 全部 删除
     *
     * @return 删除数据受影响行数，>0删除成功。
     */
    public synchronized int delete(String table) {
        SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
        int delete = db.delete(table, null, null);
        db.close();
        return delete;
    }
}
