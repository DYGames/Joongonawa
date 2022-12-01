package com.team7.joongonawa

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class CategoryAdapter(private val context : Context, private val productTypeResult: ActivityResultLauncher<Intent>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val categoryImg : ImageView = itemView.findViewById(R.id.categoryImage)
        private val categoryTitle : TextView = itemView.findViewById(R.id.categoryTitle)

        fun bind(category : CategoryData) {
            Glide.with(itemView).load("https://joongonawa-server-kfjur.run.goorm.io/public/"+category.pic).into(categoryImg)
            categoryTitle.text = category.name
            itemView.setOnClickListener(View.OnClickListener {
                val intent = Intent(context, CategoryTypeActivity::class.java)
                intent.apply {
                    putExtra("categoryId", category.id)
                    putExtra("categoryName", category.name)
                }

                productTypeResult.launch(intent)
            })
        }
    }
    var datas = mutableListOf<CategoryData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size
}
