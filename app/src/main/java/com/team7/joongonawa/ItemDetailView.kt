package com.team7.joongonawa

import android.os.Bundle
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

    }

    private fun getImgList() : ArrayList<Int> {
        return arrayListOf<Int>(R.drawable.detail_cautionbtn, R.drawable.detail_plus)
    }
}