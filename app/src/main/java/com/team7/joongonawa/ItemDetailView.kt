package com.team7.joongonawa

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.team7.joongonawa.databinding.ProductpageActivityBinding

class ItemDetailView : AppCompatActivity() {
    private  lateinit var binding: ProductpageActivityBinding
    lateinit var lineChart: LineChart
    private val chartData = ArrayList<com.team7.joongonawa.ChartData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        binding = ProductpageActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 이미지 ViewPager2 적용 코드
        binding.detailViewpager.adapter = Detail_Picture_ViewPagerAdapter(getImgList())
        binding.detailViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false) // 제목 없애기
        ab.setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼 활성화

        // size 버튼 눌리면 size_sheet.xml 띄우기
        // 후에 size_sheet에 있는 사이즈 버튼 눌리면 detailCautionBtn 텍스트랑 최근 거래가 바꾸기
        var sheetLayout = LayoutInflater.from(this).inflate(R.layout.size_sheet, null, false)
        var sheetDialog = BottomSheetDialog(this)
        sheetDialog.setContentView(sheetLayout)
        sheetDialog.closeOptionsMenu()

        binding.detailSizeBtn.setOnClickListener {
            sheetDialog.show()
        }

        chartData.clear()

        // 차트에 데이터 넣어주기
        addChartItem("1월", 7)
        addChartItem("2월", 8)
        addChartItem("3월", 8)
        addChartItem("4월", 8)
        addChartItem("5월", 7)

        // 차트 그리기
        LineChartGraph(chartData, "3개월 그래프")

        // 거래 내역 adapter 설정
        val dealAdapter = DetailFragmentAdapter(this)

        val pager = binding.detailPriceviewPager
        pager.adapter = dealAdapter

        // 탭 레이아웃 등록
        val tabTextList = listOf<String>("체결 거래", "판매 입찰", "구매 입찰")
        TabLayoutMediator(binding.detailTabLayout, binding.detailPriceviewPager){tab,pos ->
            tab.text = tabTextList[pos]
        }.attach()
    }
    // tablayout, viewpager2를 활용해 거래 내역 표 완성. LineChart를 활용해 시세 차트 완성. 버튼 기능 구현.

    // 차트에 값 추가 메소드
    private fun addChartItem(lableitem: String, dataitem: Int) {
        val item = com.team7.joongonawa.ChartData()
        item.lableData = lableitem
        item.valData = dataitem
        chartData.add(item)
    }

    private fun LineChartGraph(chartItem: ArrayList<com.team7.joongonawa.ChartData>, displayname: String){
        lineChart = binding.chart

        val entries = ArrayList<Entry>()
        for (i in chartItem.indices) {
            entries.add(Entry(chartItem[i].valData.toFloat(), i))
        }

        val depenses = LineDataSet(entries, displayname)
//        depenses.label = ""
        YAxis.AxisDependency.values()
        depenses.axisDependency = YAxis.AxisDependency.RIGHT
        depenses.setColor(Color.RED)
        depenses.valueTextSize = 0f
        depenses.setDrawCircles(false)
        depenses.setDrawFilled(false)

        val label = ArrayList<String>()
        for (i in chartItem.indices) {
            label.add(chartItem[i].lableData)
        }

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(depenses as ILineDataSet)
        val data = LineData(label, dataSets)


        lineChart.setDescription("")
        lineChart.axisLeft.isEnabled = false
//        lineChart.xAxis.isEnabled = false
        lineChart.data = data
        lineChart.setDrawGridBackground(false)
        lineChart.invalidate()
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

    // view pager 사진 입력
    private fun getImgList() : ArrayList<Int> {
        return arrayListOf<Int>(R.drawable.detail_item_ex1, R.drawable.detail_item_ex2)
    }
}

data class ChartData(
    var lableData: String = "",
    var valData: Int = 0
)