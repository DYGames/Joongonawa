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
    // 받아온 이미지 주소를 currentImage에 할당
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
        // 이미지 업로드 버튼 클릭 시 드라이브에서 파일 가져오기
        binding.uploadCategoryImageBtn.setOnClickListener {
            imageResult.launch("image/Pictures/*")
        }

        val categoryViewModel = (requireActivity() as CategoryActivity).categoryViewModel

        // 추가하기 버튼 클릭 시 서버로 데이터 추가 요청을 한다.
        binding.confirmCategoryUpdateBtn.setOnClickListener {
            // 이름 입력칸이 비어있을 시 toast메시지 출력
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