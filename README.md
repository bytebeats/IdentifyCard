# IdentifyCard
随机生成中国大陆身份证号. 自动格式化身份证输入. 验证身份证是否合法. 电子身份证生成. 扫描实体身份证识别出身份证信息.

Effect Graph
-------
<img src="/media/id_card.gif" width="360" height="800"/>

How to use?
------
<br>In xml files,
```
        <me.bytebeats.idcard.formatter.IdentifyCardEditText
            android:id="@+id/id_card_editor"
            android:paddingStart="15dp"
            android:paddingEnd="15dp"
            android:inputType="numberSigned"
            android:digits="0123456789X"
            app:splitter="pound"
            android:layout_width="0dp"
            android:layout_weight="1"
            android:layout_height="wrap_content"
            android:hint="18 Id Card Number" />

```
<br>In codes,
```
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
```

Features
------
- 随机生成中国大陆身份证号.
- 自动格式化身份证输入.
- 验证身份证是否合法.
- 电子身份证生成.(To-Do)
- 扫描实体身份证识别出身份证信息.(To-Do)