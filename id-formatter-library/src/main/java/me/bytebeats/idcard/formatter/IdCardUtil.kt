package me.bytebeats.idcard.formatter

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/8/31 10:12
 * @Version 1.0
 * @Description TO-DO
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

/** 台湾身份首字母对应数字 */
val CHINA_TAIWAN_CODES = mapOf(
    "A" to 10,
    "B" to 11,
    "C" to 12,
    "D" to 13,
    "E" to 14,
    "F" to 15,
    "G" to 16,
    "H" to 17,
    "J" to 18,
    "K" to 19,
    "L" to 20,
    "M" to 21,
    "N" to 22,
    "P" to 23,
    "Q" to 24,
    "R" to 25,
    "S" to 26,
    "T" to 27,
    "U" to 28,
    "V" to 29,
    "X" to 30,
    "Y" to 31,
    "W" to 32,
    "Z" to 33,
    "I" to 34,
    "O" to 35
)

/** 香港身份首字母对应数字 */
val CHINA_HONGKONG_CODES = mapOf(
    "A" to 1,
    "B" to 2,
    "C" to 3,
    "R" to 18,
    "U" to 21,
    "Z" to 26,
    "X" to 24,
    "W" to 23,
    "O" to 15,
    "N" to 14
)

/**
 * 将15位身份证号码转换为18位
 */
fun convert15To18(cardNumber15: String?): String? {
    if (cardNumber15?.length != CHINA_ID_MIN_LENGTH) {
        return null
    }
    if (isAllDigital(cardNumber15)) {
        val birthDay = cardNumber15.substring(6, 12)
        val birthDate = try {
            SimpleDateFormat("yyMMdd").parse(birthDay)
        } catch (e: ParseException) {
            null
        }
        val calendar = Calendar.getInstance()
        birthDate?.let {
            calendar.time = it
        }
        val year = calendar.get(Calendar.YEAR).toString()
        val cardNumber18 = "${cardNumber15.substring(0, 6)}$year${cardNumber15.substring(8)}"
        val iCard = cardNumber18.map { (it - '0') }.toIntArray()
        val iSum17 = powerSum(iCard)
        val checkCode = checkCode(iSum17)
        return if (checkCode.isNotEmpty()) {
            "$cardNumber18$checkCode"
        } else null

    }
    return null
}

/**
 * 验证身份证是否合法
 */
fun verifyIdCard(card: String?): Boolean {
    if (verifyIdCard18(card)) return true
    if (verifyIdCard15(card)) return true
    if (verifyIdCard10(card)?.get(2) == true.toString()) {
        return true
    }
    return false
}

/**
 * 验证18位身份编码是否合法
 */
fun verifyIdCard18(card: String?): Boolean {
    if (card?.length == CHINA_ID_MAX_LENGTH) {
        val first17 = card.substring(0, 17)
        val eighteen = card.substring(17, CHINA_ID_MAX_LENGTH)
        if (isAllDigital(first17)) {
            val iFirst17 = first17.map { it - '0' }.toIntArray()
            val iSum17 = powerSum(iFirst17)
            val checkSum = checkCode(iSum17)
            if (eighteen == checkSum) return true
        }
    }
    return false
}

/**
 * 验证15位身份编码是否合法
 */
fun verifyIdCard15(card: String?): Boolean {
    if (card?.length == CHINA_ID_MIN_LENGTH) {
        val provinceCode = card.take(2)
        if (!CHINA_PROVINCES.containsKey(provinceCode)) {
            return false
        }
        val birthDay = card.substring(6, 12)
        val birthYear = try {
            SimpleDateFormat("yy").parse(birthDay.take(2))
        } catch (e: ParseException) {
            return false
        }
        val calendar = Calendar.getInstance()
        birthYear?.let {
            calendar.time = birthYear
        }
        return isDateValid(
            calendar.get(Calendar.YEAR),
            birthDay.substring(2, 4).toInt(),
            birthDay.substring(4, 6).toInt()
        )
    }
    return false
}

/**
 * 验证10位身份编码是否合法
 *
 * @param idCard 身份编码
 * @return 身份证信息数组
 *         <p>
 *         [0] - 台湾、澳门、香港 [1] - 性别(男M,女F,未知N) [2] - 是否合法(合法true,不合法false)
 *         若不是身份证件号码则返回null
 *         </p>
 */
