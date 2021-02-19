package com.example.jetpack_practice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpack_practice.Model.Photo
import com.example.jetpack_practice.recyclerview.PhotoGridRecyclerviewAdapter
import kotlinx.android.synthetic.main.activity_recyclerview.*
import java.io.Serializable

class PhotoCollectionActivity : AppCompatActivity() {
    private val TAG = "PhotoCollectionActivity"

    var photoList = ArrayList<Photo>()

    // 어뎁터
    private lateinit var photoGrideRecyclerViewAdapter: PhotoGridRecyclerviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        Log.d(TAG, "PhotoCollectionActivity - onCreate() called")

        val bundle = intent.getBundleExtra("array_bundle")

        val searchTerm = intent.getStringExtra("search_term")

        photoList = bundle?.getSerializable("photo_array_list") as ArrayList<Photo>

        Log.d(
            TAG,
            "PhotoCollectionActivity - onCreate() called searchTerm : $searchTerm,photolList.count() : ${photoList.count()}"
        )
        topAppBar.title = searchTerm
        this.photoGrideRecyclerViewAdapter = PhotoGridRecyclerviewAdapter()
        this.photoGrideRecyclerViewAdapter.submitList(photoList)

        photo_recycler_view.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        photo_recycler_view.adapter = photoGrideRecyclerViewAdapter
    }
}