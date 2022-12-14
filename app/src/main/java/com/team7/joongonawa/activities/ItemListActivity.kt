package com.team7.joongonawa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team7.joongonawa.databinding.ActivityItemListBinding

class ItemListActivity : AppCompatActivity() {
    val TAG = "itemList"
    lateinit var rec: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter
    lateinit var lowcheck: CheckBox
    lateinit var searchview: SearchView

    var _binding: ActivityItemListBinding? = null
    val binding get() = _binding!!
    private val productViewModel = ProductViewModel(ProductRepository.instance)

    private val productTypeResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.data == null)
                return@registerForActivityResult
            val r = result.data!!.getIntExtra("productType", 1)
            binding.itemListCategoryText.text = "${result.data!!.getStringExtra("categoryName")} > ${result.data!!.getStringExtra("productName")}"
            productViewModel.getProductList(r)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rec = binding.rec
        searchview = binding.searchview
        searchview.setOnQueryTextListener(searchViewTextListener)

        rec.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = RecyclerAdapter(mutableListOf<ProductData>() as ArrayList<ProductData>, this)
        rec.adapter = recyclerAdapter

        binding.itemListCategorySpinner.adapter = ArrayAdapter(
            this,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            listOf("?????? ?????? ???", "?????? ?????? ???", "?????? ?????? ???", "?????? ?????? ???")
        )
        binding.itemListCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position) {
                    0 -> {
                        recyclerAdapter.filteredItem.sortBy { it.price }
                    }
                    1 -> {
                        recyclerAdapter.filteredItem.sortByDescending { it.price }
                    }
                    2 -> {
                        recyclerAdapter.filteredItem.sortBy { it.condi }
                    }
                    3 -> {
                        recyclerAdapter.filteredItem.sortByDescending { it.condi }
                    }
                }
                recyclerAdapter.notifyDataSetChanged()
            }
        }

        binding.itemListCategory.setOnClickListener {
            productTypeResult.launch(Intent(this, CategoryActivity::class.java))
        }

        productViewModel.productList.observe(this) {
            recyclerAdapter.itemList = it as ArrayList<ProductData>
            recyclerAdapter.filter.filter("")
            recyclerAdapter.notifyDataSetChanged()
        }

        binding.fabItemList.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        productViewModel.getProductList()
    }

    //SearchView ????????? ????????? ?????????
    var searchViewTextListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            //???????????? ????????? ??????, ??????????????? ???????????? ???????????? ??????
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            //????????? ??????/???????????? ??????
            override fun onQueryTextChange(s: String): Boolean {
                recyclerAdapter.filter.filter(s)
                Log.d(TAG, "SearchVies Text is changed : $s")
                return false
            }
        }
}