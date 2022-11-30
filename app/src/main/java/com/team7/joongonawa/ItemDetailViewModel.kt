package com.team7.joongonawa

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import org.json.JSONObject

class ItemDetailViewModel(private val productRepository: ProductRepository) : ViewModel() {
    var productList = MutableLiveData<MutableList<ProductData>>().apply {
        viewModelScope.launch(Main) {
            value = mutableListOf(ProductData(0, "", "", "", 0, 0, 0, ""))
        }
    }
    var itemList = MutableLiveData<MutableList<ItemData>>().apply{
        viewModelScope.launch(Main) {
            value = mutableListOf(ItemData(0, "", "", 0, 0))
        }
    }

    var uploadState =
        MutableLiveData<Boolean>().apply { viewModelScope.launch(Main) { value = false } }

    fun getItemList() {
        viewModelScope.launch {
            when (val result = productRepository.makeItemListRequest()) {
                is Result.Success<String> -> {
                    val array = JSONObject(result.data).getJSONArray("data")
                    val list = mutableListOf<ItemData>()
                    for (i in 0 until array.length()){
                        val p = ItemData(
                            array.getJSONObject(i).getInt("id"),
                            array.getJSONObject(i).getString("size"),
                            array.getJSONObject(i).getString("tradeDate"),
                            array.getJSONObject(i).getInt("price"),
                            array.getJSONObject(i).getInt("amount"),
                        )
                        list.add(p)
                    }
                    itemList.value = list
                }
                else -> {
                    itemList.value = mutableListOf(ItemData(0, "", "", 0, 0))
                }
            }
        }
    }

    fun getProductList(type: Int) {
        viewModelScope.launch {
            when (val result = productRepository.makeProductListRequest(type)) {
                is Result.Success<String> -> {
                    val array = JSONObject(result.data).getJSONArray("data")
                    val list = mutableListOf<ProductData>()
                    for (i in 0 until array.length()) {
                        val p = ProductData(
                            array.getJSONObject(i).getInt("id"),
                            array.getJSONObject(i).getString("pic"),
                            array.getJSONObject(i).getString("name"),
                            array.getJSONObject(i).getString("descr"),
                            array.getJSONObject(i).getInt("price"),
                            array.getJSONObject(i).getInt("category"),
                            array.getJSONObject(i).getInt("type"),
                            array.getJSONObject(i).getString("condi")
                        )
                        list.add(p)
                    }
                    productList.value = list
                }
                else -> {
                    productList.value = mutableListOf(ProductData(0, "", "", "", 0, 0, 0, ""))
                }
            }
        }
    }
}