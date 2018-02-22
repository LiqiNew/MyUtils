package com.liqi.myutils.demo.db;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库操作演示界面辅助对象
 * Created by LiQi on 2017/12/6.
 */

public class TestDataBaseOperateP<V extends ArrayList<Map<String, String>>> implements OnDatabaseTableHelperListener {
    private OnTestDataBaseOperateListener<V> mBaseOperateListener;

    public TestDataBaseOperateP(OnTestDataBaseOperateListener<V> baseOperateListener) {
        mBaseOperateListener = baseOperateListener;
    }

    /**
     * 增加-->详细请看save()注释
     */
    public void write(String contentOne, String contentTwo, String contentThree, String contentFour) {
        ContentValues values = new ContentValues();
        values.put(TEST_CONTENT_ONE, contentOne);
        values.put(TEST_CONTENT_TWO, contentTwo);
        values.put(TEST_CONTENT_THREE, contentThree);
        values.put(TEST_CONTENT_FOUR, contentFour);

        long saveCode = DBManagerOperation.BaseDBManagerOperation(mBaseOperateListener.getContext()).save(TEST_NAME, values);
        ArrayList<Map<String, String>> listValues = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("isOkCode", saveCode != 0 ? "数据库增加成功" : "数据库增加失败");
        listValues.add(map);
        mBaseOperateListener.presenterDataOk((V) listValues);
    }

