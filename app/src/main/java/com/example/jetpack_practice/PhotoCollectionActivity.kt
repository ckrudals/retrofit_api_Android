package com.example.jetpack_practice

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.*
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.jetpack_practice.Model.Photo
import com.example.jetpack_practice.recyclerview.PhotoGridRecyclerviewAdapter
import kotlinx.android.synthetic.main.activity_recyclerview.*

class PhotoCollectionActivity : AppCompatActivity(), SearchView.OnQueryTextListener,CompoundButton.OnCheckedChangeListener,View.OnClickListener {
    private val TAG = "PhotoCollectionActivity"

    var photoList = ArrayList<Photo>()

    // 어뎁터
    private lateinit var photoGrideRecyclerViewAdapter: PhotoGridRecyclerviewAdapter

    // Search View
    private lateinit var mySearchView: SearchView

    // Search View EditText

    private lateinit var mySearchViewEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        Log.d(TAG, "PhotoCollectionActivity - onCreate() called")
        search_history_switch.setOnCheckedChangeListener(this)
        clear_search_history.setOnClickListener(this)
        val bundle = intent.getBundleExtra("array_bundle")

        val searchTerm = intent.getStringExtra("search_term")

        photoList = bundle?.getSerializable("photo_array_list") as ArrayList<Photo>

        Log.d(
            TAG,
            "PhotoCollectionActivity - onCreate() called searchTerm : $searchTerm,photolList.count() : ${photoList.count()}"
        )

        top_app_bar.title = searchTerm

        setSupportActionBar(top_app_bar)
        this.photoGrideRecyclerViewAdapter = PhotoGridRecyclerviewAdapter()
        this.photoGrideRecyclerViewAdapter.submitList(photoList)

        my_photo_recycler_view.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        my_photo_recycler_view.adapter = photoGrideRecyclerViewAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {


        Log.d(TAG, "PhotoCollectionActivity - onCreateOptionsMenu() called")

        val inflater = menuInflater
        inflater.inflate(R.menu.top_app_bar, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        this.mySearchView = menu?.findItem(R.id.search_menu_item)?.actionView as SearchView

        this.mySearchView.apply {
            this.queryHint = "검색어를 입력해주세요"

            this.setOnQueryTextListener(this@PhotoCollectionActivity)
            this.setOnQueryTextFocusChangeListener { _, hasExpaned ->

                //hasExpaned는 검색창이 열린상태다 
                when (hasExpaned) {
                    true -> {
                        Log.d(TAG, "onCreateOptionsMenu: 뷰 열림")
                        search_history_view.visibility= View.VISIBLE
                    }
                    false -> {
                        Log.d(TAG, "onCreateOptionsMenu: 뷰 닫힘")
                        search_history_view.visibility= View.INVISIBLE
                    }
                }
            }


            // Search View 에서 EditText 를 가져옴
            mySearchViewEditText = findViewById(androidx.appcompat.R.id.search_src_text)

        }
        this.mySearchViewEditText.apply {
            this.filters = arrayOf(InputFilter.LengthFilter(12)) // 글자수 제한
            this.setTextColor(Color.WHITE) //색깔 변경
        }
        return true
    }

    // 검색이 누른 상태
    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d(TAG, "PhotoCollectionActivity - onQueryTextSubmit() called")

        if (!query.isNullOrEmpty()) {
            this.top_app_bar.title= query

            //api 호출
            //검색어 저장
        }
        this.mySearchView.setQuery("", false)
        this.mySearchView.clearFocus()

        this.top_app_bar.collapseActionView()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d(TAG, "PhotoCollectionActivity - onQueryTextChange() called")
        val userInputText = newText ?: "" //null이면 null값

        if (userInputText.count() == 12) {
            Toast.makeText(this, "검색어는 12자 까지만 가능합니다.", LENGTH_SHORT).show()
        }
        return true
    }

    override fun onCheckedChanged(switch: CompoundButton?, isChecked: Boolean) {
        when (switch){
            search_history_switch -> {
                if (isChecked) {
                    Log.d(TAG, "검색어 저장기능 true")
                } else {
                    Log.d(TAG, "onCheckedChanged: false")
                }
            }
        }
    }

    override fun onClick(view: View?) {
        when(view){
            clear_search_history -> {
                Log.d(TAG, "onClick: 검색 삭제 버튼클릭")
            }

        }
    }
}