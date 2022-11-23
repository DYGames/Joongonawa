package com.team7.joongonawa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * A simple [Fragment] subclass.
 * Use the [Detail_deal_tab3.newInstance] factory method to
 * create an instance of this fragment.
 */
class Detail_deal_tab3 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_deal_tab3, container, false)
    }

    public fun newInstant() : Detail_deal_tab3 {
        val args = Bundle()
        val frag = Detail_deal_tab3()
        frag.arguments = args
        return frag
    }

}