package com.team7.joongonawa

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.team7.joongonawa.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityCategoryBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    lateinit var categoryAdapter: CategoryAdapter
    val datas = mutableListOf<CategoryData>()

    private val categoryViewModel = CategoryViewModel(CategoryRepository.instance)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycler()

        binding.addCategoryBtn.setOnClickListener(View.OnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.main_frame, UpdateCategoryFragment()).addToBackStack(null)
                .commitAllowingStateLoss()
        })
    }

    override fun onResume() {
        super.onResume()
        categoryViewModel.getCategoryList()
        categoryViewModel.categoryList.observe(this) {
            datas.clear()
            for(i in 0 until it.size)
                datas.apply {
                    add(CategoryData(it[i].id, it[i].pic, it[i].name))
                }
            categoryAdapter.datas = datas
            categoryAdapter.notifyDataSetChanged()
            Log.d("changed", it.size.toString())
        }
    }

    private fun initRecycler() {
        categoryAdapter = CategoryAdapter(this)
        binding.recyclerview.adapter = categoryAdapter
        binding.recyclerview.layoutManager = GridLayoutManager(this, 2)

        categoryAdapter.datas = datas
        categoryAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }

}

