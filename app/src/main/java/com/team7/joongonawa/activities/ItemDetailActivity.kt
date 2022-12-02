package com.team7.joongonawa

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.team7.joongonawa.databinding.ActivityItemDetailBinding

class ItemDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemDetailBinding
    lateinit var sheetDialog: BottomSheetDialog
    lateinit var lineChart: LineChart
    lateinit var productViewModel: ProductViewModel
    private val chartData = ArrayList<ChartData>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productViewModel = ProductViewModel(ProductRepository.instance)
        productViewModel.getProduct(intent.getIntExtra("productID", 0))
        productViewModel.product.observe(this) {
            // Product data
            // 상품 브랜드, 이름, 카테고리 설정
            binding.detailItemBrandText.text = it.name
            binding.detailItemEnglishNameText.text = it.descr
            binding.detailItemKoreanNameText.text = it.condi

            // 가격 설정
            binding.detailItemPrice.text = "${it.price}${"원"}"
            priceChange(it.price, 45000)

            // 이미지 설정
//            val imgList = arrayListOf<Int>(Glide.with())
//            binding.detailViewpager.adapter = Detail_Picture_ViewPagerAdapter(imgList)

//            val img = Glide.with()
//            val buyImage = findViewById<ImageView>(R.id.detail_viewpager_img)
//            buyImage.setImageResource(img)
        }

        productViewModel.tradeHistoryList.observe(this) {
            for (data in it) {
                var month = data.tradeDate.split("T")[0]
                //month.replace("-", "월")
                //month += "일"

                addChartItem(month, data.price)
            }

            LineChartGraph(chartData, "3개월 그래프")
        }

        // 글자 바꿔주기

        // 이미지 ViewPager2 적용 코드
        binding.detailViewpager.adapter = DetailPictureViewPagerAdapter(getImgList())
        binding.detailViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false) // 제목 없애기
        ab.setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼 활성화

        // size 버튼 눌리면 size_sheet.xml 띄우기
        // 후에 size_sheet에 있는 사이즈 버튼 눌리면 detailCautionBtn 텍스트랑 최근 거래가 바꾸기
        var sheetLayout = LayoutInflater.from(this).inflate(R.layout.size_sheet, null, false)

        val grid = sheetLayout.findViewById<GridLayout>(R.id.detail_sizesheet_grid)
        val btn = createBtn("SS", "50000")
//        grid.addView(btn)

        val sizeBtn1 = sheetLayout.findViewById<Button>(R.id.size1)
        sizeBtn1.setOnClickListener {
            val btnText = sizeBtn1.text.split("\n")
            val size = btnText[0]
            val price = btnText[1]
            onClickSizeSheet(size, price)
            sheetDialog.dismiss()
        }

        val sizeBtn2 = sheetLayout.findViewById<Button>(R.id.size2)
        sizeBtn2.setOnClickListener {
            val btnText = sizeBtn2.text.split("\n")
            val size = btnText[0]
            val price = btnText[1]
            onClickSizeSheet(size, price)
            sheetDialog.dismiss()
        }

        val sizeBtn3 = sheetLayout.findViewById<Button>(R.id.size3)
        sizeBtn3.setOnClickListener {
            val btnText = sizeBtn3.text.split("\n")
            val size = btnText[0]
            val price = btnText[1]
            onClickSizeSheet(size, price)
            sheetDialog.dismiss()
        }

        val sizeBtn4 = sheetLayout.findViewById<Button>(R.id.size4)
        sizeBtn4.setOnClickListener {
            val btnText = sizeBtn4.text.split("\n")
            val size = btnText[0]
            val price = btnText[1]
            onClickSizeSheet(size, price)
            sheetDialog.dismiss()
        }

        sheetDialog = BottomSheetDialog(this)
        sheetDialog.setContentView(sheetLayout)
        sheetDialog.closeOptionsMenu()

        binding.detailSizeBtn.setOnClickListener {
            sheetDialog.show()
        }

        // 구매 버튼
        var buyLayout = LayoutInflater.from(this).inflate(R.layout.detail_buy, null, false)
        var buyDialog = BottomSheetDialog(this)
        buyDialog.setContentView(buyLayout)
        buyDialog.closeOptionsMenu()

        val nextBtn = buyLayout.findViewById<Button>(R.id.nextBtn)

        val buy1 = buyLayout.findViewById<Button>(R.id.buy1)
        buy1.setOnClickListener {
            val price = buy1.text.split("\n")[1]
            nextBtn.text = price
        }

        val buy2 = buyLayout.findViewById<Button>(R.id.buy2)
        buy2.setOnClickListener {
            val price = buy2.text.split("\n")[1]
            nextBtn.text = price
        }

        val buy3 = buyLayout.findViewById<Button>(R.id.buy3)
        buy3.setOnClickListener {
            val price = buy3.text.split("\n")[1]
            nextBtn.text = price
        }

        val buy4 = buyLayout.findViewById<Button>(R.id.buy4)
        buy4.setOnClickListener {
            val price = buy4.text.split("\n")[1]
            nextBtn.text = price
        }

        binding.detailHomeBuy.setOnClickListener {
            buyDialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )

            buyDialog.show()
        }

        chartData.clear()

        // 차트에 데이터 넣어주기
