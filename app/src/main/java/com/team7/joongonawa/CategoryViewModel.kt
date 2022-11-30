package com.team7.joongonawa

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File

class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {
    var categoryList = MutableLiveData<MutableList<CategoryData>>().apply {
        viewModelScope.launch(Dispatchers.Main) {
        }
    }

    var categoryTypeList = MutableLiveData<MutableList<CategoryTypeData>>().apply {
        viewModelScope.launch(Dispatchers.Main) {
        }
    }

    var uploadState =
        MutableLiveData<Boolean>().apply { viewModelScope.launch(Main) { value = false } }

    fun getCategoryList() {
        viewModelScope.launch {
            var result = categoryRepository.makeCategoryListRequest()
            when (result) {
                is Result.Success<String> -> {
                    val array = JSONObject(result.data).getJSONArray("data")
                    val list = mutableListOf<CategoryData>()
                    for (i in 0 until array.length()) {
                        val p = CategoryData(
                            array.getJSONObject(i).getInt("id"),
                            array.getJSONObject(i).getString("pic"),
                            array.getJSONObject(i).getString("name")
                        )

                        list.add(p)
                    }

                    categoryList.value = list
                }
                else -> {
                    categoryList.value = mutableListOf(CategoryData(0, "", ""))
                }
            }
        }
    }

    fun getCategoryTypeList(categoryId: Int) {
        viewModelScope.launch {
            var result = categoryRepository.makeCategoryTypeListRequest(categoryId)
            when (result) {
                is Result.Success<String> -> {
                    val array = JSONObject(result.data).getJSONArray("data")
                    val list = mutableListOf<CategoryTypeData>()
                    for (i in 0 until array.length()) {
                        val p = CategoryTypeData(
                            array.getJSONObject(i).getString("pic"),
                            array.getJSONObject(i).getString("name"),
                            array.getJSONObject(i).getInt("category"),
                        )
                        list.add(p)
                    }
                    categoryTypeList.value = list
                }
                else -> {
                    categoryTypeList.value = mutableListOf(CategoryTypeData("", "", 0))
                }
            }
        }
    }

    fun uploadCategory(img: File, data: CategoryData) {
        viewModelScope.launch {
            when (val result = categoryRepository.makeImageUploadRequest(img)) {
                is Result.Success<String> -> {
                    data.pic = JSONObject(result.data).getString("filename")
                    when (val result1 = categoryRepository.makeCategoryUploadRequest(data)) {
                        is Result.Success<String> -> {
                            uploadState.value = true
                        }
                        else -> {

                        }
                    }
                }
                else -> {
                    Log.d("DYDYF", result.toString())

                }
            }
        }
    }

    fun uploadCategoryType(img: File, data: CategoryTypeData, categoryId: Int) {
        viewModelScope.launch {
            when (val result = categoryRepository.makeImageUploadRequest(img)) {
                is Result.Success<String> -> {
                    data.pic = JSONObject(result.data).getString("filename")
                    when (val result1 =
                        categoryRepository.makeCategoryTypeUploadRequest(data, categoryId)) {
                        is Result.Success<String> -> {
                            uploadState.value = true
                        }
                        else -> {

                        }
                    }
                }
                else -> {
                    Log.d("DYDYF", result.toString())

                }
            }
        }
    }
}