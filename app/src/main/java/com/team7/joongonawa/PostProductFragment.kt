package com.team7.joongonawa

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.team7.joongonawa.databinding.FragmentPostProductBinding
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.String
import java.util.*
import kotlin.ByteArray
import kotlin.Int
import kotlin.also


class PostProductFragment : Fragment() {
    private var _binding: FragmentPostProductBinding? = null
    private val binding get() = _binding!!

    var currentImage: Uri? = null

    private val imageResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            binding.postProductImage.setImageURI(result)
            currentImage = result
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = FragmentPostProductBinding.inflate(layoutInflater)
        val pvm = ProductViewModel(ProductRepository.instance)
        pvm.productList.observe(this) { newList ->
            //binding.test.text =
            //"${newList[0].id}, ${newList[0].pic}, ${newList[0].name}, ${newList[0].descr}, ${newList[0].price}, ${newList[0].category}, ${newList[0].type}, ${newList[0].condi}"
        }
        pvm.getProductList(1)

        binding.postProductImage.setOnClickListener {
            imageResult.launch("image/Pictures/*")
        }

        binding.postProductTitlePostButton.setOnClickListener {
            val inputStream = context?.contentResolver?.openInputStream(currentImage!!)
            Log.d("DYDY", inputStream.toString())
            pvm.uploadProduct(
                convertInputStreamToFile(inputStream),
                ProductData(0, "", "dygames", "asdfsadfadsfadsf", 0, 0, 1, "A")
            )
            inputStream?.close()
        }
    }

    private fun convertInputStreamToFile(ips: InputStream?): File {
        val tempFile = File.createTempFile(String.valueOf(ips.hashCode()), ".tmp")
        tempFile.deleteOnExit()
        copyInputStreamToFile(ips!!, tempFile)
        return tempFile
    }

    private fun copyInputStreamToFile(inputStream: InputStream, file: File) {
        FileOutputStream(file).use { outputStream ->
            var read: Int
            val bytes = ByteArray(1024)
            while (inputStream.read(bytes).also { read = it } != -1) {
                outputStream.write(bytes, 0, read)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}