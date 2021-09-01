package me.bytebeats.idcard.formatter

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/9/1 19:52
 * @Version 1.0
 * @Description TO-DO
 */

interface OnVerifyIdCardListener {
    fun onSuccess(idCard: String)
    fun onFailure()
}