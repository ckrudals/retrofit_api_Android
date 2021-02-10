package com.example.jetpack_practice.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

// 에딧 텍스트에 대한 익스텐션
//데이터 반환 안할시, Editable 에 null
fun EditText.onMyTextChanged(completion:(Editable?)->Unit){ //comlection block
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { //글자가 바뀌기전

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { //글자가 바뀜

        }

        override fun afterTextChanged(editable: Editable?) { //글자가 바뀐 후
            completion(editable)
        }

    })

}