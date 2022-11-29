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
        var rootView = inflater.inflate(R.layout.fragment_detail_deal_tab3, container, false)

        // 거래 내역 추가
        val tab3_tableLayout = rootView.findViewById<TableLayout>(R.id.tab3_table)
        var tab3_table : ArrayList<TableData> = arrayListOf(
            TableData("L", "47,000원", "2"),
            TableData("XL", "49,000원", "3"),
            TableData("S", "51,000원", "3"),
            TableData("S", "58,000원", "1"),
            TableData("L", "59,000원", "1"),
        )

        for (data in tab3_table){
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

            tab3_tableLayout.addView(tableRow)
        }
        return rootView
    }

    public fun newInstant() : Detail_deal_tab3 {
        val args = Bundle()
        val frag = Detail_deal_tab3()
        frag.arguments = args
        return frag
    }

}