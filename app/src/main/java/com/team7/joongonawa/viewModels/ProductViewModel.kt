package com.team7.joongonawa

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File
import kotlin.reflect.typeOf

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {
    var productList = MutableLiveData<MutableList<ProductData>>().apply {
        viewModelScope.launch(Main) {
            value = mutableListOf(ProductData(0, "", "", "", 0, 0, 0, ""))
        }
    }

    var uploadState =
        MutableLiveData<Boolean>().apply { viewModelScope.launch(Main) { value = false } }

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

    fun uploadProduct(img: File, data: ProductData) {
        viewModelScope.launch {
            when (val result = productRepository.makeImageUploadRequest(img)) {
                is Result.Success<String> -> {
                    data.pic = JSONObject(result.data).getString("filename")
                    when (val result1 = productRepository.makeProductUploadRequest(data)) {
                        is Result.Success<String> -> {
                            uploadState.value = true
                        }
                        else -> {

                        }
                    }
                }
                else -> {

                }
            }
        }
    }
}