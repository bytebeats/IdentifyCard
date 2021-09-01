package me.bytebeats.idcard.formatter

import android.text.Editable
import android.text.InputFilter
import android.text.Selection
import android.text.TextWatcher
import android.widget.EditText

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/8/30 21:33
 * @Version 1.0
 * @Description TO-DO
 */

class IdentifyCardTextWatcher(private val mEditor: EditText, var splitter: Splitter = Splitter.WHITESPACE) :
        TextWatcher {
    private var prevTextLength = 0
    private var curTextLength = 0
    private var isChanged = false
    private var cursor = 0 // cursor's location;
    private var tmpCharArray: CharArray? = null
    private val buffer = StringBuffer()
    private var splitterCounter = 0
    var onVerifyIdCardListener: OnVerifyIdCardListener? = null

    init {
        mEditor.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(LIMIT_OF_INPUT_LENGTH))
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        prevTextLength = s.length
        if (buffer.isNotEmpty()) {
            buffer.delete(0, buffer.length)
        }
        splitterCounter = 0
        for (element in s) {
            if (element == splitter.splitter) {
                splitterCounter++
            }
        }
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        curTextLength = s.length
        buffer.append(s)
        if (curTextLength == prevTextLength || curTextLength <= 3 || isChanged) {
            isChanged = false
            return
        }
        isChanged = true
    }

    override fun afterTextChanged(s: Editable) {
        if (isChanged) {
            cursor = mEditor.selectionEnd
            var index = 0
            while (index < buffer.length) {
                if (buffer[index] == splitter.splitter) {
                    buffer.deleteCharAt(index)
                } else {
                    index++
                }
            }
            index = 0
            var tmpSplitterCounter = 0
            while (index < buffer.length) {
                if (index == 4 || index == 9 || index == 14 || index == 19) {
                    buffer.insert(index, splitter.splitter)
                    tmpSplitterCounter++
                }
                index++
            }
            if (tmpSplitterCounter > splitterCounter) {
                cursor += tmpSplitterCounter - splitterCounter
            }
            tmpCharArray = CharArray(buffer.length)
            buffer.getChars(0, buffer.length, tmpCharArray, 0)
            val str = buffer.toString()
            if (cursor > str.length) {
                cursor = str.length
            } else if (cursor < 0) {
                cursor = 0
            }
            mEditor.setText(str)
            val editable = mEditor.text
            Selection.setSelection(editable, cursor)
            isChanged = false
            val trimmedBankCardNo = if (mEditor is IdentifyCardEditText) {
                trimmedIdCard(mEditor)
            } else {
                trimmedIdCard(mEditor, splitter)
            }
            if (onVerifyIdCardListener != null && verifyIdCard(trimmedBankCardNo)) {
                onVerifyIdCardListener?.onSuccess(trimmedBankCardNo!!)
            } else {
                onVerifyIdCardListener?.onFailure()
            }
        }
    }

    companion object {
        private const val LIMIT_OF_INPUT_LENGTH = 22
        private const val TAG = "IdentifyCardTextWatcher"

        fun trimmedIdCard(editor: IdentifyCardEditText): String? {
            return editor.text?.replace(Regex(editor.splitter.splitter.toString()), "")
        }

        fun trimmedIdCard(editor: EditText, splitter: Splitter): String? {
            return editor.text?.replace(Regex(splitter.splitter.toString()), "")
        }
    }
}