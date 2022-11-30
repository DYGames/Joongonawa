package com.team7.joongonawa
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*

class ProductListRepository {
    companion object {
        val instance = ProductListRepository()
    }

    suspend fun makeProductListRequest(type: Int): Result<String> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val response = OkHttpClient().newCall(
                    Request.Builder().url("https://joongonawa-server-kfjur.run.goorm.io/productList?type=$type").build()
                ).execute()
                Result.Success(response.body!!.string())
            }
            catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}
//
//sealed class Result<out R> {
//    data class Success<out T>(val data: T) : Result<T>()
//    data class Error(val exception: Exception) : Result<Nothing>()
//}