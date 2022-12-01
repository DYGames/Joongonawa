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

    suspend fun makeProductRequest(id: Int): Result<String> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val response = OkHttpClient().newCall(
                    Request.Builder()
                        .url("https://joongonawa-server-kfjur.run.goorm.io/product?id=${id}")
                        .build()
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

    suspend fun makeTradeHistoryListRequest(): Result<String> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val response = OkHttpClient().newCall(
                    Request.Builder()
                        .url("https://joongonawa-server-kfjur.run.goorm.io/tradeHistory").build()
                ).execute()
                Result.Success(response.body!!.string())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    suspend fun makeProductListRequest(type: Int): Result<String> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val response = OkHttpClient().newCall(
                    Request.Builder()
                        .url("https://joongonawa-server-kfjur.run.goorm.io/productList?type=$type")
                        .build()
                ).execute()
                Result.Success(response.body!!.string())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    suspend fun makeImageUploadRequest(img: File): Result<String> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val timestamp = System.currentTimeMillis()
                val filename = "${timestamp}.png"
                val response = OkHttpClient().newCall(
                    Request.Builder().url("https://joongonawa-server-kfjur.run.goorm.io/upload")
                        .post(
                            MultipartBody.Builder().setType(MultipartBody.FORM)
                                .addFormDataPart(
                                    "image", filename,
                                    img.asRequestBody("image/png".toMediaTypeOrNull())
                                ).build()
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

    suspend fun makeProductUploadRequest(data: ProductData): Result<String> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val response = OkHttpClient().newCall(
                    Request.Builder().url("https://joongonawa-server-kfjur.run.goorm.io/product")
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

