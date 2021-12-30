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

## MIT License

    Copyright (c) 2021 Chen Pan

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
