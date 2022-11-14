package com.team7.joongonawa

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.team7.joongonawa.databinding.ProductpageActivityBinding

class ItemDetailView : AppCompatActivity() {
    private  lateinit var binding: ProductpageActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProductpageActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.detailViewpager.adapter = ViewPagerAdapter(getImgList())
        binding.detailViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false) // 제목 없애기
        ab.setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼 활성화


    }

    // toolbar 메뉴 클릭 메소드 추가
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home-> { // 뒤로가기 버튼 눌리면
                finish() // 액티비티 종료
                return true
            }
            // 여기에 나중에서 공유 버튼 눌렸을 때 추가하기
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getImgList() : ArrayList<Int> {
        return arrayListOf<Int>(R.drawable.detail_cautionbtn, R.drawable.detail_plus)
    }
}