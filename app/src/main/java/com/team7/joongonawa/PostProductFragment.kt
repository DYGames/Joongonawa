package com.team7.joongonawa

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import com.team7.joongonawa.databinding.FragmentPostProductBinding
import java.io.File

class PostProductFragment : Fragment() {
    private var _binding: FragmentPostProductBinding? = null
    private val binding get() = _binding!!

    var currentImage: Uri? = null

    private val imageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    result?.data?.data.let {
                        requireActivity().contentResolver.takePersistableUriPermission(
                            result!!.data!!.data!!,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                    }
                    binding.postProductImage.setImageURI(result!!.data!!.data!!)
                    currentImage = result!!.data!!.data!!
                }
            }
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
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            imageResult.launch(intent)
        }

        binding.postProductTitlePostButton.setOnClickListener {
            pvm.uploadProduct(
                File(currentImage!!.path),
                ProductData(0, "", "dygames", "asdfsadfadsfadsf", 0, 0, 1, "A")
            )
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