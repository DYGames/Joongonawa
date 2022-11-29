package com.team7.joongonawa

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    var userList = MutableLiveData<MutableList<UserData>>().apply {
        viewModelScope.launch(Dispatchers.Main) {
            value = mutableListOf(UserData("", "", ""))
        }
    }

    fun getUserList() {
        viewModelScope.launch {
            var result = userRepository.makeUserListRequest()
            when (result) {
                is Result.Success<String> -> {
                    val array = JSONObject(result.data).getJSONArray("data")
                    val list = mutableListOf<UserData>()
                    for (i in 0 until array.length()) {
                        val p = UserData(
                            array.getJSONObject(i).getString("id"),
                            array.getJSONObject(i).getString("nickname"),
                            array.getJSONObject(i).getString("password")
                        )
                        list.add(p)
                    }
                    userList.value = list
                }
                else -> {
                    userList.value = mutableListOf(UserData("", "", ""))
                }
            }
        }
    }

}
