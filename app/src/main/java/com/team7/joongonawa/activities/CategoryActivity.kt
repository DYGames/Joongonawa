package com.team7.joongonawa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
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

    private val productTypeResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.data == null)
                return@registerForActivityResult
            val intent = Intent(this, ItemListActivity::class.java)
            intent.putExtra("productType", result.data!!.getIntExtra("productType", 1))
            intent.putExtra("productName", result.data!!.getStringExtra("productName"))
            intent.putExtra("categoryId", result.data!!.getIntExtra("categoryId", 1))
            intent.putExtra("categoryName", result.data!!.getStringExtra("categoryName"))
            setResult(1, intent)
            finish()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycler()

        var updateCategoryFragment = UpdateCategoryFragment()
        categoryViewModel.uploadState.observe(this) {
            if (it) {
                categoryViewModel.getCategoryList()
                supportFragmentManager.beginTransaction().remove(updateCategoryFragment).commit()
            }
        }
        categoryViewModel.categoryList.observe(this) {
            datas.clear()

            datas.apply {
                for (i in 0 until it.size)
                    add(CategoryData(it[i].id, it[i].pic, it[i].name))
            }
            categoryAdapter.datas = datas
            categoryAdapter.notifyDataSetChanged()
        }

        binding.addCategoryBtn.setOnClickListener(View.OnClickListener {
            updateCategoryFragment = UpdateCategoryFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, updateCategoryFragment).addToBackStack(null)
                .commitAllowingStateLoss()
        })
    }

    override fun onResume() {
        super.onResume()

        categoryViewModel.getCategoryList()
        Log.d("d", "d")
    }

    private fun initRecycler() {
        categoryAdapter = CategoryAdapter(this, productTypeResult)
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
