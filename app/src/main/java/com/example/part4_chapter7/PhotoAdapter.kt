package com.example.part4_chapter7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.part4_chapter7.data.models.PhotoResponse
import com.example.part4_chapter7.databinding.ItemPhotoBinding

class PhotoAdapter(private val onClickedPhoto:(PhotoResponse) ->Unit) : ListAdapter<PhotoResponse, PhotoAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: PhotoResponse) = with(binding) {

            val dimensionRatio = photo.height / photo.width.toFloat()
            val targetWidth = root.resources.displayMetrics.widthPixels -(root.paddingStart+root.paddingEnd)
            val targetHeight =(targetWidth*dimensionRatio).toInt()

            contentsContainer.layoutParams = contentsContainer.layoutParams.apply {
                height = targetHeight
            }

            Glide.with(binding.root)
                .load(photo.urls?.regular)
                .thumbnail(
                    Glide.with(root)
                        .load(photo.urls?.thumb)
                        .transition(DrawableTransitionOptions.withCrossFade())
                )
                .override(targetWidth,targetHeight)
                .into(photoImageView)

            Glide.with(binding.root)
                .load(photo.user?.profileImageUrls?.small)
                .placeholder(R.drawable.shape_profile_placeholder)
                .circleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(profileImageView)

            authorTextView.text = photo.user?.name ?: ""
            descriptionTextView.text = photo.description ?:""

            itemView.setOnClickListener {
                onClickedPhoto(photo)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<PhotoResponse>() {
            override fun areItemsTheSame(
                oldItem: PhotoResponse,
                newItem: PhotoResponse
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PhotoResponse,
                newItem: PhotoResponse
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}