package com.example.customviewpager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.customviewpager.databinding.ItemSlide1Binding

//class ViewPagerAdapter1(private var imageViews: MutableList<Int>) :
//    RecyclerView.Adapter<ViewPagerAdapter1.ImageViewHolder>() {
//    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val imageView: ImageView = itemView.findViewById(com.example.customviewpager.R.id.imv1)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_slide_1, parent, false)
//        return ImageViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
//
//        val imageViewResource = imageViews[position]
//        val imageView = holder.imageView
//
//        // Use Glide to load the image into the ImageView
//        Glide.with(holder.itemView)
//            .load(imageViewResource)
//            .into(imageView)
//
//    }
//
//    override fun getItemCount(): Int {
//        return imageViews.size
//    }
//
//}

class ViewPagerAdapter1(private var imageViews: MutableList<Int>) :
    RecyclerView.Adapter<ViewPagerAdapter1.ImageViewHolder>() {

    inner class ImageViewHolder(private val binding: ItemSlide1Binding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(imageViewResource: Int) {
            Glide.with(itemView)
                .load(imageViewResource)
                .into(binding.imv1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        // Sử dụng View Binding để inflate layout và tạo ViewHolder
        val binding = ItemSlide1Binding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageViewResource = imageViews[position]
        holder.bind(imageViewResource)
    }

    override fun getItemCount(): Int {
        return imageViews.size
    }
}
