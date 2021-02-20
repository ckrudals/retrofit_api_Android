package com.example.jetpack_practice

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.example.jetpack_practice.R.id.search_photo_radio_button
import com.example.jetpack_practice.utils.Constants.TAG
import com.example.jetpack_practice.utils.RESPONSE_STATE
import com.example.jetpack_practice.utils.RetrofitManager
import com.example.jetpack_practice.utils.SEARCH_TYPE
import com.example.jetpack_practice.utils.onMyTextChanged
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_recyclerview.*
import kotlinx.android.synthetic.main.layout_button_search.*

class MainActivity : AppCompatActivity() {


    private var currentSearchType: SEARCH_TYPE = SEARCH_TYPE.PHOTO

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate: called")


        // 라디오 그룹 가져오기
        search_radio_group.setOnCheckedChangeListener { _, checkedId ->

           
            when (checkedId) { //id에 따라 내용 바꾸기
                R.id.search_photo_radio_button -> {
                    Log.d(TAG, "사진 버튼 클릭!")
                    search_text.hint = "사진"
                    search_text.startIconDrawable = resources.getDrawable(
                        R.drawable.ic_baseline_photo_library_24,
                        resources.newTheme()
                    )
                    this.currentSearchType = SEARCH_TYPE.PHOTO
                }
                R.id.search_user_radio_button -> {
                    Log.d(TAG, "사용자 검색 버튼 클릭!")
                    search_text.hint = "사용자 검색"
                    search_text.startIconDrawable = resources.getDrawable(
                        R.drawable.ic_baseline_person_24,
                        resources.newTheme()
                    )
                    this.currentSearchType = SEARCH_TYPE.USER
                }
            }

        }

        //extention
        search_edit_text.onMyTextChanged {
            //글자 입력 시
            if (it.toString().count() > 0) {
                frame_search_btn.visibility = View.VISIBLE
                //글자가 하나라도 입력시 아래 텍스트를 지움 null
                search_text.helperText = ""
                // 스크롤뷰를 올린다.
                main_scrollView.scrollTo(0, 200)
            } else {
                frame_search_btn.visibility = View.INVISIBLE
                search_text.helperText = "검색어를 입력해주세요"
            }
            if (it.toString().count() == 12) {
                Log.d(TAG, "onCreate: 에러 띄움")
                Toast.makeText(applicationContext, "최대 12글자 까지 입력이 가능합니다.! ", LENGTH_SHORT).show()
            }
        }
        // 검색  버튼 이벤트
        btn_search.setOnClickListener {
            Log.d(TAG, "onCreate: 검색 버튼 클릭")

            val userSearchinput=search_edit_text.text.toString()
            RetrofitManager.instance.searchPhotos(searchTerm = search_edit_text.text.toString(),completion = {
                responseState, responseBodyDataArrayList ->

                when(responseState){
                    RESPONSE_STATE.OKAY->{
                        Log.d(TAG, "Api 호출 성공 : ${responseBodyDataArrayList?.size}")

                        val intent= Intent(this,PhotoCollectionActivity::class.java)

                        val bundle=Bundle()
                        bundle.putSerializable("photo_array_list",responseBodyDataArrayList) //직렬화 방식

                        intent.putExtra("array_bundle",bundle) //데이터

                        intent.putExtra("search_term",userSearchinput) //검색어

                        startActivity(intent)
                    }

                    RESPONSE_STATE.FAIL->{
                        Toast.makeText(this,"API 호출 에러",Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "Api 호출 실패 : $responseBodyDataArrayList")
                    }
                }
            })
            this.handleSearchButton()
        }
    }


    private fun handleSearchButton() {
        btn_progress.visibility = View.VISIBLE
        btn_search.text = ""

        Handler().postDelayed({
            btn_progress.visibility = View.INVISIBLE
            btn_search.text = "검색"
        }, 1500)

    }
}