package com.team7.joongonawa

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    var userInfoList = MutableLiveData<MutableList<UserData>>().apply {
        viewModelScope.launch(Dispatchers.Main) {
            value = mutableListOf(UserData("", "", ""))
        }
    }
    var uploadState =
        MutableLiveData<Boolean>().apply { viewModelScope.launch(Dispatchers.Main) { value = false } }
    var signinState =
        MutableLiveData<Int>().apply { viewModelScope.launch(Dispatchers.Main) { value = 0 } }

    fun signupUser(data: UserData) {
        viewModelScope.launch {

            when (val result1 = userRepository.makeUserSignUpRequest(data)) {
                is Result.Success<String> -> {
                    uploadState.value = true
                }
                else -> {}
            }
        }
    }
    fun signinUser(data: UserData) {
        viewModelScope.launch {

            when (val result1 = userRepository.makeUserSignInRequest(data)) {
                is Result.Success<String> -> {
                    signinState.value = 1
                }
                is Result.Failed<String> -> {
                    signinState.value = 2
                }
                else -> {

                }
            }
        }
    }

    fun getUserInfoList(type: Int) {
        viewModelScope.launch {
            when (val result = userRepository.makeUserInfoListRequest(type)) {
                is Result.Success<String> -> {
                    val array = JSONObject(result.data).getJSONArray("data")
                    val list = mutableListOf<UserData>()
                    for (i in 0 until array.length()) {
                        val p = UserData(

                            array.getJSONObject(i).getString("id"),
                            array.getJSONObject(i).getString("nickname"),
                            array.getJSONObject(i).getString("password"),
                        )
                        list.add(p)
                    }
                    userInfoList.value = list
                }
                else -> {
                    userInfoList.value = mutableListOf(UserData( "", "", "" ))
                }
            }
        }
    }

}
