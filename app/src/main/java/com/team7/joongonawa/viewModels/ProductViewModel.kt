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

    var tradeHistoryList = MutableLiveData<MutableList<TradeData>>().apply {
        viewModelScope.launch(Main) {
            value = mutableListOf(TradeData(0, "", "", 0, 0))
        }
    }

    var product = MutableLiveData<ProductData>().apply {
        viewModelScope.launch(Main) {
            value = ProductData(0, "", "", "", 0, 0, 0, "")
        }
    }

    var productType = MutableLiveData<Int>().apply {
        value = 1
    }

    var uploadState =
        MutableLiveData<Boolean>().apply { viewModelScope.launch(Main) { value = false } }

    fun getProductList(type: Int) {
        productType.value = type
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

    fun getProductList() {
        viewModelScope.launch {
            Log.d("DYDYDY", productType.value!!.toString())
            when (val result =
                 productRepository.makeProductListRequest(productType.value!!.toInt()) ) {
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

    fun getProduct(id: Int) {
        viewModelScope.launch {
            when (val result = productRepository.makeProductRequest(id)) {
                is Result.Success<String> -> {
                    val data = JSONObject(result.data).getJSONArray("data").getJSONObject(0)
                    val p = ProductData(
                        data.getInt("id"),
                        data.getString("pic"),
                        data.getString("name"),
                        data.getString("descr"),
                        data.getInt("price"),
                        data.getInt("category"),
                        data.getInt("type"),
                        data.getString("condi")
                    )
                    product.value = p
                }
                else -> {
                    product.value = ProductData(0, "", "", "", 0, 0, 0, "")
                }
            }
        }
    }

    fun uploadProduct(img: File, data: ProductData, type: String) {
        viewModelScope.launch {
            when (val result = productRepository.makeImageUploadRequest(img)) {
                is Result.Success<String> -> {
                    data.pic = JSONObject(result.data).getString("filename")
                    when (val result1 = productRepository.makeProductUploadRequest(data, type)) {
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

    fun getTradeHistoryList() {
        viewModelScope.launch {
            when (val result = productRepository.makeTradeHistoryListRequest()) {
                is Result.Success<String> -> {
                    val array = JSONObject(result.data).getJSONArray("data")
                    val list = mutableListOf<TradeData>()
                    for (i in 0 until array.length()) {
                        val p = TradeData(
                            array.getJSONObject(i).getInt("id"),
                            array.getJSONObject(i).getString("size"),
                            array.getJSONObject(i).getString("tradeDate"),
                            array.getJSONObject(i).getInt("price"),
                            array.getJSONObject(i).getInt("amount"),
                        )
                        list.add(p)
                    }
                    tradeHistoryList.value = list
                }
                else -> {
                    tradeHistoryList.value = mutableListOf(TradeData(0, "", "", 0, 0))
                }
            }
        }
    }
}