package com.team7.joongonawa

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView


class Detail_deal_tab2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_detail_deal_tab2, container, false)

        // 거래 내역 추가
        val tab2_tableLayout = rootView.findViewById<TableLayout>(R.id.tab2_table)
        var tab2_table : ArrayList<TableData> = arrayListOf(
            TableData("M", "47,000원", "2"),
            TableData("XL", "49,000원", "1"),
            TableData("M", "51,000원", "1"),
            TableData("S", "58,000원", "1"),
            TableData("L", "59,000원", "2"),
        )

        for (data in tab2_table){
            val tableRow = TableRow(activity)

            val size = TextView(activity)
            size.text = data.data1
            size.gravity = Gravity.START
            tableRow.addView(size)

            val price = TextView(activity)
            price.text = data.data2
            price.gravity = Gravity.END
            tableRow.addView(price)

            val date = TextView(activity)
            date.text = data.data3
            date.gravity = Gravity.END
            tableRow.addView(date)

            tab2_tableLayout.addView(tableRow)
        }

        return rootView
    }

    public fun newInstant() : Detail_deal_tab2 {
        val args = Bundle()
        val frag = Detail_deal_tab2()
        frag.arguments = args
        return frag
    }
}