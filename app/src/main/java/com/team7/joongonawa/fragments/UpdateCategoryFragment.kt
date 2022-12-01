package com.team7.joongonawa

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.team7.joongonawa.databinding.FragmentCategoryUpdateBinding
import java.util.*


class UpdateCategoryFragment : Fragment() {
    private var _binding: FragmentCategoryUpdateBinding? = null
    private val binding get() = _binding!!


    lateinit var categoryActivity : CategoryActivity

    var currentImage: Uri? = null
    private val imageResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            binding.categoryImageInput.setImageURI(result)
            currentImage = result
        }

    //private val categoryViewModel = CategoryViewModel(CategoryRepository.instance)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        categoryActivity = context as CategoryActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentCategoryUpdateBinding.inflate(layoutInflater)
        binding.uploadCategoryImageBtn.setOnClickListener {
            imageResult.launch("image/Pictures/*")
        }

        val categoryViewModel = (requireActivity() as CategoryActivity).categoryViewModel

        binding.confirmCategoryUpdateBtn.setOnClickListener {
            if(binding.categoryNameInput.text.isEmpty()){
                Toast.makeText(context, "카테고리 이름을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                val inputStream = context?.contentResolver?.openInputStream(currentImage!!)
                categoryViewModel.uploadCategory(
                    Utils.convertInputStreamToFile(inputStream),
                    CategoryData(
                        0,
                        currentImage.toString(),
                        binding.categoryNameInput.text.toString()
                    )
                )
                inputStream?.close()
                activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
            }
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