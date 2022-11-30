package com.team7.joongonawa

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class ProductListViewModel (private val productListRepository: ProductListRepository) : ViewModel() {
    var productList = MutableLiveData<MutableList<ItemData>>().apply {
        viewModelScope.launch(Dispatchers.Main) {
            value = mutableListOf(ItemData(0,"", "", "",  0))
        }
    }

    fun getProductList(type: Int) {
        viewModelScope.launch {
            when (val result = productListRepository.makeProductListRequest(type)) {
                is Result.Success<String> -> {
                    val array = JSONObject(result.data).getJSONArray("data")
                    val list = mutableListOf<ItemData>()
                    for (i in 0 until array.length()) {
                        val p = ItemData(
                            array.getJSONObject(i).getInt("id"),
                            array.getJSONObject(i).getString("pic"),
                            array.getJSONObject(i).getString("name"),
                            array.getJSONObject(i).getString("descr"),
                            array.getJSONObject(i).getInt("price")
                        )
                        list.add(p)
                    }
                    productList.value = list
                    Log.d("getProduct", "뷰모델 getProduct")
                }
                else -> {
                    productList.value = mutableListOf(ItemData(0,"", "", "", 0))
                    Log.d("getProduct", "getProduct")
                }
            }
        }
    }

}
