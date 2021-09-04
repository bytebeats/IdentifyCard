package me.bytebeats.idcard.generator

import me.bytebeats.idcard.base.PROVINCE_CODES
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/9/1 10:17
 * @Version 1.0
 * @Description TO-DO
 */

class IDCardGenerator private constructor() {
    private val random: Random
        get() = Random(Date().time)

    fun generate(): String {
        val provinceCode = PROVINCE_CODES[random.nextInt(PROVINCE_CODES.size)]
        val cityCode = random.nextInt(1, 9999).toString().padStart(4, '0')
        val birthDay = SimpleDateFormat("yyyyMMdd").format(randomDate())
        val randomCode = random.nextInt(0, 999).toString().padStart(3, '0')
        val idCard17 = "$provinceCode$cityCode$birthDay$randomCode"
        val verifyCode = verifyCode(idCard17)
        return "$idCard17$verifyCode"
    }

    private fun randomDate(): Date {
        val calendar = Calendar.getInstance()
        val now = calendar.time.time
        calendar.set(1970, 1, 1)
        val earliestDate = calendar.time.time
        return Date(random.nextLong(earliestDate, now))
    }

    private fun verifyCode(idCard: String): String {
        val valCodeArr = arrayOf(
            "1", "0", "X", "9", "8", "7", "6", "5", "4",
            "3", "2"
        )
        val wi = arrayOf(
            "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
            "9", "10", "5", "8", "4", "2"
        )
        var iSum17 = 0
        wi.forEachIndexed { index, s ->
            iSum17 += idCard[index].toString().toInt() * s.toInt()
        }
        return valCodeArr[iSum17 % 11]
    }

    companion object {
        private var INSTANCE: IDCardGenerator? = null
        fun instance(): IDCardGenerator {
            if (INSTANCE == null) {
                synchronized(IDCardGenerator::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = IDCardGenerator()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}