//        addChartItem("12일", 49000)
//        addChartItem("11일", 58000)
//        addChartItem("10일", 59000)
//        addChartItem("9일", 47000)
//        addChartItem("8일", 51000)

        // 차트 그리기

        // 거래 내역 adapter 설정
        val dealAdapter = DetailFragmentAdapter(this, productViewModel)

        val pager = binding.detailPriceviewPager
        pager.adapter = dealAdapter

        // 탭 레이아웃 등록
        val tabTextList = listOf<String>("체결 거래", "판매 입찰", "구매 입찰")
        TabLayoutMediator(binding.detailTabLayout, binding.detailPriceviewPager) { tab, pos ->
            tab.text = tabTextList[pos]
        }.attach()


        // 변동폭 설정
        priceChange(49000, 27000)
    }

    override fun onResume() {
        super.onResume()
        productViewModel.getTradeHistoryList()
    }

    private fun onClickSizeSheet(size: String, price: String) {

        val _price = findViewById<TextView>(R.id.detail_itemPrice)
        val nprice = price.replace(",", "").replace("원", "")
        _price.text = price + "원"

        var buyLayout = LayoutInflater.from(this).inflate(R.layout.detail_buy, null, false)
        val nextBtn = buyLayout.findViewById<Button>(R.id.nextBtn)
        nextBtn.text = price

        val _size = findViewById<Button>(R.id.detail_sizeBtn)
        _size.text = size

        priceChange(nprice.toInt(), 51000)

        sheetDialog.dismiss()

    }

    private fun createBtn(size: String, price: String): Button {
        val btn = Button(this).apply {
            text = size + "\n" + price
            background = getDrawable(R.drawable.detail_sizesheet_btn)

            layoutParams = GridLayout.LayoutParams()
            layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT

            gravity = Gravity.CENTER
            setOnClickListener {
                val size = size
                val price = price
                onClickSizeSheet(size, price)
                sheetDialog.dismiss()
            }
        }
        btn.setBackgroundColor(Color.WHITE)

        return btn
    }

    fun priceChange(now: Int, prev: Int) {
        val text = binding.detailItemPriceChange
        val change = prev - now
        val percent: Double = now.toDouble() / prev.toDouble() * 10

        if (change > 0) {
            text.text = "▲" + change.toString() + "(+" + String.format("%.1f", percent) + "%)"
            text.setTextColor(Color.parseColor("#FA5858"))
        } else if (change < 0) {
            text.text = "▼" + change.toString() + "(-" + String.format("%.1f", percent) + "%)"
            text.setTextColor(Color.parseColor("#00FF00"))
        } else {
            text.text = "-"
            text.setTextColor(Color.parseColor("#D8D8D8"))
        }
    }

    // 차트에 값 추가 메소드
    private fun addChartItem(lableitem: String, dataitem: Int) {
        val item = com.team7.joongonawa.ChartData()
        item.labelData = lableitem
        item.valData = dataitem
        chartData.add(item)
    }

    private fun LineChartGraph(
        chartItem: ArrayList<com.team7.joongonawa.ChartData>,
        displayname: String
    ) {
        lineChart = binding.chart

        val entries = ArrayList<Entry>()
        for (i in chartItem.indices) {
            entries.add(Entry(chartItem[i].valData.toFloat(), i))
        }

        val depenses = LineDataSet(entries, displayname)
        YAxis.AxisDependency.values()
        depenses.axisDependency = YAxis.AxisDependency.RIGHT
        depenses.setColor(Color.RED)
        depenses.valueTextSize = 0f
        depenses.setDrawCircles(false)
        depenses.setDrawFilled(false)

        val label = ArrayList<String>()
        for (i in chartItem.indices) {
            label.add(chartItem[i].labelData)
        }

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(depenses as ILineDataSet)
        val data = LineData(label, dataSets)


        lineChart.setDescription("")
        lineChart.axisLeft.isEnabled = false
        lineChart.data = data
        lineChart.setDrawGridBackground(false)
        lineChart.invalidate()
    }

    // toolbar 메뉴 클릭 메소드 추가
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> { // 뒤로가기 버튼 눌리면
                finish() // 액티비티 종료
                return true
            }
            // 여기에 나중에서 공유 버튼 눌렸을 때 추가하기
        }
        return super.onOptionsItemSelected(item)
    }

    // view pager 사진 입력
    private fun getImgList(): ArrayList<Int> {
        return arrayListOf<Int>(R.drawable.detail_item_ex1, R.drawable.detail_item_ex2)
    }

}