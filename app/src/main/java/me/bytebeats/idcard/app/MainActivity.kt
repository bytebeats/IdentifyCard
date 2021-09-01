package me.bytebeats.idcard.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import me.bytebeats.idcard.formatter.verifyIdCard
import me.bytebeats.idcard.generator.IDCardGenerator

class MainActivity : AppCompatActivity() {
    private val editor by lazy { findViewById<EditText>(R.id.id_card_editor) }
    private val generator by lazy { findViewById<Button>(R.id.btn_generate) }
    private val verifier by lazy { findViewById<Button>(R.id.btn_verify) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        generator.setOnClickListener { editor.setText(IDCardGenerator.instance().generate()) }
        verifier.setOnClickListener {
            Toast.makeText(this, verifyIdCard(editor.text.toString()).toString(), Toast.LENGTH_SHORT).show()
        }
    }
}