fun verifyIdCard10(idCard: String?): Array<String>? {
    val info = Array(3) { "" };
    val card = idCard?.replace("[\\(|\\)]", "")
    if (card?.length !in 8..10) {
        return null
    }
    if (idCard?.matches(Regex("^[a-zA-Z][0-9]{9}\$")) == true) {
        info[0] = "台湾"
        val genderCode = idCard.substring(1, 2)
        if ("1" == genderCode) {
            info[1] = "M"
        } else if ("2" == genderCode) {
            info[1] = "F"
        } else {
            info[1] = "N"
            info[2] = false.toString()
            return info
        }
        info[2] = verifyTaiwanIdCard(idCard).toString()
    } else if (idCard?.matches(Regex("^[1|5|7][0-9]{6}\\(?[0-9A-Z]\\)?\$")) == true) {
        info[0] = "澳门"
        info[1] = "N"
    } else if (idCard?.matches(Regex("^[A-Z]{1,2}[0-9]{6}\\(?[0-9A]\\)?\$")) == true) {
        info[0] = "香港"
        info[1] = "N"
        info[2] = verfiyHKIdCard(idCard).toString()
    } else {
        return null
    }
    return info
}

/**
 * 验证台湾身份证号码
 *
 * @param idCard
 *            身份证号码
 * @return 验证码是否符合
 */
fun verifyTaiwanIdCard(card: String?): Boolean {
    if (card.isNullOrEmpty()) return false
    val start = card.take(1)
    val mid = card.substring(1, 9)
    val end = card.takeLast(1)
    val iStart = CHINA_TAIWAN_CODES[start]!!
    var sum = iStart / 10 + (iStart % 10) * 9
    val chs = mid.toCharArray()
    var iFlag = 8
    chs.forEach {
        sum += (it.toString().toInt()) * iFlag
        iFlag--
    }
    val iEnd = (if (sum % 10 == 0) 0
    else (10 - sum % 10))
    return iEnd == end.toInt()
}

/**
 * 验证香港身份证号码(存在Bug，部份特殊身份证无法检查)
 * <p>
 * 身份证前2位为英文字符，如果只出现一个英文字符则表示第一位是空格，对应数字58 前2位英文字符A-Z分别对应数字10-35
 * 最后一位校验码为0-9的数字加上字符"A"，"A"代表10
 * </p>
 * <p>
 * 将身份证号码全部转换为数字，分别对应乘9-1相加的总和，整除11则证件号码有效
 * </p>
 *
 * @param idCard 身份证号码
 * @return 验证码是否符合
 */
fun verfiyHKIdCard(card: String?): Boolean {
    if (card.isNullOrEmpty()) return false
    var sCard = card.replace("[\\(|\\)]", "")
    var sum = 0
    if (sCard.length == 9) {
        sum = (card.take(1).first().uppercaseChar().code - 55) * 9 +
                (card.substring(1, 2).first().uppercaseChar().code - 55) * 8
        sCard = sCard.substring(1, 9)
    } else {
        sum = 522 + (card.take(1).first().uppercaseChar().code - 55) * 8
    }
    val mid = sCard.substring(1, 7)
    val end = sCard.substring(7, 8)
    var iFlag = 7
    mid.forEach {
        sum += it.toString().toInt() * iFlag
        iFlag--
    }
    if (end.uppercase() == "A") {
        sum += 10
    } else {
        sum += end.toInt()
    }
    return sum % 11 == 0
}

private fun isDateValid(iYear: Int, iMonth: Int, iDate: Int): Boolean {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    if (iYear !in MIN_BIRTH_YEAR..year) {
        return false
    }
    if (iMonth !in 1..12) {
        return false
    }
    var daysPerMonth = 0
    when (iMonth) {
        4, 6, 9, 11 -> daysPerMonth = 30
        2 -> {
            val loop = iYear % 4 == 0 && iYear % 100 != 0 || iYear % 400 == 0
            daysPerMonth = if (loop) 29 else 28
        }
        else -> daysPerMonth = 31
    }
    return iDate in 1 until daysPerMonth
}

private fun isAllDigital(src: String?): Boolean {
    return !src.isNullOrEmpty() && src.matches(Regex("^[0-9]*\$"))
}

private fun powerSum(arr: IntArray): Int {
    return if (arr.size == BIT_POWERS.size) {
        arr.mapIndexed { index, i -> i * BIT_POWERS[index] }.sum()
    } else 0
}

private fun checkCode(iSum17: Int): String {
    return when (iSum17 % 11) {
        10 -> "2"
        9 -> "3"
        8 -> "4"
        7 -> "5"
        6 -> "6"
        5 -> "7"
        4 -> "8"
        3 -> "9"
        2 -> "x"
        1 -> "0"
        0 -> "1"
        else -> ""
    }
}

