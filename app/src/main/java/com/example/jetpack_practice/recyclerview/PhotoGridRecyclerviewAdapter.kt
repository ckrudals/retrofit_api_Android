package com.example.jetpack_practice.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpack_practice.Model.Photo
import com.example.jetpack_practice.R

class PhotoGridRecyclerviewAdapter : RecyclerView.Adapter<PhotoItemViewHolder>() {

    private var photoList = ArrayList<Photo>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemViewHolder {

        return PhotoItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PhotoItemViewHolder, position: Int) {

        holder.bind(this.photoList[position])
    }

    override fun getItemCount(): Int {

        return photoList.size
    }

    // 외부에서 어뎁터에 배열 넣기
    fun submitList(photoList: ArrayList<Photo>) {
        this.photoList = photoList
    }
}