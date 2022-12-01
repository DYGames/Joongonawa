package com.team7.joongonawa

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class CategoryRepository {
    companion object {
        val instance = CategoryRepository()
    }

    suspend fun makeCategoryListRequest(): Result<String> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val response = OkHttpClient().newCall(
                    Request.Builder().url("https://joongonawa-server-kfjur.run.goorm.io/category")
                        .build()
                ).execute()
                Result.Success(response.body!!.string())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    suspend fun makeCategoryTypeListRequest(categoryId: Int): Result<String> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val response = OkHttpClient().newCall(
                    Request.Builder()
                        .url("https://joongonawa-server-kfjur.run.goorm.io/productType?category=$categoryId")
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
                else{
                    Log.d("DYDYe", response.code.toString())
                    Result.Error(Exception())
                }
            } catch (e: Exception) {
                Log.d("DYDYe", e.message.toString())
                Result.Error(e)
            }
        }
    }
    suspend fun makeCategoryUploadRequest(data: CategoryData): Result<String> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                Log.d("dataPic", data.pic.toString())
                Log.d("dataName", data.name)
                val response = OkHttpClient().newCall(
                    Request.Builder().url("https://joongonawa-server-kfjur.run.goorm.io/category")
                        .post(
                            FormBody.Builder()
                                .add("pic", data.pic)
                                .add("name", data.name).build()
                        ).build()
                ).execute()
                Log.d("categoryPic", data.pic)

                if (response.code == 200)
                    Result.Success(response.body!!.string())
                else
                    Result.Error(Exception())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
    suspend fun makeCategoryTypeUploadRequest(data: CategoryTypeData, categoryId: Int): Result<String> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val response = OkHttpClient().newCall(
                    Request.Builder().url("https://joongonawa-server-kfjur.run.goorm.io/productType?category=$categoryId")
                        .post(
                            FormBody.Builder()
                                .add("pic", data.pic)
                                .add("name", data.name)
                                .add("category", data.category.toString()).build()
                        ).build()
                ).execute()
                Log.d("id", categoryId.toString())
                Log.d("code", response.code.toString())
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