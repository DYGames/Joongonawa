package com.team7.joongonawa

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.team7.joongonawa.databinding.FragmentCategoryTypeUpdateBinding
import com.team7.joongonawa.databinding.FragmentCategoryUpdateBinding
import java.util.*

class UpdateCategoryTypeFragment : Fragment() {
    private var _binding: FragmentCategoryTypeUpdateBinding? = null
    private val binding get() = _binding!!


    lateinit var categoryTypeActivity : CategoryTypeActivity

    var currentImage: Uri? = null
    private val imageResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            binding.categoryTypeImageInput.setImageURI(result)
            currentImage = result
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        categoryTypeActivity = context as CategoryTypeActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentCategoryTypeUpdateBinding.inflate(layoutInflater)
        binding.uploadCategoryTypeImageBtn.setOnClickListener {
            imageResult.launch("image/Pictures/*")
        }

        val categoryViewModel = (requireActivity() as CategoryActivity).categoryViewModel

        categoryViewModel.getCategoryTypeList(categoryTypeActivity.categoryId)

        binding.confirmCategoryTypeUpdateBtn.setOnClickListener {
            val inputStream = context?.contentResolver?.openInputStream(currentImage!!)
            categoryViewModel.uploadCategoryType(
                Utils.convertInputStreamToFile(inputStream),
                CategoryTypeData(
                    currentImage.toString(),
                    binding.categoryTypeNameInput.text.toString(),
                    categoryTypeActivity.categoryId
                ),
                categoryTypeActivity.categoryId
            )
            inputStream?.close()
            //activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as CategoryActivity).categoryViewModel.getCategoryList()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}