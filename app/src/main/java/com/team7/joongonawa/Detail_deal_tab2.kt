package com.team7.joongonawa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class Detail_deal_tab2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_deal_tab2, container, false)
    }

    public fun newInstant() : Detail_deal_tab2 {
        val args = Bundle()
        val frag = Detail_deal_tab2()
        frag.arguments = args
        return frag
    }
}