package com.example.jetpack_practice.recyclerview

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jetpack_practice.App
import com.example.jetpack_practice.Model.Photo
import com.example.jetpack_practice.R
import com.example.jetpack_practice.utils.Constants.TAG
import kotlinx.android.synthetic.main.recycler_item.view.*

class PhotoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    // 뷰들 가져오기
    private val photoImageView = itemView.photo_image
    private val photoCreatedAtText = itemView.created_at_text
    private val photoLikesCountText = itemView.likes_count_text


    fun bind(photoItem: Photo) {
        Log.d(TAG, "PhotoItemViewHolder - bind() called")

        photoCreatedAtText.text=photoItem.createdAt

        photoLikesCountText.text=photoItem.likeCount.toString()

        // 이미지를 설정한다.
        Glide.with(App.instance) //context
            .load(photoItem.thumbnail)
            .placeholder(R.drawable.ic_baseline_insert_photo_24)
            .into(photoImageView)
    }
}