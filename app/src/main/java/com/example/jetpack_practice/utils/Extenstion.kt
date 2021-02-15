package com.example.jetpack_practice.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

// 문자열이 제이슨 형태인지, 제이슨 배열 형태인지
fun String?.isJsonObject(): Boolean {
    //startWith, endWith 쓰임새 알기
    return this?.startsWith("{") == true && this.endsWith("}")

}

//문자열이 제이슨 배열 확인
fun String?.isJsonArray():Boolean{
    return this?.startsWith("{") == true && this.endsWith("}")

}

//clean code
//fun String?.inJsonObject():Boolean{
//  if (this?.startsWith("{") == true && this.endsWith("}"))
//  return true
//  else {
//  return false
//}

//}

// 에딧 텍스트에 대한 익스텐션
//데이터 반환 안할시, Editable 에 null
fun EditText.onMyTextChanged(completion: (Editable?) -> Unit) { //comlection block
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) { //글자가 바뀌기전

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { //글자가 바뀜

        }

        override fun afterTextChanged(editable: Editable?) { //글자가 바뀐 후
            completion(editable)
        }

    })

}