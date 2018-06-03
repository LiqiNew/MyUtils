package com.liqi.myutils.demo.db;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.liqi.Logger;
import com.liqi.myutils.demo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作演示界面
 * <p>
 * MVP模式
 * </P>
 * Created by LiQi on 2017/12/6.
 */

public class TestDataBaseOperateActivity extends AppCompatActivity implements View.OnClickListener, TestDataBaseOperateP.OnTestDataBaseOperateListener<ArrayList<Map<String, String>>> {
    private TextView content;
    private EditText add_key_one, add_key_two, add_key_three, add_key_four,
            query_key_one, query_key_two, query_key_three, query_key_four,
            update_key_one, update_key_two, update_key_three, update_key_four,
            delete_key_one, delete_key_two, delete_key_three, delete_key_four;
    private int queryCode;

    private Button query_key_query;
    private TestDataBaseOperateP<ArrayList<Map<String, String>>> mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_data_base_operate_layout);
        content = (TextView) findViewById(R.id.content);
        add_key_one = (EditText) findViewById(R.id.add_key_one);
        add_key_two = (EditText) findViewById(R.id.add_key_two);
        add_key_three = (EditText) findViewById(R.id.add_key_three);
        add_key_four = (EditText) findViewById(R.id.add_key_four);
        query_key_one = (EditText) findViewById(R.id.query_key_one);
        query_key_two = (EditText) findViewById(R.id.query_key_two);
        query_key_three = (EditText) findViewById(R.id.query_key_three);
        query_key_four = (EditText) findViewById(R.id.query_key_four);
        update_key_one = (EditText) findViewById(R.id.update_key_one);
        update_key_two = (EditText) findViewById(R.id.update_key_two);
        update_key_three = (EditText) findViewById(R.id.update_key_three);
        update_key_four = (EditText) findViewById(R.id.update_key_four);
        delete_key_one = (EditText) findViewById(R.id.delete_key_one);
        delete_key_two = (EditText) findViewById(R.id.delete_key_two);
        delete_key_three = (EditText) findViewById(R.id.delete_key_three);
        delete_key_four = (EditText) findViewById(R.id.delete_key_four);
        findViewById(R.id.add_key_add).setOnClickListener(this);
        query_key_query = (Button) findViewById(R.id.query_key_query);
        query_key_query.setOnClickListener(this);
        findViewById(R.id.update_key_update).setOnClickListener(this);
        findViewById(R.id.delete_key_delete).setOnClickListener(this);
        mPresenter = new TestDataBaseOperateP<>(this);
    }

    @Override
    public void presenterDataOk(ArrayList<Map<String, String>> presenterData) {
        String hint = "数据库操作执行失败";
        if (null != presenterData && !presenterData.isEmpty()) {
            hint = "";
            for (Map<String, String> map : presenterData) {
                if (!map.isEmpty()) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        hint += "key：" + entry.getKey() + "　values：" + entry.getValue() + "\n";
                    }
                }
            }
        }
        content.setText("操作数据库提示信息：\n" + hint);
    }

    @Override
    public void presenterDataNo(int tag) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //增加
            case R.id.add_key_add:
                String one = add_key_one.getText().toString().trim();
                String two = add_key_two.getText().toString().trim();
                String three = add_key_three.getText().toString().trim();
                String four = add_key_four.getText().toString().trim();
                mPresenter.write(one, two, three, four);
                break;
            //查询-轮流调用数据库查询工具方法
            case R.id.query_key_query:

                String queryOne = query_key_one.getText().toString().trim();
                String queryTwo = query_key_two.getText().toString().trim();
                String queryThree = query_key_three.getText().toString().trim();
                String queryFour = query_key_four.getText().toString().trim();
                queryCode++;
                switch (queryCode) {
                    case 1:
                        mPresenter.allPrecisionFindByIdList(queryOne, queryTwo, queryThree, queryFour);
                        break;
                    case 2:
                        mPresenter.orPrecisionFindByIdList(queryOne, queryTwo, queryThree, queryFour);
                        break;
                    case 3:
                        mPresenter.allFuzzyFindByIdList(queryOne, queryTwo, queryThree, queryFour);
                        break;
                    case 4:
                        mPresenter.andFuzzyFindByIdList(queryOne, queryTwo, queryThree, queryFour);
                        break;
                    case 5:
                        mPresenter.allPrecisionFindByIdMap(queryOne, queryTwo, queryThree, queryFour);
                        break;
                    case 6:
                        mPresenter.orPrecisionFindByIdMap(queryOne, queryTwo, queryThree, queryFour);
                        break;
                    case 7:
                        mPresenter.allFuzzyFindByIdMap(queryOne, queryTwo, queryThree, queryFour);
                        break;
                    case 8:
                        mPresenter.andFuzzyFindByIdMap(queryOne, queryTwo, queryThree, queryFour);
                        queryCode = 0;
                        break;
                }
                query_key_query.setText("查询数据库>>" + (queryCode + 1));
                
                List<Map<String, String>> mapList = mPresenter.allFindByIdList();
                if (null != mapList && !mapList.isEmpty()) {
                    for (Map<String, String> map : mapList
                            ) {
                        Logger.e("TestDataBase>>>全部查询", "one值:" + map.get(OnDatabaseTableHelperListener.TEST_CONTENT_ONE));
                    }
                }
                break;
            //更新
            case R.id.update_key_update:
                String updateOne = update_key_one.getText().toString().trim();
                String updateTwo = update_key_two.getText().toString().trim();
                String updateThree = update_key_three.getText().toString().trim();
                String updateFour = update_key_four.getText().toString().trim();
                mPresenter.update(updateOne, updateTwo, updateThree, updateFour);
                break;
            //删除
            case R.id.delete_key_delete:
                String deleteOne = delete_key_one.getText().toString().trim();
                String deleteTwo = delete_key_two.getText().toString().trim();
                String deleteThree = delete_key_three.getText().toString().trim();
                String deleteFour = delete_key_four.getText().toString().trim();
                mPresenter.delete(deleteOne, deleteTwo, deleteThree, deleteFour);
                break;
        }
    }
}
