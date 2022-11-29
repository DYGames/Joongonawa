package com.team7.joongonawa

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {
    var categoryList = MutableLiveData<MutableList<CategoryData>>().apply {
        viewModelScope.launch(Dispatchers.Main) {
            value = mutableListOf(CategoryData(0, "", ""))
        }
    }

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

}