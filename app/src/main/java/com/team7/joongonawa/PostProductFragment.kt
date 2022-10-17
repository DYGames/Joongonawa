package com.team7.joongonawa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team7.joongonawa.databinding.FragmentPostProductBinding

class PostProductFragment : Fragment() {
    private var _binding: FragmentPostProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = FragmentPostProductBinding.inflate(layoutInflater)
        val pvm = ProductViewModel(ProductRepository.instance)
        pvm.productList.observe(this) { newList ->
            binding.test.text =
                "${newList[0].id}, ${newList[0].pic}, ${newList[0].name}, ${newList[0].descr}, ${newList[0].price}, ${newList[0].category}, ${newList[0].type}, ${newList[0].condi}"
        }
        pvm.getProductList(1)

        binding.upload.setOnClickListener {
            pvm.uploadProduct(ProductData(0, "", "dygames", "asdfsadfadsfadsf", 0, 0, 1, "A"))
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