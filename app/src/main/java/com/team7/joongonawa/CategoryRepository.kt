package com.team7.joongonawa

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class CategoryRepository {
    companion object {
        val instance = CategoryRepository()
    }

    suspend fun makeCategoryListRequest(): Result<String> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val response = OkHttpClient().newCall(
                    Request.Builder().url("http://10.0.2.2:3000/category").build()
                ).execute()
                Result.Success(response.body!!.string())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}