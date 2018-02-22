package com.liqi.utils.db;

/**
 * 数据库操作查询符号返回
 * Created by LiQi on 2017/12/6.
 */

enum DataBaseValuesEnum {
    /**
     * 模糊查询符号“%”和查询拼接关键字“or”
     */
    QUERY_SYMBOL_FUZZY {
        String getQuerySymbol() {
            return "%";
        }

        String getQueryJointSymbol() {
            return " or ";
        }
    },

    /**
     * 无模糊查询符号和查询拼接关键字“and”
     */
    QUERY_SYMBOL_NULL {
        String getQuerySymbol() {
            return "";
        }

        String getQueryJointSymbol() {
            return " and ";
        }
    },

    /**
     * 模糊查询符号和查询拼接关键字“and”
     */
    QUERY_SYMBOL_FUZZY_NULL {
        String getQuerySymbol() {
            return "%";
        }

        String getQueryJointSymbol() {
            return " and ";
        }
    },

    /**
     * 无模糊查询符号和查询拼接关键字“or”
     */
    QUERY_SYMBOL_NULL_OR {
        String getQuerySymbol() {
            return "";
        }

        String getQueryJointSymbol() {
            return " or ";
        }
    };

    String getQuerySymbol() {
        return null;
    }

    String getQueryJointSymbol() {
        return null;
    }
}
