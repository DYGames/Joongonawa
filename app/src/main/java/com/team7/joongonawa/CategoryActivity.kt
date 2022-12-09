package com.team7.joongonawa

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.team7.joongonawa.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityCategoryBinding? = null

    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    lateinit var categoryAdapter: CategoryAdapter
    val datas = mutableListOf<CategoryData>()

    public val categoryViewModel = CategoryViewModel(CategoryRepository.instance)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 리사이클러뷰 초기화
        initRecycler()

        // 카테고리리스트에 새로운 항목이 추가되었는지 확인하는 코드
        var updateCategoryFragment = UpdateCategoryFragment()
        categoryViewModel.uploadState.observe(this) {
            if (it) {
                categoryViewModel.getCategoryList()
                supportFragmentManager.beginTransaction().remove(updateCategoryFragment).commit()
            }
        }

        // categoryList LiveData가 업데이트되면 리사이클러뷰의 어댑터에 새로운 데이터로 업데이트해준다.
        categoryViewModel.categoryList.observe(this) {
            datas.clear()

            datas.apply {
                for (i in 0 until it.size)
                    add(CategoryData(it[i].id, it[i].pic, it[i].name))
            }
            categoryAdapter.datas = datas
            categoryAdapter.notifyDataSetChanged()
        }

        // +버튼 클릭 시 카테고리 추가페이지(프래그먼트)로 넘어가는 코드
        binding.addCategoryBtn.setOnClickListener(View.OnClickListener {
            updateCategoryFragment = UpdateCategoryFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, updateCategoryFragment).addToBackStack(null)
                .commitAllowingStateLoss()
        })
    }

    // 프래그먼트에서 돌아올때 호출되면서 서버의 새로운 리스트로 업데이트
    override fun onResume() {
        super.onResume()

        categoryViewModel.getCategoryList()
        Log.d("d", "d")
    }

    // 리사이클러뷰 초기화
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
