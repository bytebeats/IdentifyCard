package me.bytebeats.idcard.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import me.bytebeats.idcard.formatter.*
import me.bytebeats.idcard.generator.IDCardGenerator

class MainActivity : AppCompatActivity() {
    private val editor by lazy { findViewById<IdentifyCardEditText>(R.id.id_card_editor) }
    private val editor2 by lazy { findViewById<EditText>(R.id.id_card_editor_2) }
    private val editor3 by lazy { findViewById<IdentifyCardEditText>(R.id.id_card_editor_3) }
    private val generator by lazy { findViewById<Button>(R.id.btn_generate) }
    private val verifier by lazy { findViewById<Button>(R.id.btn_verify) }
    private val verify1 by lazy { findViewById<TextView>(R.id.verify_1) }
    private val verify2 by lazy { findViewById<TextView>(R.id.verify_2) }
    private val verify3 by lazy { findViewById<TextView>(R.id.verify_3) }

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
                    verify2.text = "Invalid"
                }

                override fun onSuccess(idCard: String) {
                    verify2.text = idCard
                }
            }
        })
        verifier.setOnClickListener {
            verify1.text = if (verifyIdCard(editor.trimmedIdCard())) "Valid" else "Invalid"
            editor3.verify(object : OnVerifyIdCardListener {
                override fun onFailure() {
                    verify3.text = "Invalid"
                }

                override fun onSuccess(idCard: String) {
                    verify3.text = idCard
                }
            })
        }
    }
}