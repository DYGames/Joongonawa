package com.team7.joongonawa

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DetailPictureViewPagerAdapter(imgList: ArrayList<String>, private val context: Context) :
    RecyclerView.Adapter<DetailPictureViewPagerAdapter.PagerViewHolder>() {
    var item = imgList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        Glide.with(context).load("https://joongonawa-server-kfjur.run.goorm.io/public/" + item[position]).into(holder.img)
    }

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.viewpager_item, parent, false)){

        val img: ImageView = itemView.findViewById<ImageView>(R.id.detail_viewpager_img)
    }
}