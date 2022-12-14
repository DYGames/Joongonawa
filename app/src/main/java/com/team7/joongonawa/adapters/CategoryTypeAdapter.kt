package com.team7.joongonawa

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CategoryTypeAdapter(private val context : Context) : RecyclerView.Adapter<CategoryTypeAdapter.ViewHolder>() {
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val categoryTypeImg : ImageView = itemView.findViewById(R.id.categoryTypeImg)
        private val categoryTypeTitle : TextView = itemView.findViewById(R.id.categoryTypeTitle)

        fun bind(categoryType : CategoryTypeData) {
            Glide.with(itemView).load("https://joongonawa-server-kfjur.run.goorm.io/public/"+categoryType.pic).into(categoryTypeImg)
            categoryTypeTitle.text = categoryType.name
            itemView.setOnClickListener(View.OnClickListener {
                val intent = Intent(context, ItemListActivity::class.java)
                intent.putExtra("productType", categoryType.id)
                intent.putExtra("productName", categoryType.name)
                intent.putExtra("categoryId", (context as CategoryTypeActivity).categoryId)
                intent.putExtra("categoryName", context.categoryName)
                context.setResult(1, intent)
                context.finish()
            })
        }
    }

    var datas = mutableListOf<CategoryTypeData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_type_item_view, parent, false)
        view.setOnClickListener(View.OnClickListener {
            Toast.makeText(context, "hi", Toast.LENGTH_SHORT).show()
        })
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size
}