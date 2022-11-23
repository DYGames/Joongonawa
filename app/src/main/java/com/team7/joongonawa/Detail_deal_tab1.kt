package com.team7.joongonawa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.intellij.lang.annotations.JdkConstants.TabLayoutPolicy

class Detail_deal_tab1 : Fragment() {
    lateinit var binding: Detail_deal_tab1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_detail_deal_tab1, container, false)
    }

    fun newInstant() : Detail_deal_tab1 {
        val args = Bundle()
        val frag = Detail_deal_tab1()
        frag.arguments = args
        return frag
    }
}