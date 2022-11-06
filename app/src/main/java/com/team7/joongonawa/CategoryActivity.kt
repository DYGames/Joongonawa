package com.team7.joongonawa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.team7.joongonawa.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityCategoryBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

        lateinit var categoryAdapter: CategoryAdapter
    val datas = mutableListOf<CategoryItemData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycler()
    }

    private fun initRecycler() {
        categoryAdapter = CategoryAdapter(this)
        binding.recyclerview.adapter = categoryAdapter
        binding.recyclerview.layoutManager = GridLayoutManager(this, 2)
        
        datas.apply {
            add(CategoryItemData(img = R.drawable.ic_launcher_background, title = "생활용품"))
            add(CategoryItemData(img = R.drawable.ic_launcher_background, title = "가전제품"))
            add(CategoryItemData(img = R.drawable.ic_launcher_background, title = "소모품"))
            add(CategoryItemData(img = R.drawable.ic_launcher_background, title = "음식"))
            add(CategoryItemData(img = R.drawable.ic_launcher_background, title = "여가"))
            add(CategoryItemData(img = R.drawable.ic_launcher_background, title = "애완용품"))
            add(CategoryItemData(img = R.drawable.ic_launcher_background, title = "오락"))
        }

        categoryAdapter.datas = datas
        categoryAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}

