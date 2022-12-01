package com.team7.joongonawa

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class DetailPictureViewPagerAdapter(imgList: ArrayList<Int>) :
    RecyclerView.Adapter<DetailPictureViewPagerAdapter.PagerViewHolder>() {
    var item = imgList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.img.setImageResource(item[position])
    }

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.viewpager_item, parent, false)){

        val img: ImageView = itemView.findViewById<ImageView>(R.id.detail_viewpager_img)
    }
}