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
        val instance = ProductRepository()
    }

    suspend fun makeUserListRequest(type: Int): Result<String> {
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



    suspend fun makeProductUploadRequest(data: UserData): Result<String> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val response = OkHttpClient().newCall(
                    Request.Builder().url("http://10.0.2.2:3000/")
                        .post(
                            FormBody.Builder()
                                .add("id", data.id.toString())
                                .add("pic", data.nickname)
                                .add("name", data.password)
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
}

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}
