package com.team7.joongonawa

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class CategoryAdapter(private val context : Context) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val categoryImg : ImageView = itemView.findViewById(R.id.categoryImage)
        private val categoryTitle : TextView = itemView.findViewById(R.id.categoryTitle)

        fun bind(category : CategoryItemData) {
            Glide.with(itemView).load(category.img).into(categoryImg)
            categoryTitle.text = category.title
            itemView.setOnClickListener(View.OnClickListener {
                val intent = Intent(context, CategoryTypeActivity::class.java)
                intent.putExtra("categoryName", categoryTitle.text)
                intent.run{ context.startActivity(this)}
            })
        }
    }
    var datas = mutableListOf<CategoryItemData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size
}