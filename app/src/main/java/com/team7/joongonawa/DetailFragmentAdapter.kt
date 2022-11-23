package com.team7.joongonawa

import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailFragmentAdapter(fm : FragmentActivity): FragmentStateAdapter(fm) {
    //position 에 따라 원하는 Fragment로 이동시키기
    override fun createFragment(position: Int): Fragment {

        val fragment =  when(position)
        {
            0-> Detail_deal_tab1()
            1-> Detail_deal_tab2()
            2-> Detail_deal_tab3()
            else -> Detail_deal_tab1()
        }
        return fragment
    }

    //tab의 개수만큼 return
    override fun getItemCount(): Int = 3
}