package com.team7.joongonawa

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailFragmentAdapter(fm: FragmentActivity, private val productViewModel: ProductViewModel) :
    FragmentStateAdapter(fm) {
    //position 에 따라 원하는 Fragment로 이동시키기
    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            0 -> DetailDealTab1Fragment(productViewModel)
            1 -> DetailDealTab2Fragment(productViewModel)
            2 -> DetailDealTab3Fragment(productViewModel)
            else -> DetailDealTab1Fragment(productViewModel)
        }
        return fragment
    }

    //tab의 개수만큼 return
    override fun getItemCount(): Int = 3
}