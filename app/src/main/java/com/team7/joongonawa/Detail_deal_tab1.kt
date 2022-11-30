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
import androidx.core.view.marginTop


class Detail_deal_tab1 : Fragment() {
    lateinit var binding: Detail_deal_tab1
    lateinit var tradeDatabase : ItemDetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_detail_deal_tab1, container, false)

        // 거래 내역 추가
        val tab1_tableLayout = rootView.findViewById<TableLayout>(R.id.tab1_table)
        var tab1_table : ArrayList<TableData> = ArrayList<TableData>()

//            arrayListOf(
//            TableData("XL", "49,000원", "2022/10/12"),
//            TableData("S", "58,000원", "2022/10/11"),
//            TableData("L", "59,000원", "2022/10/10"),
//            TableData("M", "47,000원", "2022/10/09"),
//            TableData("M", "51,000원", "2022/10/08"),
//        )

        tradeDatabase = ItemDetailViewModel(ProductRepository.instance)
        tradeDatabase.itemList.observe(this.viewLifecycleOwner) {
            for (data in it){
                var priceResult = data.price.toString() + "원"
                var date = data.tradeDate.replace("-", "/")
//                    .slice(IntRange(0,10))
                tab1_table.add(TableData(data.size, priceResult, date))
            }
        }

        for (data in tab1_table){
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

            tab1_tableLayout.addView(tableRow)
        }

        return rootView
    }

    fun newInstant() : Detail_deal_tab1 {
        val args = Bundle()
        val frag = Detail_deal_tab1()
        frag.arguments = args
        return frag
    }
}