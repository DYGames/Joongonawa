package com.team7.joongonawa

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.team7.joongonawa.databinding.ActivityCategoryBinding
import com.team7.joongonawa.databinding.ActivityCategoryTypeBinding

class CategoryTypeActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityCategoryTypeBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    lateinit var categoryTypeAdapter: CategoryTypeAdapter

    val datas = mutableListOf<CategoryTypeItemData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCategoryTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.categoryType.setText(intent.getStringExtra("categoryName"))
        initRecycler()
    }

    private fun initRecycler() {
        categoryTypeAdapter = CategoryTypeAdapter(this)
        binding.categoryRecycler.adapter = categoryTypeAdapter
        binding.categoryRecycler.layoutManager = LinearLayoutManager(this)

        datas.apply {
            add(CategoryTypeItemData(img = R.drawable.ic_launcher_background, title = "아이폰14"))
            add(CategoryTypeItemData(img = R.drawable.ic_launcher_background, title = "아이폰12"))
            add(CategoryTypeItemData(img = R.drawable.ic_launcher_background, title = "아이폰XS"))
            add(CategoryTypeItemData(img = R.drawable.ic_launcher_background, title = "에어팟"))
        }

        categoryTypeAdapter.datas = datas
        categoryTypeAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}