    /**
     * 查询-->详细请看allPrecisionFindByIdList()注释
     */
    public void allPrecisionFindByIdList(String contentOne, String contentTwo, String contentThree, String contentFour) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(TEST_CONTENT_ONE);
        strings.add(TEST_CONTENT_TWO);
        strings.add(TEST_CONTENT_THREE);
        strings.add(TEST_CONTENT_FOUR);
        mBaseOperateListener.presenterDataOk((V) DBManagerOperation.BaseDBManagerOperation(mBaseOperateListener.getContext()).allPrecisionFindByIdList(TEST_NAME, queryMap(contentOne, contentTwo, contentThree, contentFour), strings));

    }

    /**
     * 查询-->详细请看orPrecisionFindByIdList()注释
     */
    public void orPrecisionFindByIdList(String contentOne, String contentTwo, String contentThree, String contentFour) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(TEST_CONTENT_ONE);
        strings.add(TEST_CONTENT_TWO);
        strings.add(TEST_CONTENT_THREE);
        strings.add(TEST_CONTENT_FOUR);
        mBaseOperateListener.presenterDataOk((V) DBManagerOperation.BaseDBManagerOperation(mBaseOperateListener.getContext()).orPrecisionFindByIdList(TEST_NAME, queryMap(contentOne, contentTwo, contentThree, contentFour), strings));

    }

    /**
     * 查询-->详细请看allFuzzyFindByIdList()注释
     */
    public void allFuzzyFindByIdList(String contentOne, String contentTwo, String contentThree, String contentFour) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(TEST_CONTENT_ONE);
        strings.add(TEST_CONTENT_TWO);
        strings.add(TEST_CONTENT_THREE);
        strings.add(TEST_CONTENT_FOUR);
        mBaseOperateListener.presenterDataOk((V) DBManagerOperation.BaseDBManagerOperation(mBaseOperateListener.getContext()).allFuzzyFindByIdList(TEST_NAME, queryMap(contentOne, contentTwo, contentThree, contentFour), strings));

    }

    /**
     * 查询-->详细请看andFuzzyFindByIdList()注释
     */
    public void andFuzzyFindByIdList(String contentOne, String contentTwo, String contentThree, String contentFour) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(TEST_CONTENT_ONE);
        strings.add(TEST_CONTENT_TWO);
        strings.add(TEST_CONTENT_THREE);
        strings.add(TEST_CONTENT_FOUR);
        mBaseOperateListener.presenterDataOk((V) DBManagerOperation.BaseDBManagerOperation(mBaseOperateListener.getContext()).andFuzzyFindByIdList(TEST_NAME, queryMap(contentOne, contentTwo, contentThree, contentFour), strings));

    }

    /**
     * 查询-->详细请看allPrecisionFindByIdMapt()注释
     */
    public void allPrecisionFindByIdMap(String contentOne, String contentTwo, String contentThree, String contentFour) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(TEST_CONTENT_ONE);
        strings.add(TEST_CONTENT_TWO);
        strings.add(TEST_CONTENT_THREE);
        strings.add(TEST_CONTENT_FOUR);
        ArrayList<Map<String, String>> listValues = new ArrayList<>();
        listValues.add(DBManagerOperation.BaseDBManagerOperation(mBaseOperateListener.getContext()).allPrecisionFindByIdMap(TEST_NAME, queryMap(contentOne, contentTwo, contentThree, contentFour), strings));
        mBaseOperateListener.presenterDataOk((V) listValues);
    }

    /**
     * 查询-->详细请看orPrecisionFindByIdMap()注释
     */
    public void orPrecisionFindByIdMap(String contentOne, String contentTwo, String contentThree, String contentFour) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(TEST_CONTENT_ONE);
        strings.add(TEST_CONTENT_TWO);
        strings.add(TEST_CONTENT_THREE);
        strings.add(TEST_CONTENT_FOUR);
        ArrayList<Map<String, String>> listValues = new ArrayList<>();
        listValues.add(DBManagerOperation.BaseDBManagerOperation(mBaseOperateListener.getContext()).orPrecisionFindByIdMap(TEST_NAME, queryMap(contentOne, contentTwo, contentThree, contentFour), strings));
        mBaseOperateListener.presenterDataOk((V) listValues);
    }

    /**
     * 查询-->详细请看allFuzzyFindByIdMap()注释
     */
    public void allFuzzyFindByIdMap(String contentOne, String contentTwo, String contentThree, String contentFour) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(TEST_CONTENT_ONE);
        strings.add(TEST_CONTENT_TWO);
        strings.add(TEST_CONTENT_THREE);
        strings.add(TEST_CONTENT_FOUR);
        ArrayList<Map<String, String>> listValues = new ArrayList<>();
        listValues.add(DBManagerOperation.BaseDBManagerOperation(mBaseOperateListener.getContext()).allFuzzyFindByIdMap(TEST_NAME, queryMap(contentOne, contentTwo, contentThree, contentFour), strings));
        mBaseOperateListener.presenterDataOk((V) listValues);
    }

    /**
     * 查询-->详细请看andFuzzyFindByIdMap()注释
     */
    public void andFuzzyFindByIdMap(String contentOne, String contentTwo, String contentThree, String contentFour) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(TEST_CONTENT_ONE);
        strings.add(TEST_CONTENT_TWO);
        strings.add(TEST_CONTENT_THREE);
        strings.add(TEST_CONTENT_FOUR);
        ArrayList<Map<String, String>> listValues = new ArrayList<>();
        listValues.add(DBManagerOperation.BaseDBManagerOperation(mBaseOperateListener.getContext()).andFuzzyFindByIdMap(TEST_NAME, queryMap(contentOne, contentTwo, contentThree, contentFour), strings));
        mBaseOperateListener.presenterDataOk((V) listValues);
    }

    /**
     * 更新--> 详细请看updateAnd()-updateOr()注释
     */
    public void update(String contentOne, String contentTwo, String contentThree, String contentFour) {
        Map<String, String> stringMap = new HashMap<>();
        if (!TextUtils.isEmpty(contentOne))
            stringMap.put(TEST_CONTENT_ONE, contentOne);
        if (!TextUtils.isEmpty(contentTwo))
            stringMap.put(TEST_CONTENT_THREE, contentThree);

        ContentValues values = new ContentValues();
        values.put(TEST_CONTENT_ONE, contentOne);
        values.put(TEST_CONTENT_TWO, contentTwo);
        values.put(TEST_CONTENT_THREE, contentThree);
        values.put(TEST_CONTENT_FOUR, contentFour);

        int update = DBManagerOperation.BaseDBManagerOperation(mBaseOperateListener.getContext()).updateAnd(TEST_NAME, stringMap, values);
        ArrayList<Map<String, String>> listValues = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("isOkCode", update > 0 ? "数据库更新成功" : "数据库更新失败");
        listValues.add(map);
        mBaseOperateListener.presenterDataOk((V) listValues);
    }

    /**
     * 删除-->详细请看deleteAnd()和deleteOr()注释
     */
    public void delete(String contentOne, String contentTwo, String contentThree, String contentFour) {
        int deleteCode = DBManagerOperation.BaseDBManagerOperation(mBaseOperateListener.getContext()).deleteAnd(TEST_NAME, queryMap(contentOne, contentTwo, contentThree, contentFour));
        ArrayList<Map<String, String>> listValues = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("isOkCode", deleteCode != 0 ? "数据库删除成功" : "数据库删除失败");
        listValues.add(map);
        mBaseOperateListener.presenterDataOk((V) listValues);
    }

    private Map<String, String> queryMap(String contentOne, String contentTwo, String contentThree, String contentFour) {
        Map<String, String> stringMap = new HashMap<>();
        if (!TextUtils.isEmpty(contentOne))
            stringMap.put(TEST_CONTENT_ONE, contentOne);
        if (!TextUtils.isEmpty(contentTwo))
            stringMap.put(TEST_CONTENT_TWO, contentTwo);
        if (!TextUtils.isEmpty(contentThree))
            stringMap.put(TEST_CONTENT_THREE, contentThree);
        if (!TextUtils.isEmpty(contentFour))
            stringMap.put(TEST_CONTENT_FOUR, contentFour);
        return stringMap;
    }

    public interface OnTestDataBaseOperateListener<V> {
        void presenterDataOk(V v);

        Context getContext();

        void presenterDataNo(int tag);
    }
}
