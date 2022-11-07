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

class ProductRepository {
    companion object {
        val instance = ProductRepository()
    }

    suspend fun makeProductListRequest(type: Int): Result<String> {
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

    fun makeImageUploadRequest(img: File): String {
        OkHttpClient().newCall(
            Request.Builder().url("http://10.0.2.2:3000/upload")
                .post(
                    MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("id", "Dygames")
                        .addFormDataPart(
                            "files", "file.png",
                            img.asRequestBody("image/png".toMediaTypeOrNull())
                        ).build()
                ).build()
        ).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("DYDYF", e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("DYDYR", response.body!!.string())
            }
        })
        return "file.png"
    }

    fun makeProductUploadRequest(data: ProductData) {
        OkHttpClient().newCall(
            Request.Builder().url("http://10.0.2.2:3000/product")
                .post(
                    FormBody.Builder()
                        .add("id", data.id.toString())
                        .add("pic", data.pic)
                        .add("name", data.name)
                        .add("descr", data.descr)
                        .add("price", data.price.toString())
                        .add("category", data.category.toString())
                        .add("type", data.type.toString())
                        .add("condi", data.condi).build()
                ).build()
        ).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("DYDY", e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("DYDY", response.body!!.string())
            }

        })
    }
}

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

