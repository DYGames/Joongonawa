package com.team7.joongonawa

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.team7.joongonawa.databinding.ActivityCategoryBinding
import com.team7.joongonawa.databinding.ActivityCategoryTypeBinding
import java.util.Locale.Category

class CategoryTypeActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityCategoryTypeBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    var categoryId : Int = 0

    private lateinit var categoryTypeAdapter: CategoryTypeAdapter
    val datas = mutableListOf<CategoryTypeData>()
    private val categoryViewModel = CategoryViewModel(CategoryRepository.instance)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCategoryTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryId = intent.getIntExtra("categoryId", 0)

        categoryViewModel.getCategoryTypeList(categoryId)

        initRecycler()

        categoryViewModel.categoryTypeList.observe(this) {
            for(i in 0 until it.size)
                datas.apply {
                    add(CategoryTypeData(it[i].pic, it[i].name, it[i].category))
                }
            categoryTypeAdapter.datas = datas
            categoryTypeAdapter.notifyDataSetChanged()
        }

        binding.categoryType.text = intent.getStringExtra("categoryName")

        binding.addCategoryTypeBtn.setOnClickListener(View.OnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.categoryType_frame, UpdateCategoryTypeFragment()).addToBackStack(null)
                .commitAllowingStateLoss()
        })
    }

    private fun initRecycler() {
        categoryTypeAdapter = CategoryTypeAdapter(this)
        binding.categoryRecycler.adapter = categoryTypeAdapter
        binding.categoryRecycler.layoutManager = LinearLayoutManager(this)

        categoryTypeAdapter.datas = datas
        categoryTypeAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }

}