package com.team7.joongonawa

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException

class UserRepository {
    companion object {
        val instance = UserRepository()
    }

    suspend fun makeUserInfoListRequest(type: Int): Result<String> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val response = OkHttpClient().newCall(
                    Request.Builder().url("http://10.0.2.2:3000/productList?type=$type").build()
                ).execute()
                Result.Success(response.body!!.string())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    suspend fun makeUserSignUpRequest(data: UserData): Result<String> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val response = OkHttpClient().newCall(
                    Request.Builder().url("https://joongonawa-server-kfjur.run.goorm.io/signup")
                        .post(
                            FormBody.Builder()
//                                .add("email", data.email)
//                                .add("phonenumber", data.phonenumber)
                                .add("id", data.id)
                                .add("nickname", data.nickname)
                                .add("password", data.password).build()
                        ).build()
                ).execute()
                if (response.code == 200)
                    Result.Success(response.body!!.string())
                else
                    Result.Error(Exception())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
    suspend fun makeUserSignInRequest(data: UserData): Result<String> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val response = OkHttpClient().newCall(
                    Request.Builder().url("https://joongonawa-server-kfjur.run.goorm.io/login?id=${data.id}&password=${data.password}")
                      .build()
                ).execute()
                if (response.code == 200)
                    Result.Success(response.body!!.string())
                else if(response.code == 201)
                    Result.Failed(response.body!!.string())
                else
                    Result.Error(Exception())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}

