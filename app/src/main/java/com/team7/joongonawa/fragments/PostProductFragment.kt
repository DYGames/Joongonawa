package com.team7.joongonawa

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.team7.joongonawa.databinding.FragmentPostProductBinding
import java.util.*


class PostProductFragment : Fragment() {
    private var _binding: FragmentPostProductBinding? = null
    private val binding get() = _binding!!

    var currentImage: Uri? = null

    private var categoryIds: List<Int> = listOf()
    private var categoryIdx = 0

    private val imageResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            binding.postProductImage.setImageURI(result)
            currentImage = result
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = FragmentPostProductBinding.inflate(layoutInflater)

        val categoryViewModel = CategoryViewModel(CategoryRepository.instance)
        val productViewModel = ProductViewModel(ProductRepository.instance)
        categoryViewModel.getCategoryList()

        categoryViewModel.categoryList.observe(this) { newList ->
            binding.postProductCategoryEdit.adapter = ArrayAdapter(
                requireContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                newList.map { it.name })
            categoryIds = newList.map { it.id }
        }

        binding.postProductCategoryEdit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               categoryIdx = position
            }
        }

        binding.postProductImage.setOnClickListener {
            imageResult.launch("image/Pictures/*")
        }

        productViewModel.uploadState.observe(this) {
            if (it) {
                Toast.makeText(activity, "????????? ??????", Toast.LENGTH_SHORT).show()
                requireActivity().finish()
            }
        }

        binding.postProductTitlePostButton.setOnClickListener {
            // ???????????? ??????????????? ????????? ????????? ????????? ???????????? ?????? ????????? ????????? ???????????? ?????? ?????????? ????????? ???????????? ????????????
            val inputStream = context?.contentResolver?.openInputStream(currentImage!!)
            productViewModel.uploadProduct(
                Utils.convertInputStreamToFile(inputStream),
                ProductData(
                    0,
                    "",
                    binding.postProductTitleEdit.text.toString(),
                    binding.postProductDescrEdit.text.toString(),
                    binding.postProductPriceEdit.text.toString().toInt(),
                    categoryIds[categoryIdx],
                    1,
                    binding.postProductCondiEdit.text.toString()
                ), binding.postProductTypeEdit.text.toString()
            )
            inputStream?.close()
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
        requireActivity().finish()
    }
}