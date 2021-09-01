package me.bytebeats.idcard.formatter

import android.content.Context
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/8/30 21:33
 * @Version 1.0
 * @Description TO-DO
 */

class IdentifyCardEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    var onVerifyIdCardListener: OnVerifyIdCardListener?
        get() = mTextWatcher.onVerifyIdCardListener
        set(value) {
            mTextWatcher.onVerifyIdCardListener = value
        }

    private val mTextWatcher by lazy { IdentifyCardTextWatcher(this) }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.IdentifyCardEditText, defStyleAttr, 0)
        val splitterIdx = typedArray.getInt(R.styleable.IdentifyCardEditText_splitter, 0)
        mTextWatcher.splitter = Splitter.values()[splitterIdx]
        typedArray.recycle()
        addTextChangedListener(mTextWatcher)
        isFocusable = true
        isEnabled = true
        isFocusableInTouchMode = true
        inputType = InputType.TYPE_NUMBER_FLAG_SIGNED
        keyListener = DigitsKeyListener.getInstance(context.getString(R.string.id_card_digits))
    }

    val splitter: Splitter
        get() = mTextWatcher.splitter

    fun trimmedIdCard(): String? = IdentifyCardTextWatcher.trimmedIdCard(this)

    fun verify(listener: OnVerifyIdCardListener?) {
        trimmedIdCard()?.let {
            if (verifyIdCard(it)) {
                listener?.onSuccess(it)
            } else {
                listener?.onFailure()
            }
        }
    }
}