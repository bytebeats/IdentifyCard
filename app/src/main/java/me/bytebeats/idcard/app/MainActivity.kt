package me.bytebeats.idcard.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import me.bytebeats.idcard.formatter.*
import me.bytebeats.idcard.generator.IDCardGenerator

class MainActivity : AppCompatActivity() {
    private val editor by lazy { findViewById<IdentifyCardEditText>(R.id.id_card_editor) }
    private val editor2 by lazy { findViewById<EditText>(R.id.id_card_editor_2) }
    private val editor3 by lazy { findViewById<IdentifyCardEditText>(R.id.id_card_editor_3) }
    private val generator by lazy { findViewById<Button>(R.id.btn_generate) }
    private val verifier by lazy { findViewById<Button>(R.id.btn_verify) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        generator.setOnClickListener {
            editor.setText(IDCardGenerator.instance().generate())
            editor.text?.let { s -> editor.setSelection(s.length) }
        }
        editor2.addTextChangedListener(IdentifyCardTextWatcher(editor2, Splitter.WHITESPACE).apply {
            onVerifyIdCardListener = object : OnVerifyIdCardListener {
                override fun onFailure() {
                    editor2.error = "Invalid"
                }

                override fun onSuccess(idCard: String) {
                    editor2.error = idCard
                }
            }
        })
        verifier.setOnClickListener {
            editor.error = verifyIdCard(editor.trimmedIdCard()).toString()
            editor3.verify(object : OnVerifyIdCardListener {
                override fun onFailure() {
                    editor3.error = "Invalid"
                }

                override fun onSuccess(idCard: String) {
                    editor3.error = idCard
                }
            })
        }
    }
}