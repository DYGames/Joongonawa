package com.team7.joongonawa
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ClipData.Item
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.Filter.FilterResults
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerAdapter(var itemList: ArrayList<ItemData>, var con: Context) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(), Filterable {
    var TAG = "RecyclerViewAdapter"

    var filteredItem = ArrayList<ItemData>()
    var itemFilter = ItemFilter()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var title: TextView
        var place: TextView
        var price: TextView

        init {
            img = itemView.findViewById(R.id.img)
            title = itemView.findViewById(R.id.txt_title)
            place = itemView.findViewById(R.id.txt_place)
            price = itemView.findViewById(R.id.txt_price)

            itemView.setOnClickListener {
                AlertDialog.Builder(con).apply {
                    var position = adapterPosition
                    var item = filteredItem[position]
                    setTitle(item.name)
                    setMessage(item.price)
                    setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        Toast.makeText(con, "OK Button Click", Toast.LENGTH_SHORT).show()
                    })
                    show()
                }
            }
        }
    }

    init {
        filteredItem.addAll(itemList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val con = parent.context
        val inflater = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.itemview, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ItemData = filteredItem[position]
          //이미지 Glide
//        holder.apply {
//            Glide.with(con).load(item.pic).into(img)
//        }
        Log.d("TEST", item.name)
        holder.title.text = item.name
        holder.place.text = item.place
        holder.price.text = item.price.toString() + "원"
    }
    fun lowcheck(){
        var low = filteredItem.sortBy { it.price }
        notifyDataSetChanged()
        return low
    }

    override fun getItemCount(): Int {
        return filteredItem.size
    }

    //-- filter
    override fun getFilter(): Filter {
        return itemFilter
    }

    inner class ItemFilter : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filterString = charSequence.toString()
            val results = FilterResults()
            Log.d(TAG, "charSequence : $charSequence")

            //검색이 필요없을 경우를 위해 원본 배열을 복제
            val filteredList: ArrayList<ItemData> = ArrayList<ItemData>()
            //공백제외 아무런 값이 없을 경우 -> 원본 배열
            if (filterString.trim { it <= ' ' }.isEmpty()) {
                results.values = itemList
                results.count = itemList.size

                return results
                //공백제외 2글자인 경우 -> 제목으로만 검색
            } else if (filterString.trim { it <= ' ' }.length <= 2) {
                for (item in itemList) {
                    if (item.name.contains(filterString)) {
                        filteredList.add(item)
                    }
                }
                //그 외의 경우(공백제외 2글자 초과) -> 제목/지역으로 검색
            } else {
                for (item in itemList) {
                    if (item.name.contains(filterString) || item.place.contains(filterString)) {
                        filteredList.add(item)
                    }
                }
            }
            results.values = filteredList
            results.count = filteredList.size

            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
            filteredItem.clear()
            filteredItem.addAll(filterResults.values as ArrayList<ItemData>)
            notifyDataSetChanged()
        }
    }


}
