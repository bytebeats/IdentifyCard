package me.bytebeats.idcard.base

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/9/1 10:21
 * @Version 1.0
 * @Description Constants
 */

/** 中国公民身份证号码最小长度 */
const val CHINA_ID_MIN_LENGTH = 15

/** 中国公民身份证号码最大长度 */
const val CHINA_ID_MAX_LENGTH = 18

/** 省、直辖市代码表 */
val PROVINCE_CODES = arrayOf(
    "11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41",
    "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71",
    "81", "82", "91"
)

/** 每位加权因子 */
val BIT_POWERS = arrayOf(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2)

/** 第18位校检码 */
val CHECK_CODES = arrayOf("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2")

/** 最低年限 */
const val MIN_BIRTH_YEAR = 1920
val CHINA_PROVINCES = mapOf(
    "11" to "北京",
    "12" to "天津",
    "13" to "河北",
    "14" to "山西",
    "15" to "内蒙古",
    "21" to "辽宁",
    "22" to "吉林",
    "23" to "黑龙江",
    "31" to "上海",
    "32" to "江苏",
    "33" to "浙江",
    "34" to "安徽",
    "35" to "福建",
    "36" to "江西",
    "37" to "山东",
    "41" to "河南",
    "42" to "湖北",
    "43" to "湖南",
    "44" to "广东",
    "45" to "广西",
    "46" to "海南",
    "50" to "重庆",
    "51" to "四川",
    "52" to "贵州",
    "53" to "云南",
    "54" to "西藏",
    "61" to "陕西",
    "62" to "甘肃",
    "63" to "青海",
    "64" to "宁夏",
    "65" to "新疆",
    "71" to "台湾",
    "81" to "香港",
    "82" to "澳门",
    "91" to "国外"
)